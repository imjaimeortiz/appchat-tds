package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;
import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.ControladorChat;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;

public class VentanaMostrarContactos {

	private JFrame frame;
	private Usuario user;
	private JTable table;
	private DefaultTableModel tableModel;
	private LinkedList<Contacto> contactos;
	private JTable table_1;
	
	/**
	 * Create the application.
	 */
	public VentanaMostrarContactos(Usuario user) {
		this.user = user;
		this.contactos = new LinkedList<Contacto>();
		for (Contacto contacto : ControladorChat.getUnicaInstancia().getTodosContactos(user)) {
			contactos.add(contacto);
		}
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		table_1 = new JTable();
		frame.getContentPane().add(table_1);
		
		contactos = ControladorChat.getUnicaInstancia().recuperarContactos(user);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		LinkedList<Contacto> contactos = ControladorChat.getUnicaInstancia().recuperarContactos(user);
		for (Contacto contacto : contactos) {
			if (contacto instanceof Grupo) contactos.remove(contacto);
		}
		for (int i = 0; i < contactos.size(); i++) {
			Vector<Object> vector = new Vector<Object>();
			ContactoIndividual c = (ContactoIndividual)contactos.get(i);
			vector.add(c.getNombre());
			vector.add(c.getFoto());
			vector.add(ControladorChat.getUnicaInstancia().getGruposComun(user, c));
			tableModel.insertRow(i, vector);
		}
		table_1.getColumnModel().getColumn(1).setCellRenderer(new ShowContactsRenderer());
		table_1.setModel(tableModel);	
	}

}
