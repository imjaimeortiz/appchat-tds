package vistas;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ShowContactsRenderer implements TableCellRenderer{

	/**
	 * 
	 */
	JLabel label;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
			ImageIcon icon = new ImageIcon(getClass().getResource((String) value));
		    label.setIcon(icon);
		    return label;
	}
	
}
