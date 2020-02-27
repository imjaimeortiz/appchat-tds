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
import javax.swing.JScrollPane;

public class VentanaMostrarContactos {

	private JFrame frame;
	private Usuario user;
	private JTable table;
	
	/**
	 * Create the application.
	 */
	public VentanaMostrarContactos(Usuario user) {
		this.user = user;
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
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();

		table = new JTable();
		table.setModel(tableModel);
		table.setEnabled(false);
		table.getColumn("Imagen").setCellRenderer(new ShowContactsRenderer());
		scrollPane.setViewportView(table);
		
		LinkedList<ContactoIndividual>contactos = ControladorChat.getUnicaInstancia().recuperarContactosIndividuales(user);
		
		for (int i = 0; i < contactos.size(); i++) {
			Vector<Object> vector = new Vector<Object>();
			ContactoIndividual c = (ContactoIndividual)contactos.get(i);
			vector.add(c.getNombre());
			vector.add(c.getFoto());
			vector.add(ControladorChat.getUnicaInstancia().getGruposComun(user, c));
			tableModel.insertRow(i, vector);
		}
		
	}

}
