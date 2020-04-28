package modelo;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.text.DocumentException;

import informacionUso.Pdf;

public class Usuario {
	private int codigo;
	private String nombre;
	private Date fechaNacimiento;
	private String movil;
	private String nick;
	private String contrasena;
	private String imagen;
	private LocalDate fechaRegistro;
	private boolean premium;
	private List<Grupo> gruposAdmin; // bidireccionalidad con grupo
	//private Estado estado; // unidirecconalidad 0..1 con estado
	private LinkedList<Contacto> contactos;// asociacion multiple con contacto



	public Usuario(String nombre, Date fechaNacimiento, String movil,String nick, String contrasena, String imagen, LocalDate fechaRegistro) {
		codigo = 0;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.nick = nick;
		this.contrasena = contrasena;
		this.imagen = imagen;
		this.premium = false;
		this.fechaRegistro = fechaRegistro;
		this.gruposAdmin = new LinkedList<Grupo>();
		this.contactos = new LinkedList<Contacto>();
	}
	// CON IMAGEN POR DEFECTO
	public Usuario(String nombre, Date fechaNacimiento, String movil,String nick, String contrasena) {
		codigo = 0;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.nick = nick;
		this.contrasena = contrasena;
		this.imagen = "/vistas/avatar.png";
		this.premium = false;
		this.fechaRegistro = LocalDate.now();
		this.gruposAdmin = new LinkedList<Grupo>();
		this.contactos = new LinkedList<Contacto>();
	}

	

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	

	public LocalDate getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(LocalDate fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public boolean isPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}

	public List<Grupo> getGruposAdmin() {
		return gruposAdmin;
	}
	
	public void addGrupoAdmin(Grupo g) {
		gruposAdmin.add(g);
	}
	
	public List<Contacto> getContactos() {
		return contactos;
	}


	//Añadir un nuevo contacto
	public ContactoIndividual addContacto(String nombre, String movil, Usuario usuario) {
		ContactoIndividual contacto = new ContactoIndividual( nombre, movil, usuario);
		this.contactos.add(contacto);
		return contacto;
	}
	//Añadir un nuevo contacto ya creado
	public void addContacto(ContactoIndividual c) {
		this.contactos.add(c);
	}
	
	//Devuelve una lista con todos los contactos del usuario
	public List<Contacto> recuperarContactos(Usuario user){
		List<Contacto> contactos = user.getContactos().stream().collect(Collectors.toList());
		return contactos;
	}
	


	//Añadir un nuevo grupo como contacto
	public Grupo addGrupo(String nombre, LinkedList<ContactoIndividual> miembros) {
		Grupo grupo = new Grupo(nombre, this, miembros);
		contactos.add(grupo);
		gruposAdmin.add(grupo);
		return grupo;
	}
	//Añadir un nuevo grupo ya creado
	public void addGrupo(Grupo g) {
		contactos.add(g);
	}
	//Eliminar un grupo de la lista de contactos
	public void removeGrupo(Grupo group) {
		contactos.remove(group);
	}
	//Eliminar un contacto de la lista de contactos
	public void removeContacto(ContactoIndividual contacto) {
		contactos.remove(contacto);
	}
	//Eliminar un grupo de la lista de los grupos en los cuales el usuario es administrador
	public void removeGrupoAdmin(Grupo grupo) {
		gruposAdmin.remove(grupo);
	}
	//Usuaario abandona un grupo
	public void abandonarGrupo(Grupo grupo) {
		//eliminamos el contacto del usuario en el grupo
		grupo.getContactos().stream().filter(contacto -> contacto.getMovil().equals(getMovil()))
		.forEach(c -> grupo.removeContacto(c));
		//Si el usuario que abandona fuera el administrador le pasamos el rol de administrador a otro usuaario del grupo
		if (grupo.getAdmin().equals(this) && grupo.getContactos().size() > 0) {
			grupo.setAdmin(grupo.getContactos().get(0).getUsuario());
		}
	}
	
	
	//Añade a la listas de mensajes del contacto recepto el mensaje enviado
	public Mensaje enviarMensajeEmisor(String texto, LocalDateTime localDate, int emoticono, Usuario emisor, Contacto receptor) {
		
		return receptor.addMensaje(texto, localDate, emoticono, emisor, receptor);
	}
	
	//Busca el contacto que corresponde al usuaario dado
	public Contacto buscarEmisor(Usuario emisor) {
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual) {
				if (((ContactoIndividual) c).getUsuario().equals(emisor)) {
					return c;
				}
			}
		}
		
		return null;
	}
	
	// Añade el mensaje al contacto del emisor que tiene el receptor. Esto es para que se actualice los mensajes en el usuario receptor con su contacto que actua como emisor
	public Mensaje recibirMensaje(String texto, LocalDateTime hora, int emoticono, Usuario emisor, ContactoIndividual receptor, ContactoIndividual contactoDelEmisorEnElReceptor) {
		
		Mensaje mensaje = contactoDelEmisorEnElReceptor.addMensaje(texto, hora, emoticono, emisor, receptor);
		return mensaje;
		
	}
	
	//Cuenta los mensajes enviados por el usuario con todos sus contactos en el ultimo mes
	public int getMensajesEnviadosUltimoMes() {
		int cont = 0;
		for(Contacto c : contactos) {
			cont += c.getMensajesEnviados(this);
		}

		return cont;
	
		

	}
	

	// Devuelve el numero de mensajaes enviados por el usauario en cada mes del año
	public Integer[] getMensajesAno() {
		Integer[] mensajesMes = {0,0,0,0,0,0,0,0,0,0,0,0};
		for(int i = 0; i<mensajesMes.length; i++) {
			mensajesMes[i]=0;
		}
		contactos.stream().forEach(c -> c.mensajesCadaMes(mensajesMes, this));
		return mensajesMes;
		
	}
	
	//Saca los 6 grupos con más mensajes
	public Integer[] getGruposConMasMensajes() {
		Integer[] gruposMasMensajes = {0,0,0,0,0,0};
		List<Integer> mensajes = contactos.stream().filter(c -> (c instanceof Grupo)).map(c -> c.getNumMensajes()).filter(m -> m >= 0)
				.sorted(Comparator.reverseOrder()).limit(6).collect(Collectors.toList());
		mensajes.toArray(gruposMasMensajes);
		return gruposMasMensajes;
	}
	
	//Crea el pdf con la lista de los contactos del usuario junto con sus números
	public void informacionPdf() throws FileNotFoundException, DocumentException {
		List<String> contactosynumeros = new ArrayList<String>();
		for(Contacto c : contactos) {
			if(c instanceof ContactoIndividual) {
				String contactoi = ((ContactoIndividual) c).getMovil() + c.getNombre();
				contactosynumeros.add(contactoi);
				
			}else {
				// En el caso de un grupo sacamos todos sus contactos indicando a qué grupo pertenecen
				String nombreg ="Contactos del grupo: " + ((Grupo)c).getNombre() + ":";
				contactosynumeros.add(nombreg);
				for(ContactoIndividual ci : ((Grupo) c).getContactos()) {
					String contactog = ci.getMovil() + ci.getNombre();
					contactosynumeros.add(contactog);
				}
				String finGrupo = "FIN DEL GRUPO";
						contactosynumeros.add(finGrupo);
				
				
			}
		}
		
		Pdf pdf = new Pdf();
		pdf.crearPdf(contactosynumeros);
	}
	
	//Devuelve el contacto cuyo nombre es igual al nombre dado
	public ContactoIndividual buscarContactoPorNombre(String nombre) {
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual && c.getNombre().equals(nombre))
				return (ContactoIndividual) c;
		}
		return null;
	}
	

}