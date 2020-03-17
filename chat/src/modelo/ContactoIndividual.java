package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ContactoIndividual extends Contacto {
	private int codigo;
	private String movil;
	private Usuario usuario;	
	
	public ContactoIndividual(String nombre, String movil, Usuario usuario) {
		super(nombre);
		this.movil = movil;
		this.usuario = usuario;	
	}

	public ContactoIndividual(String nombre, String movil, Usuario usuario, List<Mensaje> mensajes) {
		super(nombre, mensajes);
		this.movil = movil;
		this.usuario = usuario;	
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
	
	public List<Mensaje> buscarMensajes(String texto, LocalDateTime inicio, LocalDateTime fin){
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		for(Mensaje m : mensajes) {
			if((m.getTexto().equals(texto))&&(m.getHora().isAfter(inicio)) && (m.getHora().isBefore(fin)) ) {
				mensajes.add(m);
			}
		}
		return mensajes;
	}

}