package modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Document;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;






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



	public Usuario(String nombre, Date fechaNacimiento, String movil,String nick, String contrasena, String imagen) {
		codigo = 0;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
		this.movil = movil;
		this.nick = nick;
		this.contrasena = contrasena;
		this.imagen = imagen;
		this.premium = false;
		this.fechaRegistro = LocalDate.now();
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
	public LinkedList<Contacto> recuperarContactos(Usuario user){
		LinkedList<Contacto> contactos = new LinkedList<Contacto>();
		user.getContactos().stream().forEach(contacto -> contactos.add(contacto));
		return contactos;
	}
	
	/*public LinkedList<Contacto> recuperarContactos(Usuario user) {
		LinkedList<Contacto> contactos = new LinkedList<Contacto>();
		for(Contacto c : user.getContactos()) {
			//if(c instanceof ContactoIndividual)
				contactos.add(c);	
			
		}
		return contactos;
	}*/

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
	
	/*public void abandonarGrupo(Grupo grupo) {
		for (ContactoIndividual c : grupo.getContactos()) {
			if (c.getMovil().equals(this.getMovil())) {
				grupo.removeContacto(c);
			}
		}
		if (grupo.getAdmin().equals(this) && grupo.getContactos().size() > 0) {
			grupo.setAdmin(grupo.getContactos().get(0).getUsuario());
		}
	}*/
	
	public Mensaje enviarMensajeEmisor(String texto, LocalDateTime localDate, int emoticono, Usuario emisor, Contacto c) {
		
		return c.addMensaje(texto, localDate, emoticono, emisor, c);
	}
	

	public Contacto contactoEnReceptor(Usuario emisor) {
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual) {
				if (((ContactoIndividual) c).getUsuario().equals(emisor)) {
					return c;
				}
			}
		}
		
		return null;
	}
	
	public Mensaje recibirMensaje(String texto, LocalDateTime hora, int emoticono, Usuario emisor, ContactoIndividual receptor, ContactoIndividual contactoEmisorEnReceptro) {
		
		Mensaje mensaje = contactoEmisorEnReceptro.addMensaje(texto, hora, emoticono, emisor, receptor);
		return mensaje;
		
	}
	

	
	
	public void mostrarEstadisticas() {
		
	}
	
<<<<<<< HEAD
	public int getMensajesEnviadosUltimoMes() {
		int cont = 0;
=======
	/*public void toPDF(List<String> contactos) throws FileNotFoundException, DocumentException {
		FileOutputStream archivo = new FileOutputStream("C:\\Usuarios\\Luu\\Escritorio\\Contactos.pdf");
		
		
		
	}*/
	
	public void generarPDF() {
		List<String> contactosToPdf = new LinkedList<String>();
>>>>>>> d17e2fe88c67b8beb591f22f74b943dcaeca1e86
		for(Contacto c : contactos) {
			cont += c.getMensajesEnviados(this);
		}
<<<<<<< HEAD
		return cont;
=======
		
		//toPDF(contactosToPdf);
		
>>>>>>> d17e2fe88c67b8beb591f22f74b943dcaeca1e86
	}
	

	

	
	

}