package controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Descuento;
import modelo.DescuentoFecha;
import modelo.DescuentoMensajes;
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
	public void addGrupo(Usuario admin, Grupo grupo) {
		adaptadorGrupo.registrarGrupo(grupo);
		admin.addGrupo(grupo);
		admin.addGrupoAdmin(grupo);
		adaptadorUsuario.modificarUsuario(admin);
		for (ContactoIndividual miembro : grupo.getContactos()) {
			if (!miembro.getUsuario().equals(admin)) {
				miembro.getUsuario().addGrupo(grupo);
				adaptadorUsuario.modificarUsuario(miembro.getUsuario());
			}
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
		adaptadorUsuario.modificarUsuario(user);
	}
	
	public void modificarGrupo(Grupo grupo) {
		adaptadorGrupo.modificarGrupo(grupo);
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
		adaptadorGrupo.modificarGrupo(group);
		catalogoUsuarios.modificarUsuarios(nuevos);
	}

	//eliminar contactos de un grupo
	public void eliminarContactosGrupo(Grupo group, List<ContactoIndividual> eliminados) {
		group.removeContactos(eliminados);
		adaptadorGrupo.modificarGrupo(group);
		catalogoUsuarios.modificarUsuarios(eliminados);
	}
	
	//cambiar nombre del grupo
	public void actualizarNombreGrupo(Grupo group, String text) {
		group.setNombre(text);
		adaptadorGrupo.modificarGrupo(group);
	}
	
	public List<ContactoIndividual> miembrosGrupo(Grupo g) {
		return g.getContactos();
	}
	
	public Mensaje enviarMensaje(String texto, LocalDateTime localDate, int emoticono, Usuario emisor, Contacto c) {
	
	Mensaje mensaje = emisor.enviarMensajeEmisor(texto, localDate, emoticono, emisor, c);
	Mensaje mensaje1;
	
	if (c instanceof ContactoIndividual) {
		ContactoIndividual contactoDelEmisorEnElReceptor = (ContactoIndividual) ((ContactoIndividual) c).getUsuario().contactoEnReceptor(emisor);
		
		if (contactoDelEmisorEnElReceptor== null) {
			contactoDelEmisorEnElReceptor = new ContactoIndividual(emisor.getMovil(), emisor.getMovil(), emisor);
			((ContactoIndividual) c).getUsuario().addContacto(contactoDelEmisorEnElReceptor);
			adaptadorContactoIndividual.registrarContactoIndividual(contactoDelEmisorEnElReceptor);
			adaptadorUsuario.modificarUsuario(((ContactoIndividual) c).getUsuario());	
		}
			mensaje1 = ((ContactoIndividual) c).getUsuario().recibirMensaje(texto, localDate, emoticono, emisor, (ContactoIndividual) c, contactoDelEmisorEnElReceptor);
			contactoDelEmisorEnElReceptor.addMensaje(mensaje1);
			adaptadorContactoIndividual.modificarContactoIndividual(contactoDelEmisorEnElReceptor);
		
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje1);
			adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) c);
		
	} else {
		LinkedList<ContactoIndividual> contactos = (LinkedList<ContactoIndividual>) ((Grupo) c).getContactos();
		for (ContactoIndividual contactoIndividual : contactos) {
			ContactoIndividual contactoDelEmisorEnElReceptor = (ContactoIndividual) contactoIndividual.getUsuario().contactoEnReceptor(emisor);
			if(contactoDelEmisorEnElReceptor== null) {
				for(ContactoIndividual ci : ((Grupo) c).getContactos()) {
					contactoDelEmisorEnElReceptor = ci.getUsuario().addContacto(emisor.getMovil(), emisor.getMovil(), emisor);
					adaptadorContactoIndividual.registrarContactoIndividual(contactoDelEmisorEnElReceptor);
					adaptadorUsuario.modificarUsuario(ci.getUsuario());
				}
			}
			mensaje1 = contactoIndividual.getUsuario().recibirMensaje(texto, localDate, emoticono, emisor, contactoIndividual, contactoDelEmisorEnElReceptor);
			contactoDelEmisorEnElReceptor.addMensaje(mensaje1);
			adaptadorMensaje.registrarMensaje(mensaje1);

		}
		adaptadorMensaje.registrarMensaje(mensaje);
		adaptadorGrupo.modificarGrupo((Grupo)c);
	}
	return mensaje;

	}
	
	//Para el descuento comprobamos si el ususario cumple los requisitos de ambos tipos
	//en el caso de que cumpla ambos nos quedamos con el menor precio a pagar
	///////// HABRIA QUE VER SI DEVOLVER DE ALGUNA FORMA EL TIPO DE DESCUENTO QUE SE HA APLICADO
	/////////PARA INFORMAR AL USUARIO EN LA VENTANA ??????????????
	public int setPremium(Usuario user) {
		int pago = 50;
		int pago1 = 100; 
		int pago2 = 100;
		LocalDate fechaDescuento = LocalDate.now().minusMonths(4);
		if(user.getFechaRegistro().isBefore(fechaDescuento)) {
			Descuento desc = new DescuentoFecha();
			pago1 = pago - desc.getDescuento();
		}
		if(user.getMensajesEnviadosUltimoMes() > 50) {
			Descuento desc1 = new DescuentoMensajes();
			pago2 = pago - desc1.getDescuento();
		}
		
		user.setPremium(true);
		adaptadorUsuario.modificarUsuario(user);
		int min = Math.min(pago, pago1);
		int precio = Math.min(min, pago2);
		return precio;
	}
	
	public List<Mensaje> buscarMensaje(Contacto contacto, String nombre, String texto, Date inicio, Date fin){
		LocalDateTime i = null;
		LocalDateTime f = null;
		if(inicio != null && fin != null) {
			i = inicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			f = fin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		}
		if(contacto instanceof ContactoIndividual) {
			return ((ContactoIndividual)contacto).buscarMensajes(texto, i, f);
		}else {
			return ((Grupo)contacto).buscarMensajes(texto, nombre, i, f);
		}
	}
	
	
	
	
	
	public void mostrarEstadisticas(Usuario user) {
		if(user.isPremium()) {
			user.mostrarEstadisticas();
		}
	}
	
	/*public void generarPDF(Usuario user) {
		if (user.isPremium()) {
			user.generarPDF();
		}
	}*/
	
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

	public LinkedList<String> getGruposComun(Usuario user, ContactoIndividual c) {
		LinkedList<String> gruposComun = new LinkedList<>();
		for (Contacto g : user.getContactos())
			if (g instanceof Grupo)
				if (c.getUsuario().getContactos().contains(g))
					gruposComun.add(g.getNombre());
		return gruposComun;
	}

	public boolean tlfValid(String tlf) {
		return tlf.length() == 9;
	}

	public LinkedList<Mensaje> mensajesConContacto(Contacto c) {
		return (LinkedList<Mensaje>) c.getMensajes();
	}
	
	public LinkedList<Grupo> gruposAdmin (Usuario user) {
		return (LinkedList<Grupo>) user.getGruposAdmin();
	}
}
