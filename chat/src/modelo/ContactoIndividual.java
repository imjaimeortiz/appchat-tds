package modelo;

import java.util.List;


public class ContactoIndividual extends Contacto {
	private int codigo;
	private String movil;
	private Usuario usuario;
	private String foto;
	
	
	public ContactoIndividual(String nombre, String movil, Usuario usuario) {
		super(nombre);
		this.movil = movil;
		this.usuario = usuario;	
		this.foto = usuario.getImagen();
	}

	public ContactoIndividual(String nombre, String movil, Usuario usuario, List<Mensaje> mensajes) {
		super(nombre, mensajes);
		this.movil = movil;
		this.usuario = usuario;	
		this.foto = usuario.getImagen();
	}

	
	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
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

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

}