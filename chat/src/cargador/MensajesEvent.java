package cargador;

import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.List;
import modelo.MensajeWhatsApp;
import modelo.Plataforma;
import parser.SimpleTextParser;

public class MensajesEvent extends EventObject {
private List<MensajeWhatsApp> listaMensajes;
	
	public MensajesEvent(Object source, String ruta, Plataforma plataforma, int tipo) {
		super(source);
		try {
			String formatDateWhatsApp = null;
			switch(plataforma) {
			case IOS:
				formatDateWhatsApp = "d/M/yy H:mm:ss";
				break;
			case ANDROID:
				if(tipo == 1) formatDateWhatsApp = "d/M/yy H:mm";
				else formatDateWhatsApp = "d/M/yyyy, H:mm";
				break;
			}
			listaMensajes = SimpleTextParser.parse(ruta, formatDateWhatsApp, plataforma);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<MensajeWhatsApp> getListaMensajes() {
		return listaMensajes;
	}

}
