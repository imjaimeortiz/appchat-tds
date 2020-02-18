package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;
import java.awt.FlowLayout;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.ControladorChat;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class VentanaMostrarContactos {

	private JFrame frame;
	private Usuario user;
	private JTable table;
	private DefaultTableModel tableModel;
	private LinkedList<Contacto> contactos;
	
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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contactos = ControladorChat.getUnicaInstancia().recuperarContactos(user);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{67, 300, 0};
		gridBagLayout.rowHeights = new int[]{1, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		DefaultTableModel tableModel;
		JTable table;
		LinkedList<Contacto> contactos = ControladorChat.getUnicaInstancia().recuperarContactos(user);
		for (Contacto contacto : contactos) {
			if (contacto instanceof Grupo) contactos.remove(contacto);
		}
		ContactoIndividual[] arraycontactos = (ContactoIndividual[]) contactos.toArray();
		tableModel = new DefaultTableModel(arraycontactos, 4);
		
		for (Contacto c : user.getContactos()) {
			System.out.println(c.getNombre());
		}
				
	}

}
