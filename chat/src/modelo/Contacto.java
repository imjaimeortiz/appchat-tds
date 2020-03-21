package modelo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public abstract class Contacto {
	private int codigo;
	private String nombre;
	private List<Mensaje> mensajes;
	
	
	public Contacto(String nombre) {
		codigo = 0;
		this.nombre = nombre;
		this.mensajes = new LinkedList<Mensaje>();
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
	
	public Mensaje addMensaje(String texto, LocalDateTime localDate, int emoticono, Usuario usuario, Contacto c) {
		Mensaje mensaje = new Mensaje(texto, localDate, emoticono, usuario, c);
		mensajes.add(mensaje);
		return mensaje;
	}
	
	public void addMensaje(Mensaje mensaje) {
		mensajes.add(mensaje);
	}
	
	public int getMensajesEnviados(Usuario user) {
		Month now = LocalDateTime.now().getMonth();
		int cont = 0;
		for(Mensaje m : mensajes) {
			if((m.getUsuario().equals(user))&& (m.getHora().getMonth().equals(now))){
				cont ++;
			}
		}
		return cont;
	}

}
