package vistas;

import javax.swing.JPanel;
import tds.BubbleText;

import javax.swing.JScrollPane;

import modelo.Contacto;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

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
		
		initialize();
		this.setVisible(true);

	}


	private void initialize() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 219, 0};
		gridBagLayout.rowHeights = new int[]{100, 50, 50, 50, 50, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);
		
		chat = new JPanel();
		scrollPane.setViewportView(chat);
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 6;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 4;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton btnEmoji = new JButton("");
		btnEmoji.setIcon(new ImageIcon(Chats.class.getResource("/vistas/smile.png")));
		GridBagConstraints gbc_btnEmoji = new GridBagConstraints();
		gbc_btnEmoji.fill = GridBagConstraints.VERTICAL;
		gbc_btnEmoji.anchor = GridBagConstraints.WEST;
		gbc_btnEmoji.insets = new Insets(0, 0, 0, 5);
		gbc_btnEmoji.gridx = 0;
		gbc_btnEmoji.gridy = 0;
		panel.add(btnEmoji, gbc_btnEmoji);
		
		JTextArea textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 8;
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 0;
		panel.add(textArea, gbc_textArea);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String m = textArea.getText();
				BubbleText b = new BubbleText(chat, m, Color.GREEN, "Tú", BubbleText.SENT);
				chat.add(b);
				textArea.setText(null);
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.fill = GridBagConstraints.VERTICAL;
		gbc_btnSend.anchor = GridBagConstraints.EAST;
		gbc_btnSend.gridx = 9;
		gbc_btnSend.gridy = 0;
		panel.add(btnSend, gbc_btnSend);
	}	
}
