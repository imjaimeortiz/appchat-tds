package vistas;

import java.awt.Component;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import controlador.ControladorChat;
import modelo.ContactoIndividual;

public class ShowContactsRenderer implements TableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6930805152259892543L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		LinkedList<ContactoIndividual> contactos = ControladorChat.getUnicaInstancia().recuperarContactos();		
		
		
		return this;
	}

}
