package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.ContactoIndividual;

import modelo.Mensaje;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import modelo.Usuario;

public class AdaptadorContactoIndividual implements IAdaptadorContactoIndividualDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoIndividual unicaInstancia = null;

	public static AdaptadorContactoIndividual getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorContactoIndividual();
		} else
			return unicaInstancia;
	}

	private AdaptadorContactoIndividual() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un contacto individual se le asigna un identificador unico */
	public void registrarContactoIndividual(ContactoIndividual contactoi) {
		Entidad eContactoIndividual;
		// Si la entidad estÃ¡ registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eContactoIndividual = servPersistencia.recuperarEntidad(contactoi.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		//registrar los atributos que son objetos
		AdaptadorUsuario adaptadorUsuario = AdaptadorUsuario.getUnicaInstancia();
		AdaptadorMensaje adaptadorM = AdaptadorMensaje.getUnicaInstancia();
		
		adaptadorUsuario.registrarUsuario(contactoi.getUsuario());
		for (Mensaje m : contactoi.getMensajes()) {
			adaptadorM.registrarMensaje(m);
			}
		
		// crear entidad contacto individual
		eContactoIndividual = new Entidad();
		eContactoIndividual.setNombre("contactoi");
		eContactoIndividual.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(
				new Propiedad("movil", contactoi.getMovil()),
				new Propiedad("nombre", contactoi.getNombre()),
				new Propiedad("usuario", String.valueOf(contactoi.getUsuario().getCodigo())),
				new Propiedad("mensajes", obtenerCodigosMensajes(contactoi.getMensajes())))));
		
		// registrar entidad contacto individual
		eContactoIndividual = servPersistencia.registrarEntidad(eContactoIndividual);
		contactoi.setCodigo(eContactoIndividual.getId()); 
		PoolDAO.getUnicaInstancia().addObjeto(contactoi.getCodigo(), contactoi); 
	}

	public void borrarContactoIndividual(ContactoIndividual contactoi) {
		AdaptadorMensaje adaptadorM = AdaptadorMensaje.getUnicaInstancia();

		for (Mensaje m : contactoi.getMensajes()) {
			adaptadorM.borrarMensaje(m);
		}
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoi.getCodigo());
		PoolDAO.getUnicaInstancia().removeObjeto(eContactoIndividual.getId(), eContactoIndividual);
		servPersistencia.borrarEntidad(eContactoIndividual);
		
	}

	public void modificarContactoIndividual(ContactoIndividual contactoi) {
		Entidad eContactoIndividual = servPersistencia.recuperarEntidad(contactoi.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eContactoIndividual, "movil");
		servPersistencia.anadirPropiedadEntidad(eContactoIndividual, "movil", contactoi.getMovil());
		servPersistencia.eliminarPropiedadEntidad(eContactoIndividual, "nombre");
		servPersistencia.anadirPropiedadEntidad(eContactoIndividual, "nombre", contactoi.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eContactoIndividual, "usuario");
		servPersistencia.anadirPropiedadEntidad(eContactoIndividual, "usuario",
				String.valueOf(contactoi.getUsuario().getCodigo()));
		
		String mensajes = obtenerCodigosMensajes(contactoi.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eContactoIndividual, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eContactoIndividual, "mensajes", mensajes);
	}

	public ContactoIndividual recuperarContactoIndividual(int codigo) {
		// Si la entidad estÃ¡ en el pool la devuelve directamente
			if (PoolDAO.getUnicaInstancia().contiene(codigo))
					return (ContactoIndividual) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		Entidad eContactoIndividual;
		String movil;
		String nombre;
		Usuario usuario;
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		

		eContactoIndividual = servPersistencia.recuperarEntidad(codigo);
		movil = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "movil");
		nombre = servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "nombre");
		ContactoIndividual contactoi = new ContactoIndividual(nombre, movil);
		contactoi.setCodigo(codigo);
		PoolDAO.getUnicaInstancia().addObjeto(codigo, contactoi);
		// Para recuperar el usuario se lo solicita al adaptador usuario
			AdaptadorUsuario adaptadorUsuario = AdaptadorUsuario.getUnicaInstancia();
			usuario = adaptadorUsuario.recuperarUsuario(
					Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "usuario")));
	
		contactoi.setUsuario(usuario);
		mensajes = obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eContactoIndividual, "mensajes"));
		for (Mensaje m : mensajes)
			contactoi.addMensaje(m);
		
		return contactoi;
	}

	public List<ContactoIndividual> recuperarTodosContactosIndividuales() {
		List<ContactoIndividual> contactosIndividuales = new LinkedList<ContactoIndividual>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("contactoi");

		for (Entidad eContactoIndividual : entidades) {

			contactosIndividuales.add(recuperarContactoIndividual(eContactoIndividual.getId()));

		
		}
		return contactosIndividuales;
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	///////////////////FUNCIONES AUXILIARES //////////////////////////////////////
	private String obtenerCodigosMensajes(List<Mensaje> listamensajes) {
		String aux = "";
		for (Mensaje m : listamensajes) {
			aux += m.getCodigo() + " ";
		}
		return aux.trim();
	}
	private List<Mensaje> obtenerMensajesDesdeCodigos(String mensajes) {

		List<Mensaje> listaMensajes = new LinkedList<Mensaje>();
		StringTokenizer strTok = new StringTokenizer(mensajes, " ");
		AdaptadorMensaje adaptadorM = AdaptadorMensaje.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaMensajes.add(adaptadorM.recuperarMensaje(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaMensajes;
	}


}