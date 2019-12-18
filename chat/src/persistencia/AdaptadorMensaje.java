package persistencia;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Mensaje;
import modelo.Usuario;
import modelo.ContactoIndividual;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensaje implements IAdaptadorMensajeDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensaje unicaInstancia = null;
	private SimpleDateFormat dateFormat;

	public static AdaptadorMensaje getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorMensaje();
		} else
			return unicaInstancia;
	}

	private AdaptadorMensaje() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = new SimpleDateFormat("hh/MM/ss");
	}

	/* cuando se registra un mensaje se le asigna un identificador unico */
	public void registrarMensaje(Mensaje mensaje) {
		Entidad eMensaje = null;
		// Si la entidad estÃ¡ registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		//registrar los atributos que son objetos
		AdaptadorUsuario adaptadorUsuario = AdaptadorUsuario.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(mensaje.getUsuario());
		
		AdaptadorContactoIndividual adaptadorContacto = AdaptadorContactoIndividual.getUnicaInstancia();
		adaptadorContacto.registrarContactoIndividual(mensaje.getContacto());
		
		// crear entidad mensaje
		eMensaje = new Entidad();
		eMensaje.setNombre("mensaje");
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("hora", mensaje.getHora().toString()),
				new Propiedad("emoticono", String.valueOf(mensaje.getEmoticono())),
				new Propiedad("usuario", String.valueOf(mensaje.getUsuario().getCodigo())),
				new Propiedad("contacto", String.valueOf(mensaje.getContacto().getCodigo())))));
		
		// registrar entidad mensaje
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		mensaje.setCodigo(eMensaje.getId());  
	}

	public void borrarMensaje(Mensaje mensaje) {
		
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		servPersistencia.borrarEntidad(eMensaje);
	}

	public void modificarMensaje(Mensaje mensaje) {
		Entidad eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eMensaje, "texto");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "texto", String.valueOf(mensaje.getTexto()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "hora");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "hora", mensaje.getHora().toString());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emoticono");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emoticono", mensaje.getEmoticono());
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "usuario");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "usuario",
				String.valueOf(mensaje.getUsuario().getCodigo()));
		servPersistencia.anadirPropiedadEntidad(eMensaje, "contacto",
				String.valueOf(mensaje.getContacto().getCodigo()));
	}

	public Mensaje recuperarMensaje(int codigo) {
		Entidad eMensaje;
		String texto;
		Date hora = null ;
		String emoticono;
		Usuario usuario;
		ContactoIndividual contacto;

		eMensaje = servPersistencia.recuperarEntidad(codigo);
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		emoticono = servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono");
		
		// Para recuperar el usuario se lo solicita al adaptador usuario
		AdaptadorUsuario adaptadorUsuario = AdaptadorUsuario.getUnicaInstancia();
		usuario = adaptadorUsuario.recuperarUsuario(
				Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "usuario")));
		
		AdaptadorContactoIndividual adaptadorContacto = AdaptadorContactoIndividual.getUnicaInstancia();
		contacto = adaptadorContacto.recuperarContactoIndividual(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "contacto")));
				
		
		try {
			hora = (Date) dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		Mensaje mensaje = new Mensaje(texto, hora, emoticono, usuario, contacto);
		mensaje.setCodigo(codigo);
		return mensaje;
		
		
		
	}

	public List<Mensaje> recuperarTodosMensajes() {
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("mensaje");

		for (Entidad eMensaje : entidades) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}


}