package persistencia;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Mensaje;
import modelo.Usuario;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensaje implements IAdaptadorMensajeDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorMensaje unicaInstancia = null;
	private DateTimeFormatter dateFormat;

	public static AdaptadorMensaje getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorMensaje();
		} else
			return unicaInstancia;
	}

	private AdaptadorMensaje() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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
		AdaptadorGrupo adaptadorGrupo = AdaptadorGrupo.getUnicaInstancia();
		Contacto c = mensaje.getContacto();
		if (c instanceof ContactoIndividual)
			adaptadorContacto.registrarContactoIndividual((ContactoIndividual) c);
		else adaptadorGrupo.registrarGrupo((Grupo)c);
		
		// crear entidad mensaje
		eMensaje = new Entidad();
		eMensaje.setNombre("mensaje");
		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("hora", mensaje.getHora().format(dateFormat)),
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
		servPersistencia.anadirPropiedadEntidad(eMensaje, "hora", mensaje.getHora().format(dateFormat));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "emoticono");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "emoticono", String.valueOf(mensaje.getEmoticono()));
		servPersistencia.eliminarPropiedadEntidad(eMensaje, "usuario");
		servPersistencia.anadirPropiedadEntidad(eMensaje, "usuario",
				String.valueOf(mensaje.getUsuario().getCodigo()));
		servPersistencia.anadirPropiedadEntidad(eMensaje, "contacto",
				String.valueOf(mensaje.getContacto().getCodigo()));
	}

	public Mensaje recuperarMensaje(int codigo) {
		// Si la entidad estÃ¡ en el pool la devuelve directamente
				if (PoolDAO.getUnicaInstancia().contiene(codigo))
					return (Mensaje) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		Entidad eMensaje;
		String texto;
		LocalDateTime hora = null ;
		int emoticono;
		Usuario usuario;
		ContactoIndividual contacto;

		eMensaje = servPersistencia.recuperarEntidad(codigo);
		texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		emoticono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono"));
		String fechaAux = servPersistencia.recuperarPropiedadEntidad(eMensaje, "hora");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		hora = LocalDateTime.parse(fechaAux, formatter);
		
		// Para recuperar el usuario se lo solicita al adaptador usuario
		AdaptadorUsuario adaptadorUsuario = AdaptadorUsuario.getUnicaInstancia();
		usuario = adaptadorUsuario.recuperarUsuario(
				Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "usuario")));
		
		AdaptadorContactoIndividual adaptadorContacto = AdaptadorContactoIndividual.getUnicaInstancia();
		contacto = adaptadorContacto.recuperarContactoIndividual(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "contacto")));

		
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