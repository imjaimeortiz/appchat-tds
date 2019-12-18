
package controlador;

import java.util.List;
import java.util.LinkedList;

import modelo.CatalogoUsuarios;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;
import persistencia.IAdaptadorGrupoDAO;
import persistencia.IAdaptadorUsuarioDAO;


public class MetodosGrupo {

	private IAdaptadorGrupoDAO adaptadorGrupo;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private CatalogoUsuarios catalogoUsuarios;
	
	
	public MetodosGrupo(IAdaptadorGrupoDAO adaptadorGrupo, IAdaptadorUsuarioDAO adaptadorUsuario, CatalogoUsuarios catalogoUsuarios) {
		this.adaptadorGrupo = adaptadorGrupo;
		this.adaptadorUsuario = adaptadorUsuario;
		this.catalogoUsuarios = catalogoUsuarios;
	}
	
	
	public void addGrupo(String nombre, Usuario usuario, LinkedList<ContactoIndividual> miembros) {
		Grupo grupo = usuario.addGrupo(nombre, miembros);
		adaptadorGrupo.registrarGrupo(grupo);
		for (ContactoIndividual miembro : miembros) {
			miembro.getUsuario().addGrupo(grupo);
			adaptadorUsuario.modificarUsuario(miembro.getUsuario());
		}
		adaptadorUsuario.modificarUsuario(usuario);
		
	}

	public void agregarContactosGrupo(Grupo group, java.util.List<ContactoIndividual> nuevos) {
		for (ContactoIndividual contacto : nuevos) {
			group.addContacto(contacto);
			contacto.getUsuario().addGrupo(group);
			adaptadorUsuario.modificarUsuario(contacto.getUsuario());
		}
		adaptadorGrupo.modificarGrupo(group);
	}

	public void eliminarContactosGrupo(Grupo group, List<ContactoIndividual> eliminados) {
		for (ContactoIndividual contacto : eliminados) {
			group.removeContacto(contacto);
			contacto.getUsuario().removeGrupo(group);
			adaptadorUsuario.modificarUsuario(contacto.getUsuario());
		}
		adaptadorGrupo.modificarGrupo(group);
	}

	public void actualizarNombreGrupo(Grupo group, String text) {
		group.setNombre(text);
		adaptadorGrupo.modificarGrupo(group);
	}


	public void abandonarGrupo(Usuario usuario, Grupo g) {
		for (ContactoIndividual c : g.getContactos()) {
			if (c.getMovil().equals(usuario.getMovil())) {
				g.removeContacto(c);
			}
		}
		if (g.getAdmin().equals(usuario) && g.getContactos().size() > 0) {
			g.setAdmin(g.getContactos().get(0).getUsuario());
		}
		adaptadorGrupo.modificarGrupo(g);
	}
	
	public void eliminarGrupo(Grupo g) {
		List<ContactoIndividual> contactos = g.getContactos();
		eliminarContactosGrupo(g, contactos);
		adaptadorGrupo.borrarGrupo(g);
		
		
	}

	}


