
package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Grupo;
import modelo.Mensaje;
import modelo.Usuario;
import modelo.ContactoIndividual;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorGrupo implements IAdaptadorGrupoDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorGrupo unicaInstancia = null;

	public static AdaptadorGrupo getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorGrupo();
		} else
			return unicaInstancia;
	}

	private AdaptadorGrupo() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un grupo se le asigna un identificador unico */
	public void registrarGrupo(Grupo grupo) {
		Entidad eGrupo = null;
		// Si la entidad estÃ¡ registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		AdaptadorUsuario adaptadorUsuario = AdaptadorUsuario.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(grupo.getAdmin());
		
		AdaptadorMensaje adaptadorM = AdaptadorMensaje.getUnicaInstancia();
		for (Mensaje m : grupo.getMensajes())
			adaptadorM.registrarMensaje(m);
		
		AdaptadorContactoIndividual adaptadorCI = AdaptadorContactoIndividual.getUnicaInstancia();
		for (ContactoIndividual ci : grupo.getContactos())
			adaptadorCI.registrarContactoIndividual(ci);
		
		// crear entidad grupo
		eGrupo = new Entidad();
		eGrupo.setNombre("grupo");
		eGrupo.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombre", grupo.getNombre()),
						new Propiedad("admin", String.valueOf(grupo.getAdmin().getCodigo())), 
						new Propiedad("mensajes", obtenerCodigosMensajes(grupo.getMensajes())),
						new Propiedad("foto", grupo.getFoto()),
						new Propiedad("contactos", obtenerCodigosContactos(grupo.getContactos())))));
		
		// registrar entidad grupo
		eGrupo = servPersistencia.registrarEntidad(eGrupo);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		grupo.setCodigo(eGrupo.getId());  
	}

	public void borrarGrupo(Grupo grupo) {
		
		AdaptadorMensaje adaptadorM = AdaptadorMensaje.getUnicaInstancia();

		for (Mensaje m : grupo.getMensajes()) {
			adaptadorM.borrarMensaje(m);
		}
		
		AdaptadorContactoIndividual adaptadorCI = AdaptadorContactoIndividual.getUnicaInstancia();

		for (ContactoIndividual ci : grupo.getContactos()) {
			adaptadorCI.borrarContactoIndividual(ci);
		}
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		servPersistencia.borrarEntidad(eGrupo);
	}

	public void modificarGrupo(Grupo grupo) {
		Entidad eGrupo = servPersistencia.recuperarEntidad(grupo.getCodigo());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "admin");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "admin", String.valueOf(grupo.getAdmin()));
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "nombre");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "nombre", grupo.getNombre() );
		String mensajes = obtenerCodigosMensajes(grupo.getMensajes());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "mensajes");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "mensajes", mensajes);
		
		String contactos = obtenerCodigosContactos(grupo.getContactos());
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "contactos");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "contactos", contactos);
		
		servPersistencia.eliminarPropiedadEntidad(eGrupo, "foto");
		servPersistencia.anadirPropiedadEntidad(eGrupo, "foto", grupo.getFoto());
	}

	public Grupo recuperarGrupo(int codigo) {
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Grupo) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		Entidad eGrupo = servPersistencia.recuperarEntidad(codigo);
		
		String nombre;
		Usuario admin;
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		String foto;
		
		nombre = servPersistencia.recuperarPropiedadEntidad(eGrupo, "nombre");
		
		AdaptadorUsuario adaptadorUsuario = AdaptadorUsuario.getUnicaInstancia();
		admin  = adaptadorUsuario.recuperarUsuario(Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eGrupo, "admin")));
		contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eGrupo, "contactos"));
		foto = servPersistencia.recuperarPropiedadEntidad(eGrupo, "foto");
		
		
		Grupo grupo = new Grupo(nombre, admin, contactos, foto);
		grupo.setCodigo(codigo);
		
		PoolDAO.getUnicaInstancia().addObjeto(codigo, grupo);
		
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		mensajes = obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eGrupo, "mensajes"));
		for (Mensaje m : mensajes)
			grupo.addMensaje(m);
		
		
		
		return grupo;
	}

	public List<Grupo> recuperarTodosGrupos() {
		List<Grupo> grupos = new LinkedList<Grupo>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("grupo");

		for (Entidad eGrupo : entidades) {
			grupos.add(recuperarGrupo(eGrupo.getId()));
		}
		return grupos;
	}
	
	
	///////// Funciones auxiliares ////////////
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
	
	private String obtenerCodigosContactos(List<ContactoIndividual> listacontactos) {
		String aux = "";
		for (ContactoIndividual ci : listacontactos) {
			aux += ci.getCodigo() + " ";
		}
		return aux.trim();
	}
	private List<ContactoIndividual> obtenerContactosDesdeCodigos(String contactos) {

		List<ContactoIndividual> listaContactos = new LinkedList<ContactoIndividual>();
		StringTokenizer strTok = new StringTokenizer(contactos, " ");
		AdaptadorContactoIndividual adaptadorCI = AdaptadorContactoIndividual.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaContactos.add(adaptadorCI.recuperarContactoIndividual(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaContactos;
	}

}