package vistas;

import javax.swing.JPanel;
import tds.BubbleText;

import javax.swing.JScrollPane;

import modelo.Contacto;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

public class Chats extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -585496333970497924L;
	private Contacto c;
	private String user;
	private JScrollPane scrollPaneContacts;
	private DefaultListModel<Contacto> listModel;
	private JPanel chat;
	
	/**
	 * Create the panel.
	 */
	public Chats(Contacto c, String user, JScrollPane scrollPaneContacts, DefaultListModel<Contacto> listModel) {
		this.c = c;
		this.user = user;
		this.scrollPaneContacts = scrollPaneContacts;
		this.listModel = listModel;
		this.chat = new JPanel();
		
		initialize();
	}


	private void initialize() {
				
		JScrollPane scrollPane = new JScrollPane();
		
		chat.setLayout(new BoxLayout(chat,BoxLayout.Y_AXIS));
		
		add(scrollPane);
		scrollPane.setViewportView(chat);
		this.setVisible(true);
		chat.setVisible(true);
	}	
	
	public void mostrarMensaje(String mensaje) {
		BubbleText b = new BubbleText(chat, mensaje, Color.GREEN, "Tú", BubbleText.SENT);
		System.out.println(mensaje);
		chat.add(b);
	}
}
