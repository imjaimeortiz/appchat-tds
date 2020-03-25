package modelo;


import java.util.List;

public class Grupo extends Contacto {
	private int codigo;
	private Usuario admin;
	private List<ContactoIndividual> contactos; // asociacion multiple con contacto individual
	private String foto;
	
	
	// constructor imagen por defecto
	public Grupo(String nombre, Usuario administrador, List<ContactoIndividual> contactos) {
		super(nombre);
		this.admin = administrador;
		this.contactos = contactos;
		this.foto = "/vistas/team.png";
	}
	
	public Grupo(String nombre, Usuario administrador, List<ContactoIndividual> contactos, String foto) {
		super(nombre);
		this.admin = administrador;
		this.contactos = contactos;
		this.foto = foto;
	}

	public Grupo(String nombre, Usuario administrador, List<ContactoIndividual> contactos, List<Mensaje> mensajes, String foto) {
		super(nombre, mensajes);
		this.admin = administrador;
		this.contactos = contactos;
		this.foto = foto;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public void setContactos(List<ContactoIndividual> contactos) {
		this.contactos = contactos;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}
	
	public List<ContactoIndividual> getContactos() {
		return contactos;
	}
	
	public void addContacto(String movil, String nombre,Usuario usuario) {
		ContactoIndividual contacto = new ContactoIndividual(movil, nombre, usuario);
		contactos.add(contacto);	
	}
	
	public void addContacto(ContactoIndividual contacto) {
		contactos.add(contacto);
	}
	
	public void addContactos(List<ContactoIndividual> nuevos) {
		nuevos.stream().forEach(contacto -> {
					addContacto(contacto);
					contacto.getUsuario().addGrupo(this);
	});
		
	}
	

	public void removeContacto(ContactoIndividual contactoIndividual) {
		contactos.remove(contactoIndividual);
	}
	
	
	public void removeContactos(List<ContactoIndividual> eliminados) {
		eliminados.stream().forEach(contacto -> {
			removeContacto(contacto);
			contacto.getUsuario().removeGrupo(this);
		});
	}
	
	

}