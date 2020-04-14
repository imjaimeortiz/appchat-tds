package controlador;

import java.util.EventObject;
import java.util.List;
import cargador.IMensajesListener;
import cargador.MensajesEvent;
import modelo.MensajeWhatsApp;
import modelo.ContactoIndividual;
import modelo.Usuario;

public class CargadorMensajesListener implements IMensajesListener {

	@Override
	public boolean nuevosMensajes(EventObject event) {
		List<MensajeWhatsApp> mensajes = ((MensajesEvent) event).getListaMensajes();
		ContactoIndividual contacto = null;
		boolean encontrado = false;
		// recorremos hasta encontrar quien es el contacto con el que se esta hablando
		for (MensajeWhatsApp mensaje : mensajes) {
			contacto = ControladorChat.usuarioActual.buscarContactoPorNombre(mensaje.getAutor());
			if(contacto != null) {
				encontrado = true;
				break;
			}
		}
		if (!encontrado)
			return false;
		ContactoIndividual receptor = null;
		Usuario emisor = null;
		encontrado = false;
		for (MensajeWhatsApp mensaje : mensajes) {
			if (mensaje.getAutor().equals(contacto.getNombre())) {
				emisor = contacto.getUsuario();
				receptor = emisor.buscarContactoPorNombre(ControladorChat.usuarioActual.getNombre());
				if(receptor == null) return false;

			} else {
				emisor = ControladorChat.usuarioActual;
				receptor = contacto;
			}
			ControladorChat.getUnicaInstancia().enviarMensaje(mensaje.getTexto(), mensaje.getFecha(), -1, emisor, receptor);
		}
		return false;
	}

}
