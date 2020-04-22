package controlador;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.text.DocumentException;

import cargador.CargadorMensajes;
import modelo.CatalogoUsuarios;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Descuento;
import modelo.DescuentoFecha;
import modelo.DescuentoMensajes;
import modelo.Grupo;
import modelo.Usuario;
import modelo.Mensaje;
import modelo.Plataforma;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoIndividualDAO;
import persistencia.IAdaptadorGrupoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;
import informacionUso.Graficos;

public class ControladorChat {

	private static ControladorChat unicaInstancia;
	public static Usuario usuarioActual = null;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoIndividualDAO adaptadorContactoIndividual;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private static Graficos graficos;

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

	// registrar un usuario
	public boolean addUsuario(String nombre, Date fechaNacimiento, String movil, String nick, String contrasena) {
		if (catalogoUsuarios.getUsuario(nick) == null && !catalogoUsuarios.existeTlf(movil)) {
			Usuario usuario = catalogoUsuarios.addUsuario(nombre, fechaNacimiento, movil, nick, contrasena);
			adaptadorUsuario.registrarUsuario(usuario);
			usuarioActual = usuario;
			return true;
		}
		// si ya existe un usuario con ese nick o ese movil
		return false;
	}

	// COMPROBAR USUARIO
	public boolean usuarioTrue(String nombre, String contraseña) {
		usuarioActual = recuperarUsuario(nombre);
		return catalogoUsuarios.usuarioTrue(nombre, contraseña);
	}

	public Usuario recuperarUsuario(String nick) {
		return catalogoUsuarios.getUsuario(nick);
	}

	// Obtener los contactos de un usuario
	public List<Contacto> recuperarContactos(Usuario user) {
		return user.recuperarContactos(user);
	}

	public List<Contacto> recuperarContactosIndividuales(Usuario user) {
		List<Contacto> contactos = recuperarContactos(user).stream().filter(c -> (c instanceof ContactoIndividual))
				.collect(Collectors.toList());
		return contactos;

	}

	// comprobar si existe el tlf
	public boolean existeTlf(String movil) {
		return catalogoUsuarios.existeTlf(movil);
	}

	// añadir un contacto
	public void addContacto(Usuario usuario, String movil, String nombre) {
		Usuario u = catalogoUsuarios.buscarUsuarioDelMovil(movil);
		ContactoIndividual contactoi = usuario.addContacto(nombre, movil, u);
		adaptadorContactoIndividual.registrarContactoIndividual(contactoi);
		adaptadorUsuario.modificarUsuario(usuario);

	}

	// crear un grupo
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

	// eliminar un grupo
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

	// abandonar un grupo
	public void abandonarGrupo(Usuario usuario, Grupo g) {
		usuario.abandonarGrupo(g);
		usuario.removeGrupo(g);
		adaptadorUsuario.modificarUsuario(usuario);
		adaptadorGrupo.modificarGrupo(g);
	}

	// añadir contactos a un grupo
	public void agregarContactosGrupo(Grupo group, List<ContactoIndividual> nuevos) {
		group.addContactos(nuevos);
		adaptadorGrupo.modificarGrupo(group);
		catalogoUsuarios.modificarUsuarios(nuevos);
	}

	// eliminar contactos de un grupo
	public void eliminarContactosGrupo(Grupo group, List<ContactoIndividual> eliminados) {
		group.removeContactos(eliminados);
		adaptadorGrupo.modificarGrupo(group);
		catalogoUsuarios.modificarUsuarios(eliminados);
	}

	// cambiar nombre del grupo
	public void actualizarNombreGrupo(Grupo group, String text) {
		group.setNombre(text);
		adaptadorGrupo.modificarGrupo(group);
	}

	public List<ContactoIndividual> miembrosGrupo(Grupo g) {
		return g.getContactos();
	}

	public Mensaje enviarMensaje(String texto, LocalDateTime localDate, int emoticono, Usuario emisor, Contacto receptor) {

		// guardar el mensaje en el contacto receptor del emisor
		Mensaje mensaje = emisor.enviarMensajeEmisor(texto, localDate, emoticono, emisor, receptor);
		Mensaje mensaje1;

		if (receptor instanceof ContactoIndividual) {
			ContactoIndividual contactoDelEmisorEnElReceptor = (ContactoIndividual) ((ContactoIndividual) receptor)
					.getUsuario().buscarEmisor(emisor);

			if (contactoDelEmisorEnElReceptor == null) {
				// si el receptor no tiene el contacto del emisor se crea
				contactoDelEmisorEnElReceptor = new ContactoIndividual(emisor.getMovil(), emisor.getMovil(), emisor);
				((ContactoIndividual) receptor).getUsuario().addContacto(contactoDelEmisorEnElReceptor);
				adaptadorContactoIndividual.registrarContactoIndividual(contactoDelEmisorEnElReceptor);
				adaptadorUsuario.modificarUsuario(((ContactoIndividual) receptor).getUsuario());
			}
			// guardar mensaje en el contacto emisor del receptor
			mensaje1 = ((ContactoIndividual) receptor).getUsuario().recibirMensaje(texto, localDate, emoticono, emisor,
					(ContactoIndividual) receptor, contactoDelEmisorEnElReceptor);

			adaptadorContactoIndividual.modificarContactoIndividual(contactoDelEmisorEnElReceptor);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorMensaje.registrarMensaje(mensaje1);
			adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) receptor);

		} else {

			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorGrupo.modificarGrupo((Grupo) receptor);
		}
		return mensaje;

	}

	public double setPrecioFinal(Usuario user) {
		double pago = 29.99;
		LocalDate fechaDescuento = LocalDate.now().minusMonths(4);
		if (user.getMensajesEnviadosUltimoMes() > 50) {
			Descuento desc1 = new DescuentoMensajes();
			return pago - desc1.getDescuento();
		} else if (user.getFechaRegistro().isBefore(fechaDescuento)) {
			Descuento desc = new DescuentoFecha();
			return pago - desc.getDescuento();
		}

		return pago;
	}

	public void setPremium(Usuario user) {
		user.setPremium(true);
		adaptadorUsuario.modificarUsuario(user);
	}

	// busqueda de mensajes por todos los parametros
	public List<Mensaje> buscarMensaje(Contacto contacto, String nombre, String texto, Date inicio, Date fin) {
		LocalDateTime i = null;
		LocalDateTime f = null;

		if (inicio != null && fin != null) {
			i = inicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			f = fin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		}
		if (contacto instanceof ContactoIndividual) {
			return contacto.buscarMensajes(texto, i, f);
		} else {
			return contacto.buscarMensajes(texto, nombre, i, f);
		}
	}

	public void mostrarEstadisticas(Usuario user) {
		if (user.isPremium()) {
			try {
				graficos.getPngChart(user.getMensajesAno());
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				graficos.getPngChartTarta(user.getGruposConMasMensajes());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void generarPDF(Usuario user)  {
		if (user.isPremium()) {
			try {
				user.informacionPdf();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (DocumentException e) {

				e.printStackTrace();
			}
		}
	}

	 public void cargarMensajes(String file, Plataforma SO, int tipo) {
		CargadorMensajes cargador = new CargadorMensajes();
		CargadorMensajesListener cargadorListener = new CargadorMensajesListener();
		cargador.addMensajeListener(cargadorListener);
		cargador.convertirMensajes(file, SO, tipo);
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

	public LinkedList<Grupo> gruposAdmin(Usuario user) {
		return (LinkedList<Grupo>) user.getGruposAdmin();
	}

	public void borrarMensajes(Usuario user, Contacto contactoSelected) {
		contactoSelected.getMensajes().clear();
	}

	public void cambiarNombreContacto(Usuario user, Contacto contacto, String nombre) {
		contacto.setNombre(nombre);
		if (contacto instanceof ContactoIndividual) {
			adaptadorContactoIndividual.modificarContactoIndividual((ContactoIndividual) contacto);
			adaptadorUsuario.modificarUsuario(user);
		} 
		
		else {
			adaptadorGrupo.modificarGrupo((Grupo) contacto);
			for (ContactoIndividual c : ((Grupo) contacto).getContactos()) {
				adaptadorUsuario.modificarUsuario(c.getUsuario());
			}
		}
	}

}
