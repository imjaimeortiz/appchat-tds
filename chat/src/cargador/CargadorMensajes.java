package cargador;

import java.util.Vector;

import modelo.Plataforma;

public class CargadorMensajes implements ConversorMensajes{
	

	private Vector mensajeListeners = new Vector();
	String ruta;
	
	public CargadorMensajes() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void convertirMensajes(String ruta, Plataforma plataforma, int tipo) {
		MensajesEvent event = new MensajesEvent(this, ruta, plataforma, tipo);
		notificarCambioEncendido(event);
	}
	
	private void notificarCambioEncendido(MensajesEvent evento) {
		Vector lista;
		synchronized (this) {
			lista = (Vector) mensajeListeners.clone();
		}
		for (int i = 0; i < lista.size(); i++) {
			IMensajesListener listener = (IMensajesListener) lista.elementAt(i);
			listener.nuevosMensajes(evento);
		}
	}
	
	public synchronized void addMensajeListener(IMensajesListener listener) {
		mensajeListeners.add(listener);
	}

	public synchronized void removeMensajeListener(IMensajesListener listener) {
		mensajeListeners.remove(listener);
	}
	

}
