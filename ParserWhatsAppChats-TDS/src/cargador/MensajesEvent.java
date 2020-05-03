package cargador;

import java.io.IOException;
import java.util.EventObject;
import java.util.List;
import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import parser.SimpleTextParser;

public class MensajesEvent extends EventObject {
	
	/**
	 * 
	 */
	private List<MensajeWhatsApp> mensajes;
	
	public MensajesEvent(Object source, List<MensajeWhatsApp> mensajes) {
		super(source);
		this.mensajes = mensajes;
	}
	
	public List<MensajeWhatsApp> getListaMensajes() {
		return mensajes;
	}

	

}

