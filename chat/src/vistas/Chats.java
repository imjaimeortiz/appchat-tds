package vistas;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import tds.BubbleText;

import javax.swing.JScrollPane;

import modelo.Contacto;
import modelo.Mensaje;
import modelo.Usuario;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import controlador.ControladorChat;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Chats extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -585496333970497924L;
	private Contacto c;
	private Usuario user;
	private DefaultListModel<Contacto> listModel;
	private JPanel chat;
	private JTextArea textArea;
	
	/**
	 * Create the panel.
	 */
	public Chats(Contacto c, Usuario user, DefaultListModel<Contacto> listModel) {
		this.c = c;
		this.user = user;
		this.listModel = listModel;
		initialize();
	}


	private void initialize() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{50, 39, 0, 0, 52, 31, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridheight = 5;
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
		gbc_panelSend.fill = GridBagConstraints.BOTH;
		gbc_panelSend.gridx = 0;
		gbc_panelSend.gridy = 5;
		add(panelSend, gbc_panelSend);
		
		GridBagLayout gbl_panelSend = new GridBagLayout();
		gbl_panelSend.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelSend.rowHeights = new int[]{0, 0};
		gbl_panelSend.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelSend.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelSend.setLayout(gbl_panelSend);
		
		JButton btnEmoji = new JButton("");
		btnEmoji.setIcon(new ImageIcon(Chats.class.getResource("/vistas/smile.png")));

		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setLayout(new GridLayout(6, 4));
		addPopup(btnEmoji, popupMenu);
		btnEmoji.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});		
		
		for (int i = 0; i <= BubbleText.MAXICONO; i++) {
			final int emoji = i;
			JButton btn = new JButton();
			btn.setIcon(BubbleText.getEmoji(i));
			popupMenu.add(btn);
			btn.addActionListener(e -> {
				Mensaje m = ControladorChat.getUnicaInstancia().enviarMensaje(textArea.getText(), LocalDateTime.now(), emoji, user, c);
				mostrarMensaje(m, chat);
			});
		}
		
		
		GridBagConstraints gbc_btnEmoji = new GridBagConstraints();
		gbc_btnEmoji.fill = GridBagConstraints.VERTICAL;
		gbc_btnEmoji.anchor = GridBagConstraints.WEST;
		gbc_btnEmoji.insets = new Insets(0, 0, 0, 5);
		gbc_btnEmoji.gridx = 0;
		gbc_btnEmoji.gridy = 0;
		panelSend.add(btnEmoji, gbc_btnEmoji);
		//btnEmoji.add(popupMenu);
		
		
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
				if (!textArea.getText().equals(null)) {
					Mensaje m = ControladorChat.getUnicaInstancia().enviarMensaje(textArea.getText(), LocalDateTime.now(), -1, user, c);
					mostrarMensaje(m, chat);
					textArea.setText(null);
				}
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.VERTICAL;
		gbc_btnSend.anchor = GridBagConstraints.EAST;
		gbc_btnSend.gridx = 9;
		gbc_btnSend.gridy = 0;
		panelSend.add(btnSend, gbc_btnSend);
	
	}	
	
	private void mostrarMensaje(Mensaje m, JPanel chat) {
		BubbleText b ;
		if (m.getUsuario().equals(user))
			b = new BubbleText(chat, m.getTexto(), Color.GREEN, "Tú", BubbleText.SENT);
		else 
			b = new BubbleText(chat, m.getTexto(), Color.LIGHT_GRAY, c.getNombre(), BubbleText.RECEIVED);
		chat.add(b);
		LinkedList<Contacto> listAux = new LinkedList<Contacto>();
		for (int i = 0; i < listModel.getSize(); i++) {
			if (listModel.get(i).equals(c)) listAux.addFirst(c);
			else listAux.add(listModel.get(i));
		}
		listModel.clear();
		listAux.stream().forEach(c-> listModel.addElement(c));
	}
	
	public void pintarMensajes(LinkedList<Mensaje> mensajes) {
		mensajes.stream()
			.forEach(m->mostrarMensaje(m, chat));
		chat.repaint();
		chat.revalidate();
	}
	
	public void eliminarMensajes () {
		chat.removeAll();
		chat.repaint();
		chat.revalidate();
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
