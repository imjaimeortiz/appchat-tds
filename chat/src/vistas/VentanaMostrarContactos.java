package vistas;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Usuario;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.ControladorChat;
import java.awt.GridLayout;

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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.addColumn("Nombre");
		tableModel.addColumn("Teléfono");
		tableModel.addColumn("Imagen");
		tableModel.addColumn("Grupos");
		
		List<Contacto> contactos = ControladorChat.getUnicaInstancia().recuperarContactosIndividuales(user);
		
		for (Contacto contactoIndividual : contactos) {
			tableModel.addRow(new Object[] { contactoIndividual.getNombre(), ((ContactoIndividual) contactoIndividual).getMovil(), ((ContactoIndividual) contactoIndividual).getUsuario().getImagen(), ControladorChat.getUnicaInstancia().getGruposComun(user, (ContactoIndividual) contactoIndividual) });
		}
		
		table = new JTable();
		table.setModel(tableModel);
		table.setEnabled(false);
		table.getColumn("Imagen").setCellRenderer(new ShowContactsRenderer());
		scrollPane.setViewportView(table);
		
	}

}
