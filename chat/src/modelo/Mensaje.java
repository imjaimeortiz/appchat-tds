package modelo;

import java.util.Date;

public class Mensaje {
	private int codigo;
	private String texto;
	private Date hora;
	private String emoticono;
	private Usuario usuario; // emisor
	private ContactoIndividual contacto; //receptor
	
	public Mensaje(String texto, Date hora, String emoticono, Usuario usuario, ContactoIndividual contacto) {
		codigo =0;
		this.texto = texto;
		this.hora = hora;
		this.emoticono = emoticono;
		this.usuario = usuario;
		this.contacto = contacto;
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

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public String getEmoticono() {
		return emoticono;
	}

	public void setEmoticono(String emoticono) {
		this.emoticono = emoticono;
	}

	public ContactoIndividual getContacto() {
		return contacto;
	}

	public void setContacto(ContactoIndividual contacto) {
		this.contacto = contacto;
	}
	
	

	
}