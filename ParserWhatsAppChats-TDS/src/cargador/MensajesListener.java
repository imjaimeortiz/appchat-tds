package cargador;

import java.util.EventListener;
import java.util.EventObject;

public interface MensajesListener extends EventListener {
	
	public boolean nuevosMensajes(EventObject e);

}