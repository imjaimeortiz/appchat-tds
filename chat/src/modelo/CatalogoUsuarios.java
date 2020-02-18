package modelo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class CatalogoUsuarios {
	private Map<String,Usuario> usuarios; 
	private static CatalogoUsuarios unicaInstancia = new CatalogoUsuarios();
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private CatalogoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			usuarios = new HashMap<String,Usuario>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoUsuarios getUnicaInstancia(){
		return unicaInstancia;
	}
	
	//devuelve todos los usuarios
	public List<Usuario> getUsuarios(){
		
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario u:usuarios.values()) 
			lista.add(u);
		return lista;
	}
	
	public Usuario getUsusario(int codigo) {
		for (Usuario u:usuarios.values()) {
			if (u.getCodigo()==codigo) return u;
		}
		return null;
	}
	public Usuario getUsuario(String nombre) {
		return usuarios.get(nombre); 
	}
	
	public Usuario addUsuario(String nombre, Date fechaNacimiento, String movil,String nick, String contrasena) {
		Usuario usuario = new Usuario(nombre, fechaNacimiento, movil, nick, contrasena);
		usuarios.put(usuario.getNick(),usuario);
		return usuario;
	}
	public void removeUsuario (Usuario u) {
		usuarios.remove(u.getNick());
	}
	
	public boolean usuarioTrue(String nombre, String contraseña) {
		for (Usuario u : getUsuarios()) {
			if (u.getNick().equals(nombre) && u.getContrasena().equals(contraseña)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean existeTlf(String tlf) {
		for (Usuario u : getUsuarios()) {
			if (u.getMovil().equals(tlf)) return true;
		}
		return false;
	}
	
	public Usuario buscarUsuarioDelMovil(String tlf) {
		for(Usuario u : getUsuarios()) {
			if(u.getMovil().equals(tlf)) {
				return u;
				}
	        }
		return null;
		}
	
	public void modificarUsuarios(List<ContactoIndividual> contactos) {
		for (ContactoIndividual contacto : contactos) {
			adaptadorUsuario.modificarUsuario(contacto.getUsuario());
		}
	}
	
	//Recupera todos los usuarios para trabajar con ellos en memoria
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario u: usuariosBD) 
			     usuarios.put(u.getNick(),u);
	}
}
