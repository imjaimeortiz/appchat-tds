package modelo;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;

public class Test {
	Usuario usuario;
	Usuario usuario1;
	ContactoIndividual contacto;
	
	@Before
	public void crearUsuario() {
		usuario = new Usuario("Pepe", Date.valueOf(LocalDate.of(1998, 4, 2)), "696140537", "pepeg", "11");
		usuario1 = new Usuario("Juan", Date.valueOf(LocalDate.of(1998, 4, 2)), "602548792", "juanpe", "11");
		contacto = new ContactoIndividual("Juan", "602548792", usuario1);
	}
	

	@org.junit.Test
	public void testAnadirContacto() {
		ContactoIndividual c = usuario.addContacto("Juan", "602548792", usuario1);
		assertEquals(c.getMovil(), contacto.getMovil());
		
	}
	@org.junit.Test
	public void testListaContactos() {
		usuario.addContacto(contacto);
		assertEquals(usuario.getContactos().size(), 1);
		
	}
	
	
	@org.junit.Test
	public void testAnadirGrupo() {
		LinkedList<ContactoIndividual> miembros = new LinkedList<ContactoIndividual>();
		miembros.add(contacto);
		usuario.addGrupo("Grupo", miembros);
		assertEquals(usuario.getGruposAdmin().size(), 1);
		
	}
	
	@org.junit.Test
	public void testPremium() {
		usuario.setPremium(true);
		assertTrue(usuario.isPremium());
	}
	
	@org.junit.Test
	public void testEliminarDelgrupo() {
		LinkedList<ContactoIndividual> miembros = new LinkedList<ContactoIndividual>();
		miembros.add(contacto);
		usuario.addGrupo("Grupo", miembros);
		Grupo g = usuario.getGruposAdmin().get(0);
		g.removeContacto(contacto);
		assertEquals(g.getContactos().size(), 0);
		
	}
	@org.junit.Test
	public void testEnviarMensaje() {
		usuario.enviarMensajeEmisor("hola", LocalDateTime.of(2018, 10, 10, 11, 25), 0, usuario, contacto);
		assertEquals(contacto.getMensajes().size(), 1);
		
	}
	
	@org.junit.Test
	public void testBorrarContacto() {
		usuario.addContacto(contacto);
		usuario.removeContacto(contacto);
		assertTrue(usuario.getContactos().isEmpty());
		
	}
	

}
