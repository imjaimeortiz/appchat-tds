package vistas;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import tds.BubbleText;

import javax.swing.JScrollPane;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Mensaje;
import modelo.Usuario;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import controlador.ControladorChat;

public class Chats extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -585496333970497924L;
	private Contacto c;
	private Usuario user;
	private JScrollPane scrollPaneContacts;
	private DefaultListModel<Contacto> listModel;
	private JPanel chat;
	private JPopupMenu popupMenu;
	private JTextArea textArea;
	
	/**
	 * Create the panel.
	 */
	public Chats(Contacto c, Usuario user, JScrollPane scrollPaneContacts, DefaultListModel<Contacto> listModel) {
		this.c = c;
		this.user = user;
		this.scrollPaneContacts = scrollPaneContacts;
		this.listModel = listModel;
		
		initialize();
		this.setVisible(true);

	}


	private void initialize() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 219, 0};
		gridBagLayout.rowHeights = new int[]{100, 50, 50, 30, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		chat = new JPanel();
		scrollPane.setViewportView(chat);
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		
		textArea = new JTextArea();
		
		JPanel panelSend = new JPanel();
		GridBagConstraints gbc_panelSend = new GridBagConstraints();
		gbc_panelSend.gridwidth = 6;
		gbc_panelSend.fill = GridBagConstraints.BOTH;
		gbc_panelSend.gridx = 0;
		gbc_panelSend.gridy = 3;
		add(panelSend, gbc_panelSend);
		
		GridBagLayout gbl_panelSend = new GridBagLayout();
		gbl_panelSend.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelSend.rowHeights = new int[]{0, 0};
		gbl_panelSend.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelSend.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelSend.setLayout(gbl_panelSend);
		
		JButton btnEmoji = new JButton("");
		btnEmoji.setIcon(new ImageIcon(Chats.class.getResource("/vistas/smile.png")));
		GridBagConstraints gbc_btnEmoji = new GridBagConstraints();
		gbc_btnEmoji.fill = GridBagConstraints.VERTICAL;
		gbc_btnEmoji.anchor = GridBagConstraints.WEST;
		gbc_btnEmoji.insets = new Insets(0, 0, 0, 5);
		gbc_btnEmoji.gridx = 0;
		gbc_btnEmoji.gridy = 0;
		panelSend.add(btnEmoji, gbc_btnEmoji);
		btnEmoji.add(popupMenu);
		for (int i = 0; i <= BubbleText.MAXICONO; i++) {
			final int emoji = i;
			JButton btn = new JButton();
			btn.setIcon(BubbleText.getEmoji(i));
			popupMenu.add((Action) BubbleText.getEmoji(i));
			btn.addActionListener(e -> {
				ControladorChat.getUnicaInstancia().enviarMensaje(textArea.getText(), LocalDateTime.now(), emoji, user, c);
				mostrarMensaje(chat);
			});
		}
		
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 8;
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 0;
		panelSend.add(textArea, gbc_textArea);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mostrarMensaje(chat);
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.VERTICAL;
		gbc_btnSend.anchor = GridBagConstraints.EAST;
		gbc_btnSend.gridx = 9;
		gbc_btnSend.gridy = 0;
		panelSend.add(btnSend, gbc_btnSend);
	}	
	
	private void mostrarMensaje(JPanel chat) {
		String m = textArea.getText();
		BubbleText b = new BubbleText(chat, m, Color.GREEN, "Tú", BubbleText.SENT);
		chat.add(b);
		textArea.setText(null);
	}
}
