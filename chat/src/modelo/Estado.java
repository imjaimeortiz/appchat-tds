package modelo;

public class Estado {
	private int codigo;
	private String mensaje;
	private String imagen;
	
	public Estado(String mensaje, String imagen) {
		codigo = 0;
		this.mensaje = mensaje;
		this.imagen = imagen;
		
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	

}