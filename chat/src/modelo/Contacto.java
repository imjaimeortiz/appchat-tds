package modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public abstract class Contacto {
	private int codigo;
	private String nombre;
	private List<Mensaje> mensajes;
	
	
	public Contacto(String nombre) {
		codigo = 0;
		this.nombre = nombre;
	}
	
	public Contacto(String nombre, List<Mensaje> mensajes) {
		this(nombre);
		this.mensajes = mensajes;
		
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
	
	
	public List<Mensaje> getMensajes() {
		return mensajes;
	}
	
	public Mensaje addMensaje(String texto, Date hora, String emoticono, Usuario usuario, ContactoIndividual contacto) {
		Mensaje mensaje = new Mensaje(texto, hora, emoticono, usuario, contacto);
		mensajes.add(mensaje);
		return mensaje;
	}
	
	public void addMensaje(Mensaje mensaje) {
		mensajes.add(mensaje);
	}
	

}
