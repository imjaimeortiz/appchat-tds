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
	
	/*private ImageIcon redimensionar(ImageIcon imagen) {
		int alto = imagen.getIconHeight();
		int ancho = imagen.getIconWidth();
		int nuevoAncho = 20;
		int nuevoAlto = (alto * nuevoAncho) / ancho;
		Image image = imagen.getImage();
		Image nueva = image.getScaledInstance(nuevoAncho, nuevoAlto, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(nueva);
	}
*/
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		label = new JLabel();
		label.setIcon(new ImageIcon((String) value));
		//label.setIcon(redimensionar(new ImageIcon((String) value)));
		return label;
	}
	
}
