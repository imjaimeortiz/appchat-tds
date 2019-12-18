package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import modelo.ContactoIndividual;
import modelo.Usuario;
import java.awt.FlowLayout;
import java.util.LinkedList;

import javax.swing.JTable;

import controlador.ControladorChat;

public class VentanaMostrarContactos {

	private JFrame frame;
	private Usuario user;
	private JTable table;
	private LinkedList<ContactoIndividual> contactos;
	
	/**
	 * Create the application.
	 */
	public VentanaMostrarContactos(Usuario user) {
		this.user = user;
		this.contactos = new LinkedList<ContactoIndividual>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		contactos = ControladorChat.getUnicaInstancia().recuperarContactos(user);
		
		table = new JTable(contactos.size(), 4);
		frame.getContentPane().add(table);
		
	}

}
