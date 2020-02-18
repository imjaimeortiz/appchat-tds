package vistas;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modelo.Contacto;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

public class Chats extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -585496333970497924L;
	private Contacto c;
	private String user;
	private JScrollPane scrollPane;
	private DefaultListModel<Contacto> listModel;
	/**
	 * Create the panel.
	 */
	public Chats(Contacto c, String user, JScrollPane scrollPane, DefaultListModel<Contacto> listModel) {
		this.c = c;
		this.user = user;
		this.scrollPane = scrollPane;
		this.listModel = listModel;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		initialize();
	}


	private void initialize() {
		
		this.setVisible(true);
	}	
}
