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
	
	public void addContacto(ContactoIndividual c) {
		this.contactos.add(c);
	}
	
	//recuperar los contactos
	public List<Contacto> recuperarContactos(Usuario user){
		List<Contacto> contactos = user.getContactos().stream().collect(Collectors.toList());
		return contactos;
	}
	


	//Añadir un nuevo grupo
	public Grupo addGrupo(String nombre, LinkedList<ContactoIndividual> miembros) {
		Grupo grupo = new Grupo(nombre, this, miembros);
		contactos.add(grupo);
		gruposAdmin.add(grupo);
		return grupo;
	}
	
	public void addGrupo(Grupo g) {
		contactos.add(g);
	}

	public void removeGrupo(Grupo group) {
		contactos.remove(group);
	}
	
	public void removeContacto(ContactoIndividual contacto) {
		contactos.remove(contacto);
	}
	public void removeGrupoAdmin(Grupo grupo) {
		gruposAdmin.remove(grupo);
	}
	
	public void abandonarGrupo(Grupo grupo) {
		grupo.getContactos().stream().filter(contacto -> contacto.getMovil().equals(getMovil()))
		.forEach(c -> grupo.removeContacto(c));
		
		if (grupo.getAdmin().equals(this) && grupo.getContactos().size() > 0) {
			grupo.setAdmin(grupo.getContactos().get(0).getUsuario());
		}
	}
	
	
	
	public Mensaje enviarMensajeEmisor(String texto, LocalDateTime localDate, int emoticono, Usuario emisor, Contacto receptor) {
		
		return receptor.addMensaje(texto, localDate, emoticono, emisor, receptor);
	}
	

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
	
	public Mensaje recibirMensaje(String texto, LocalDateTime hora, int emoticono, Usuario emisor, ContactoIndividual receptor, ContactoIndividual contactoDelEmisorEnElReceptor) {
		
		Mensaje mensaje = contactoDelEmisorEnElReceptor.addMensaje(texto, hora, emoticono, emisor, receptor);
		return mensaje;
		
	}
	
	public int getMensajesEnviadosUltimoMes() {
		int cont = 0;
		for(Contacto c : contactos) {
			cont += c.getMensajesEnviados(this);
		}

		return cont;
	
		

	}
	

	
	public Integer[] getMensajesAno() {
		Integer[] mensajesMes = {0,0,0,0,0,0,0,0,0,0,0,0};
		for(int i = 0; i<mensajesMes.length; i++) {
			mensajesMes[i]=0;
		}
		contactos.stream().forEach(c -> c.mensajesCadaMes(mensajesMes, this));
		return mensajesMes;
		
	}
	
	
	public Integer[] getGruposConMasMensajes() {
		Integer[] gruposMasMensajes = {0,0,0,0,0,0};
		List<Integer> mensajes = contactos.stream().filter(c -> (c instanceof Grupo)).map(c -> c.getNumMensajes()).filter(m -> m >= 0)
				.sorted(Comparator.reverseOrder()).limit(6).collect(Collectors.toList());
		mensajes.toArray(gruposMasMensajes);
		return gruposMasMensajes;
	}
	
	public void informacionPdf() throws FileNotFoundException, DocumentException {
		List<String> contactosynumeros = new ArrayList<String>();
		for(Contacto c : contactos) {
			if(c instanceof ContactoIndividual) {
				String contactoi = ((ContactoIndividual) c).getMovil() + c.getNombre();
				contactosynumeros.add(contactoi);
				
			}else {
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
	
	
	

}