package modelo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Contacto {
	private int codigo;
	private String nombre;
	private List<Mensaje> mensajes;
	
	
	public Contacto(String nombre) {
		codigo = 0;
		this.nombre = nombre;
		this.mensajes = new LinkedList<Mensaje>();
	}
	
	public Contacto(String nombre, List<Mensaje> mensajes) {
		this(nombre);
		this.mensajes = mensajes;
		
	}
	
	

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public List<Mensaje> getMensajes() {
		return mensajes;
	}
	
	public int getNumMensajes() {
		return mensajes.size();
	}
	
	public Mensaje addMensaje(String texto, LocalDateTime localDate, int emoticono, Usuario usuario, Contacto c) {
		Mensaje mensaje = new Mensaje(texto, localDate, emoticono, usuario, c);
		mensajes.add(mensaje);
		return mensaje;
	}
	
	public void addMensaje(Mensaje mensaje) {
		mensajes.add(mensaje);
	}
	
	public int getMensajesEnviados(Usuario user) {
		Month now = LocalDateTime.now().getMonth();
		int cont = 0;
		for(Mensaje m : mensajes) {
			if((m.getUsuario().equals(user))&& (m.getHora().getMonth().equals(now))){
				cont ++;
			}
		}
		return cont;
	}
	
	public void mensajesCadaMes(Integer[]mensajesMes, Usuario usuario) {
		for(Mensaje m : mensajes) {
			Integer year = LocalDateTime.now().getYear();
			if((m.getUsuario().equals(usuario)) && (year.equals(m.getHora().getYear()))) {
				switch(m.getHora().getMonthValue()) {
				case 1: mensajesMes[0]= mensajesMes[0]++; break;
				case 2: mensajesMes[1]= mensajesMes[1]++; break;
				case 3: mensajesMes[2]= mensajesMes[2]++; break;
				case 4: mensajesMes[3]= mensajesMes[3]++; break;
				case 5: mensajesMes[4]= mensajesMes[4]++; break;
				case 6: mensajesMes[5]= mensajesMes[5]++; break;
				case 7: mensajesMes[6]= mensajesMes[6]++; break;
				case 8: mensajesMes[7]= mensajesMes[7]++; break;
				case 9: mensajesMes[8]= mensajesMes[8]++; break;
				case 10: mensajesMes[9]= mensajesMes[9]++; break;
				case 11: mensajesMes[10]= mensajesMes[10]++; break;
				case 12: mensajesMes[11]= mensajesMes[11]++; break;
				}
				
			}
		}
	}
	
	//busqueda para contactos individuales por texto y rango de fechas
	public List<Mensaje> buscarMensajes(String texto, LocalDateTime inicio, LocalDateTime fin){
		List<Mensaje> mensajesEncontrados = mensajes.stream().filter(m -> ((m.getTexto().contains(texto))&&(m.getHora().isAfter(inicio)) && (m.getHora().isBefore(fin))))
				.collect(Collectors.toList());
		return mensajesEncontrados;
	}
	
	//busqueda de mensajes para grupos
	public List<Mensaje> buscarMensajes(String texto, String nombre, LocalDateTime inicio, LocalDateTime fin){
		List<Mensaje> mensajesEncontrados = new LinkedList<Mensaje>();
		int opcion = 0;
		if((texto == null) && (nombre != null) && (inicio != null)) opcion = 1; // busqueda sin texto
		if((nombre == null) && (texto != null) && (inicio != null)) opcion = 2; // busqueda sin nombre
		if((inicio == null) && (texto != null) && (nombre != null)) opcion = 3; // busqueda sin rango de fechas
		if((texto != null) && (nombre == null) && (inicio == null)) opcion = 4; // busqueda solo por texto
		if((nombre != null) && (texto == null) && (inicio == null)) opcion = 5; // busqueda solo por nombre
		if((inicio != null) && (texto == null) && (nombre == null)) opcion = 6; // busqueda solo por rango de fechas
		
		for(Mensaje m : mensajes) {
			switch(opcion) {
			case 0: 
				if((m.getTexto().contains(texto)) && (m.getUsuario().getNombre().equals(nombre)) && (m.getHora().isAfter(inicio)) && (m.getHora().isBefore(fin))) {
					mensajesEncontrados.add(m);
				}
				break;
			case 1:
				if((m.getUsuario().getNombre().equals(nombre)) && (m.getHora().isAfter(inicio)) && (m.getHora().isBefore(fin))) {
					mensajesEncontrados.add(m);
				}
				break;
			case 2: 
				if((m.getTexto().contains(texto)) && (m.getHora().isAfter(inicio)) && (m.getHora().isBefore(fin))) {
					mensajesEncontrados.add(m);
				}
				break;
			case 3:
				if((m.getTexto().contains(texto)) && (m.getUsuario().getNombre().equals(nombre))) {
					mensajesEncontrados.add(m);
				}
				break;
			case 4:
				if((m.getTexto().contains(texto))) {
					mensajesEncontrados.add(m);
				}
				break;
			case 5:
				if( (m.getUsuario().getNombre().equals(nombre))) {
					mensajesEncontrados.add(m);
				}
				break;
				
			case 6:
				if((m.getHora().isAfter(inicio)) && (m.getHora().isBefore(fin))) {
					mensajesEncontrados.add(m);
				}
				break;
			
			}
		}
		
		return mensajesEncontrados;
	}
	
	
	

}
