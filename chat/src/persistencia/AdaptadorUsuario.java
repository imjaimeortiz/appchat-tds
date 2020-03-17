package persistencia;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import modelo.Usuario;

import modelo.Grupo;
import modelo.Contacto;

import modelo.ContactoIndividual;


public class AdaptadorUsuario implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuario unicaInstancia = null;
	private SimpleDateFormat dateFormat;

	public static AdaptadorUsuario getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuario();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuario() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia(); 
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	}

	/* cuando se registra un usuario se le asigna un identificador Ãºnico */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario;
		boolean existe = true; 
		
		// Si la entidad estÃ¡ registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;

		AdaptadorGrupo adaptadorGrupo = AdaptadorGrupo.getUnicaInstancia();
		for (Grupo g : usuario.getGruposAdmin())
			adaptadorGrupo.registrarGrupo(g);
		
		AdaptadorContactoIndividual adaptadorCI = AdaptadorContactoIndividual.getUnicaInstancia();
		AdaptadorGrupo adaptadorG = AdaptadorGrupo.getUnicaInstancia();
		for (Contacto c : usuario.getContactos()) {
			if (c instanceof ContactoIndividual) {
				adaptadorCI.registrarContactoIndividual((ContactoIndividual)c);
			}
			if (c instanceof Grupo) {
				adaptadorG.registrarGrupo((Grupo)c);
			}
			
		}
			
	

		// crear entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("Usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(
						new Propiedad("nombre", usuario.getNombre()), 
						new Propiedad("fecha nacimiento", dateFormat.format(usuario.getFechaNacimiento())), 
						new Propiedad("movil", usuario.getMovil()),
						new Propiedad("nick", usuario.getNick()), 
						new Propiedad("contrasena", usuario.getContrasena()),
						new Propiedad("imagen", usuario.getImagen()),
						new Propiedad("fechaRegistro", dateFormat.format(usuario.getFechaRegistro())),
						new Propiedad("premium", String.valueOf(usuario.isPremium())),
						new Propiedad("gruposAdmin", obtenerCodigosGrupos(usuario.getGruposAdmin())),
						new Propiedad("contactos", obtenerCodigosContactos(usuario.getContactos())))));
		
		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId()); 
		PoolDAO.getUnicaInstancia().addObjeto(eUsuario.getId(), usuario);
	}
	
	
	

	public void borrarUsuario(Usuario usuario) {
		AdaptadorContactoIndividual adaptadorCI = AdaptadorContactoIndividual.getUnicaInstancia();
		AdaptadorGrupo adaptadorG = AdaptadorGrupo.getUnicaInstancia();
		
		for (Contacto c : usuario.getContactos()) {
			if(c instanceof ContactoIndividual) {
			adaptadorCI.borrarContactoIndividual((ContactoIndividual)c);
		   }
			if(c instanceof Grupo) {
				adaptadorG.borrarGrupo((Grupo)c);
			}
		}
		
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		
		PoolDAO.getUnicaInstancia().removeObjeto(eUsuario.getId(), eUsuario);
		servPersistencia.borrarEntidad(eUsuario);
	}

	
	
	public void modificarUsuario(Usuario usuario) {

		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eUsuario, "nombre");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "nombre", usuario.getNombre());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "fecha nacimiento");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "fecha nacimiento",dateFormat.format(usuario.getFechaNacimiento()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "movil");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "movil", usuario.getMovil());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "nick");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "nick", usuario.getNick());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contrasena");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contrasena", usuario.getContrasena());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "imagen");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "imagen", usuario.getImagen());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "fecha registro");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "fecha registro",dateFormat.format(usuario.getFechaRegistro()));
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "premium");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "premium", String.valueOf(usuario.isPremium()));
		String gruposAdmin = obtenerCodigosGrupos(usuario.getGruposAdmin());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "gruposAdmin");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "gruposAdmin", gruposAdmin);
		String contactos = obtenerCodigosContactos(usuario.getContactos());
		servPersistencia.eliminarPropiedadEntidad(eUsuario, "contactos");
		servPersistencia.anadirPropiedadEntidad(eUsuario, "contactos", contactos);
		
		
	}
	

	public Usuario recuperarUsuario(int codigo) {

		// Si la entidad estÃ¡ en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		String nombre;
		Date fechaNacimiento = null;
		String movil;
		String nick;
		String contrasena;
		String imagen;
		Date fechaRegistro = null;
		boolean premium;
		List<Grupo> gruposAdmin = new LinkedList<Grupo>();
		List<Contacto> contactos = new LinkedList<Contacto>();
		
		

		// recuperar entidad
		Entidad eUsuario = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		
		try {
			fechaNacimiento = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fecha nacimiento"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		try {
			fechaRegistro = dateFormat.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fecha registro"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		movil = servPersistencia.recuperarPropiedadEntidad(eUsuario, "movil");
		nick = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nick");
		contrasena = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contrasena");
		imagen = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagen");
		premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));

		

		Usuario usuario = new Usuario(nombre, fechaNacimiento, movil, nick, contrasena,imagen);
		usuario.setCodigo(codigo);

		
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usuario);
		
		gruposAdmin = obtenerGruposDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "gruposAdmin"));

		for (Grupo g : gruposAdmin)
			usuario.addGrupoAdmin(g);
		
		
		contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));
		for (Contacto c : contactos) {
			if(c instanceof ContactoIndividual)
			usuario.addContacto((ContactoIndividual)c);
			
			if(c instanceof Grupo)
				usuario.addGrupo((Grupo)c);
		}
		
		return usuario;
	}

		
		

	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("Usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}

	// -------------------Funciones auxiliares-----------------------------
	
	private String obtenerCodigosGrupos(List<Grupo> listagrupos) {
		String aux = "";
		for (Grupo g : listagrupos) {
			aux += g.getCodigo() + " ";
		}
		return aux.trim();
	}
	private List<Grupo> obtenerGruposDesdeCodigos(String grupos) {

		List<Grupo> listaGrupos = new LinkedList<Grupo>();
		StringTokenizer strTok = new StringTokenizer(grupos, " ");
		AdaptadorGrupo adaptadorG = AdaptadorGrupo.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listaGrupos.add(adaptadorG.recuperarGrupo(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaGrupos;
	}
	
	private String obtenerCodigosContactos(List<Contacto> contactos) {
		String aux = "";
		String aux1 = "";
		int cont = 0;
		for (Contacto c : contactos) {
			if (c instanceof ContactoIndividual) {
				cont= cont + 1;
				aux += c.getCodigo() + " ";
			}
		}
		for(Contacto c : contactos) {
			if (c instanceof Grupo) 
				aux += c.getCodigo() + " ";
			
		}
		aux1 = cont + " " + aux;
		return aux1.trim();

	}

	private List<Contacto> obtenerContactosDesdeCodigos(String contactos) {

		List<Contacto> listaContactos = new LinkedList<Contacto>();
		StringTokenizer strTok = new StringTokenizer(contactos, " ");
		AdaptadorContactoIndividual adaptadorCI = AdaptadorContactoIndividual.getUnicaInstancia();
		AdaptadorGrupo adaptadorG = AdaptadorGrupo.getUnicaInstancia();
		int numContactos = Integer.valueOf((String) strTok.nextElement());
		while (strTok.hasMoreTokens()&& numContactos > 0) {
			listaContactos.add(adaptadorCI.recuperarContactoIndividual(Integer.valueOf((String) strTok.nextElement())));
			numContactos --;
		}
		while (strTok.hasMoreTokens()) {
			listaContactos.add(adaptadorG.recuperarGrupo(Integer.valueOf((String) strTok.nextElement())));
		}
		return listaContactos;
	}


}