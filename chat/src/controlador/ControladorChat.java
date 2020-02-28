package controlador;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;
import modelo.Mensaje;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorGrupoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class ControladorChat {

	private static ControladorChat unicaInstancia;

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorContactoIndividual;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;

	private CatalogoUsuarios catalogoUsuarios;

	private ControladorChat() {
		
		inicializarAdaptadores(); // debe ser la primera linea para evitar error
								  // de sincronización
		inicializarCatalogos();
		
	}

	public static ControladorChat getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorChat();
		return unicaInstancia;
	}

	
	
	//registrar un usuario
	public boolean addUsuario(String nombre, Date fechaNacimiento, String movil,String nick, String contrasena) {
		if (catalogoUsuarios.getUsuario(nick) == null && !catalogoUsuarios.existeTlf(movil)) {
			Usuario usuario = catalogoUsuarios.addUsuario(nombre, fechaNacimiento, movil, nick, contrasena);
			adaptadorUsuario.registrarUsuario(usuario);
			return true;
		}
		//si ya existe un usuario con ese nick o ese movil
		return false;
	}
	
	// COMPROBAR USUARIO
	public boolean usuarioTrue(String nombre, String contraseña) {
		return catalogoUsuarios.usuarioTrue(nombre, contraseña);
	}
	
	public Usuario recuperarUsuario(String nick) {
		return catalogoUsuarios.getUsuario(nick);
	}

	// Obtener los contactos de un usuario
	public LinkedList<Contacto> recuperarContactos(Usuario user) {
		return user.recuperarContactos(user);
	}
	
	public LinkedList<ContactoIndividual> recuperarContactosIndividuales(Usuario user) {
		LinkedList<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		for (Contacto contacto : recuperarContactos(user)) {
			if (contacto instanceof ContactoIndividual) contactos.add((ContactoIndividual) contacto);
		}
		return contactos;
	}
	
	// comprobar si existe el tlf
	public boolean existeTlf(String movil) {
		return catalogoUsuarios.existeTlf(movil);
	}
	
	//añadir un contacto
	public void addContacto(Usuario usuario, String movil, String nombre) {
		Usuario u = catalogoUsuarios.buscarUsuarioDelMovil(movil);
		ContactoIndividual contactoi = usuario.addContacto(nombre, movil, u);
		adaptadorContactoIndividual.registrarContactoIndividual(contactoi);
		adaptadorUsuario.modificarUsuario(usuario);
		
	}
	
	//crear un grupo
	public void addGrupo(Grupo grupo) {
		adaptadorGrupo.registrarGrupo(grupo);
		grupo.getAdmin().addGrupo(grupo);
		adaptadorUsuario.modificarUsuario(grupo.getAdmin());
		for (ContactoIndividual miembro : grupo.getContactos()) {
			miembro.getUsuario().addGrupo(grupo);
			adaptadorUsuario.modificarUsuario(miembro.getUsuario());
		}
	}
	
	//eliminar un grupo
	public void removeGrupo(Usuario usuario, Grupo grupo) {
		usuario.removeGrupo(grupo);
		usuario.removeGrupoAdmin(grupo);
		adaptadorUsuario.modificarUsuario(usuario);
		eliminarContactosGrupo(grupo, grupo.getContactos());
		adaptadorGrupo.borrarGrupo(grupo);		
	}
	
	public void setImage(String path, Usuario user) {
		user.setImagen(path);
	}
	
	
	//abandonar un grupo
	public void abandonarGrupo(Usuario usuario, Grupo g) {
		usuario.abandonarGrupo(g);
		usuario.removeGrupo(g);
		adaptadorUsuario.modificarUsuario(usuario);
		adaptadorGrupo.modificarGrupo(g);
	}

	//añadir contactos a un grupo
	public void agregarContactosGrupo(Grupo group, List<ContactoIndividual> nuevos) {
		group.addContactos(nuevos);
		catalogoUsuarios.modificarUsuarios(nuevos);
		adaptadorGrupo.modificarGrupo(group);
	}

	//eliminar contactos de un grupo
	public void eliminarContactosGrupo(Grupo group, List<ContactoIndividual> eliminados) {
		group.removeContactos(eliminados);
		catalogoUsuarios.modificarUsuarios(eliminados);
		adaptadorGrupo.modificarGrupo(group);
	}
	
	//cambiar nombre del grupo
	public void actualizarNombreGrupo(Grupo group, String text) {
		group.setNombre(text);
		adaptadorGrupo.modificarGrupo(group);
	}
	
	public List<ContactoIndividual> miembrosGrupo(Grupo g) {
		return g.getContactos();
	}
	
	public void enviarMensaje(String texto, LocalDateTime localDate, int emoticono, Usuario emisor, Contacto c) {
	
	Mensaje mensaje = emisor.enviarMensajeEmisor(texto, localDate, emoticono, emisor, c);
	Mensaje mensaje1;
	
	if (c instanceof ContactoIndividual) {
		ContactoIndividual contactoDelEmisorEnElReceptor = (ContactoIndividual) ((ContactoIndividual) c).getUsuario().existeContacto(emisor);
		if(contactoDelEmisorEnElReceptor== null) {
			contactoDelEmisorEnElReceptor = ((ContactoIndividual) c).getUsuario().addContacto(emisor.getMovil(), emisor.getMovil(), emisor);
			
			adaptadorUsuario.modificarUsuario(((ContactoIndividual) c).getUsuario());
			
		}
			mensaje1 = ((ContactoIndividual) c).getUsuario().recibirMensaje(texto, localDate, emoticono, emisor, (ContactoIndividual) c, contactoDelEmisorEnElReceptor);
			adaptadorContactoIndividual.modificarContactoIndividual(contactoDelEmisorEnElReceptor);
		
		adaptadorMensaje.registrarMensaje(mensaje);
		adaptadorMensaje.registrarMensaje(mensaje1);
		adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) c);
		
	} else {
		LinkedList<ContactoIndividual> contactos = (LinkedList<ContactoIndividual>) ((Grupo) c).getContactos();
		for (ContactoIndividual contactoIndividual : contactos) {
			ContactoIndividual contactoDelEmisorEnElReceptor = (ContactoIndividual) contactoIndividual.getUsuario().existeContacto(emisor);
			if(contactoDelEmisorEnElReceptor== null) {
				contactoDelEmisorEnElReceptor = ((ContactoIndividual) c).getUsuario().addContacto(emisor.getMovil(), emisor.getMovil(), emisor);
				
				adaptadorUsuario.modificarUsuario(((ContactoIndividual) c).getUsuario());
				
			}
			mensaje1 = contactoIndividual.getUsuario().recibirMensaje(texto, localDate, emoticono, emisor, contactoIndividual, contactoDelEmisorEnElReceptor);
			adaptadorMensaje.registrarMensaje(mensaje1);

		}
		adaptadorMensaje.registrarMensaje(mensaje);
		adaptadorGrupo.modificarGrupo((Grupo)c);
	}

	}
	
	public void setPremium(Usuario user) {
		user.setPremium(true);
	}
	
	public void mostrarEstadisticas(Usuario user) {
		if(user.isPremium()) {
			user.mostrarEstadisticas();
		}
	}
	
	public void generarPDF(Usuario user) {
		if (user.isPremium()) {
			user.generarPDF();
		}
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorContactoIndividual = factoria.getContactoIndividualDAO();
		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
	}

	public List<Usuario> getUsuarios() {
		return catalogoUsuarios.getUsuarios();
	}

	/*public List<Contacto> getTodosContactos(Usuario user) {
		return user.getContactos();
		
	}*/

	public Vector<String> getGruposComun(Usuario user, ContactoIndividual c) {
		LinkedList<Contacto> grupos = new LinkedList<>(); 
		Vector<String> gruposComun = new Vector<>();
		for (Contacto g : user.getContactos()) {
			if (g instanceof Grupo)
				grupos.add(g);
		}
		for (Contacto grupo : grupos) {
			if (c.getUsuario().getContactos().contains(grupo))
			gruposComun.add(grupo.getNombre());
		}
		return null;
	}

	public boolean tlfValid(String tlf) {
		return tlf.length() == 9;
	}
}
