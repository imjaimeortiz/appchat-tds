package modelo;

import java.time.LocalDateTime;


public class Mensaje {
	private int codigo;
	private String texto;
	private LocalDateTime hora;
	private int emoticono;
	private Usuario usuario; // emisor
	private Contacto contacto; //receptor
	
	public Mensaje(String texto, LocalDateTime hora2, int emoticono, Usuario usuario, Contacto c) {
		codigo = 0;
		this.texto = texto;
		this.hora = hora2;
		this.emoticono = emoticono;
		this.usuario = usuario;
		this.contacto = c;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getHora() {
		return hora;
	}

	public void setHora(LocalDateTime hora) {
		this.hora = hora;
	}

	public int getEmoticono() {
		return emoticono;
	}

	public void setEmoticono(int emoticono) {
		this.emoticono = emoticono;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	
	

	
}