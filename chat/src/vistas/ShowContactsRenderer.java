package vistas;

import java.awt.Component;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import controlador.ControladorChat;
import modelo.ContactoIndividual;

public class ShowContactsRenderer implements TableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6930805152259892543L;
	JLabel label;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		this.label = new JLabel();
		label.setIcon(resize(new ImageIcon((String)value)));
	
		return label;
	}

	private ImageIcon resize(ImageIcon imageIcon) {
		int h = imageIcon.getIconHeight();
		int w = imageIcon.getIconWidth();
		Image img = imageIcon.getImage();
		Image newImg = imageIcon.
	}

}
