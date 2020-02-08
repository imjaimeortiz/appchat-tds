package controlador;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import modelo.CatalogoUsuarios;
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
	public LinkedList<ContactoIndividual> recuperarContactos(Usuario user) {
		return user.recuperarContactos(user);
	}
	
	// comprobar si existe el tlf
	public boolean existeTlf(String movil) {
		return catalogoUsuarios.existeTlf(movil);
	}
	
	//añadir un contacto
	public void addContacto(Usuario usuario, String movil, String nombre) {
		Usuario u = catalogoUsuarios.buscarUsuarioDelMovil(movil);
		ContactoIndividual contactoi = usuario.addContacto(nombre, movil, u);
		adaptadorUsuario.modificarUsuario(usuario);
		adaptadorContactoIndividual.registrarContactoIndividual(contactoi);
	}
	
	//crear un grupo
	public void addGrupo(String nombre, Usuario usuario, LinkedList<ContactoIndividual> miembros) {
		Grupo grupo = usuario.addGrupo(nombre, miembros);
		adaptadorGrupo.registrarGrupo(grupo);
		adaptadorUsuario.modificarUsuario(usuario);
		for (ContactoIndividual miembro : miembros) {
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
	
	
	public void enviarMensaje(String texto, Date hora, String emoticono, Usuario emisor, ContactoIndividual receptor) {
	Mensaje mensaje = emisor.enviarMensaje(texto, hora, emoticono, emisor, receptor);
	adaptadorMensaje.registrarMensaje(mensaje);
	adaptadorContactoIndividual.modificarContactoIndividual(receptor);

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
}