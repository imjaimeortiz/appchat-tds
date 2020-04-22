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

		// Recuperamos el contacto del mensaje
		int i = 0;
		while (i < mensajes.size() && contacto.equals(null)) {
			contacto = ControladorChat.usuarioActual.buscarContactoPorNombre(mensajes.get(i).getAutor());
		}
		// si no existe el contacto, no podemos seguir
		if (contacto.equals(null))
			return false;
		
		ContactoIndividual receptor = null;
		Usuario emisor = null;
		for (MensajeWhatsApp mensaje : mensajes) {
			// si estamos recibiendo el mensaje del contacto
			if (mensaje.getAutor().equals(contacto.getNombre())) {
				emisor = contacto.getUsuario();
				receptor = emisor.buscarContactoPorNombre(ControladorChat.usuarioActual.getNombre());
				// si no nos tiene agregados, no podemos seguir
				if (receptor == null) 
					return false;
			} else {
				// si estamos enviando nosotros un mensaje como usuario
				emisor = ControladorChat.usuarioActual;
				receptor = contacto;
			}
			ControladorChat.getUnicaInstancia().enviarMensaje(mensaje.getTexto(), mensaje.getFecha(), -1, emisor, receptor);
		}
		return false;
	}

}
