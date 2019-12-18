package controlador;

import java.util.Date;


import modelo.Mensaje;
import modelo.Usuario;
import modelo.ContactoIndividual;

import persistencia.IAdaptadorMensajeDAO;

public class MetodosMensaje {

	private IAdaptadorMensajeDAO adaptadorMensaje;
	
	
	public MetodosMensaje(IAdaptadorMensajeDAO adaptadorMensaje) {
		this.adaptadorMensaje = adaptadorMensaje;
		
	}
	
	public void addMensaje(String texto, Date hora, String emoticono, Usuario usuario, ContactoIndividual contacto) {
		Mensaje mensaje = new Mensaje(texto, hora, emoticono, usuario, contacto);
		adaptadorMensaje.registrarMensaje(mensaje);
	}

}
