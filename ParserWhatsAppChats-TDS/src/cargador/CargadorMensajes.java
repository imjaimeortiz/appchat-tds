package cargador;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import parser.SimpleTextParser;

public class CargadorMensajes implements Serializable {
	

	private Vector<MensajesListener> mensajeListeners = new Vector();
	String ruta;
	
	public synchronized void addMensajeListener(MensajesListener listener) {
		mensajeListeners.add(listener);
	}

	public synchronized void removeMensajeListener(MensajesListener listener) {
		mensajeListeners.remove(listener);
	}
	
	public CargadorMensajes() {

	}
	
	public void setMensajes (String ruta, Plataforma plataforma) {
		
		String formatDateWhatsApp = null;
		switch (plataforma) {
			case ANDROID:
				formatDateWhatsApp = "d/M/yy H:mm";
				break;
			case ANDROID_2:
				formatDateWhatsApp = "d/M/yyyy, H:mm";
				break;
			case IOS:
				formatDateWhatsApp = "d/M/yy H:mm:ss";
				break;
		}
		
		List<MensajeWhatsApp> mensajes = null;
		try {
			mensajes = SimpleTextParser.parse(ruta, formatDateWhatsApp, plataforma);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MensajesEvent event = new MensajesEvent(this, mensajes);
		notificarNuevoMensaje(event);
	}
	
	private void notificarNuevoMensaje(MensajesEvent evento) {
		Vector lista;
		synchronized (this) {
			lista = (Vector) mensajeListeners.clone();
		}
		for (int i = 0; i < lista.size(); i++) {
			MensajesListener listener = (MensajesListener) lista.elementAt(i);
			listener.nuevosMensajes(evento);
		}
	}
	

}

