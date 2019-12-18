package modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Usuario {
	private int codigo;
	private String nombre;
	private Date fechaNacimiento;
	private String movil;
	private String nick;
	private String contrasena;
	private String imagen;
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
	public LinkedList<ContactoIndividual> recuperarContactos(Usuario user) {
		LinkedList<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		for(Contacto c : user.getContactos()) {
			if(c instanceof ContactoIndividual)
				contactos.add((ContactoIndividual) c);	
			
		}
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
		for (ContactoIndividual c : grupo.getContactos()) {
			if (c.getMovil().equals(this.getMovil())) {
				grupo.removeContacto(c);
			}
		}
		if (grupo.getAdmin().equals(this) && grupo.getContactos().size() > 0) {
			grupo.setAdmin(grupo.getContactos().get(0).getUsuario());
		}
	}

	

	
	

}