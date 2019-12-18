package controlador;

import java.util.Date;
import java.util.LinkedList;

import modelo.CatalogoUsuarios;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;
import modelo.Contacto;
import persistencia.AdaptadorContactoIndividual;
import persistencia.AdaptadorUsuario;
import persistencia.IAdaptadorUsuarioDAO;

public class MetodosUsuario {
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private CatalogoUsuarios catalogoUsuarios;
	
	public MetodosUsuario(IAdaptadorUsuarioDAO adaptadorUsuario, CatalogoUsuarios catalogoUsuarios) {
		this.adaptadorUsuario = adaptadorUsuario;
		this.catalogoUsuarios = catalogoUsuarios;
	}

	public boolean addUsuario(String nombre, Date fechaNacimiento, String movil,String nick, String contrasena, String imagen) {
		if (catalogoUsuarios.getUsuario(nick) == null) {
			Usuario usuario = catalogoUsuarios.addUsuario(nombre, fechaNacimiento, movil, nick, contrasena, imagen);
			adaptadorUsuario.registrarUsuario(usuario);
			return true;
		}
		// Significa que ya existe un usuario con ese nick
		return false;
	}
	
	public boolean usuarioTrue(String nombre, String contraseña) {
		return catalogoUsuarios.usuarioTrue(nombre, contraseña);
	}
	
	public Usuario recuperarUsuario(String nick) {
		return catalogoUsuarios.getUsuario(nick);
	}

	public ContactoIndividual addContacto(Usuario usuario, String nombre, String movil, Usuario contacto) {
		ContactoIndividual contactoi = usuario.addContacto(nombre, movil, contacto);
		adaptadorUsuario.modificarUsuario(usuario);
		return contactoi;
		
	}
	
	public void deleteContacto(Usuario usuario, ContactoIndividual contacto) {
		usuario.removeContacto(contacto);
		adaptadorUsuario.modificarUsuario(usuario);
	}

	/*public boolean existeTlf(String tlf) {
		return catalogoUsuarios.existeTlf(tlf);
	}*/

	public LinkedList<ContactoIndividual> recuperarContactos(Usuario user) {
		return user.recuperarContactos(user);
	}
	
	

	public void abandonarGrupo(Usuario usuario, Grupo g) {
		usuario.removeGrupo(g);
		adaptadorUsuario.modificarUsuario(usuario);
	}
	
	public void eliminarGrupo(Usuario usuario, Grupo grupo) {
		usuario.removeGrupo(grupo);
		usuario.removeGrupoAdmin(grupo);
		adaptadorUsuario.modificarUsuario(usuario);
		
		
	}

}
