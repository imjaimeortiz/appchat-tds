package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JMenuItem;

import java.awt.GridBagLayout;
import javax.swing.JTextField;

import modelo.Contacto;
import modelo.Grupo;
import modelo.Usuario;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NuevaVentanaChat {

	private JFrame frame;
	private JTextField textField;
	private JButton btnUser;
	private JButton btnSearch;
	private JButton btnMenu;
	private JButton btnContact;
	private JButton btnDelete;
	private JButton btnOptions;
	private JScrollPane panelText;
	private JScrollPane panelContacts;
	private JComboBox<Grupo> gruposAdmin;
	
	private Usuario user;
	private Grupo group;
	
	/**
	 * Create the application.
	 */
	public NuevaVentanaChat(Usuario user) {
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{72, 72, 72, 100, 72, 72, 0};
		gbl_panel.rowHeights = new int[]{25, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnUser = new JButton("");
		btnUser.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/avatar.png")));
		GridBagConstraints gbc_btnUser = new GridBagConstraints();
		gbc_btnUser.fill = GridBagConstraints.BOTH;
		gbc_btnUser.insets = new Insets(0, 0, 0, 5);
		gbc_btnUser.gridx = 0;
		gbc_btnUser.gridy = 0;
		panel.add(btnUser, gbc_btnUser);
		
		final JPopupMenu popupMenu = new JPopupMenu();
		btnMenu = new JButton("");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnMenu.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/menu.png")));
		addPopup(btnMenu, popupMenu);
		
		JMenuItem mitemCrearContacto = new JMenuItem("Crear contacto");
		mitemCrearContacto.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					new VentanaCrearContacto(user);
					}
			});
		JMenuItem mitemMostrarContacto = new JMenuItem("Mostrar contacto");
		JMenuItem mitemCrearGrupo = new JMenuItem("Crear grupo");
		mitemCrearGrupo.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					new VentanaGrupo(user);
			}
		});
		JMenuItem mitemModificarGrupo = new JMenuItem("Modificar grupo");
		mitemModificarGrupo.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gruposAdmin.setVisible(true);
				gruposAdmin.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						group = (Grupo) gruposAdmin.getSelectedItem();
						new VentanaGrupo(group);
					}
				});
			}
		});
		JMenuItem mitemEstadisticas = new JMenuItem("Mostrar estadísticas");
		JMenuItem mitemPremium = new JMenuItem("Ir a premium");
		JMenuItem mitemCerrarSesion = new JMenuItem("Cerrar sesión");
		mitemCerrarSesion.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					frame.dispose();
					new VentanaInicio();
			}
			
		});
		popupMenu.add(mitemCrearContacto);
		mitemCrearContacto.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaCrearContacto(user);
			}
		});
		popupMenu.add(mitemMostrarContacto);
		mitemMostrarContacto.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaMostrarContactos(user);
			}
		});
		popupMenu.add(mitemCrearGrupo);
		mitemCrearGrupo.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaGrupo(user);
			}
		});
		
		btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/chat.png")));
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 0;
		panel.add(btnSearch, gbc_btnSearch);
		popupMenu.add(mitemModificarGrupo);
		popupMenu.add(mitemEstadisticas);
		popupMenu.add(mitemPremium);
		popupMenu.add(mitemCerrarSesion);
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.fill = GridBagConstraints.BOTH;
		gbc_btnMenu.insets = new Insets(0, 0, 0, 5);
		gbc_btnMenu.gridx = 2;
		gbc_btnMenu.gridy = 0;
		panel.add(btnMenu, gbc_btnMenu);
		
		btnContact = new JButton("Name");
		btnContact.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/avatar.png")));
		GridBagConstraints gbc_btnContact = new GridBagConstraints();
		gbc_btnContact.fill = GridBagConstraints.BOTH;
		gbc_btnContact.insets = new Insets(0, 0, 0, 5);
		gbc_btnContact.gridx = 3;
		gbc_btnContact.gridy = 0;
		panel.add(btnContact, gbc_btnContact);
		
		btnDelete = new JButton("");
		btnDelete.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/delete-button.png")));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.fill = GridBagConstraints.BOTH;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 4;
		gbc_btnDelete.gridy = 0;
		panel.add(btnDelete, gbc_btnDelete);
		
		btnOptions = new JButton("");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnOptions.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/menu.png")));
		GridBagConstraints gbc_btnOptions = new GridBagConstraints();
		gbc_btnOptions.fill = GridBagConstraints.BOTH;
		gbc_btnOptions.gridx = 5;
		gbc_btnOptions.gridy = 0;
		panel.add(btnOptions, gbc_btnOptions);
		
		gruposAdmin = new JComboBox<Grupo>();
		for (Grupo g : user.getGruposAdmin()) {
			gruposAdmin.addItem(g);	
		}
		
		JSplitPane splitPane = new JSplitPane();
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panelChat = new JPanel();
		splitPane.setRightComponent(panelChat);
		panelChat.setLayout(new BorderLayout(0, 0));
		
		JPanel panelWrite = new JPanel();
		panelChat.add(panelWrite, BorderLayout.SOUTH);
		GridBagLayout gbl_panelWrite = new GridBagLayout();
		gbl_panelWrite.columnWidths = new int[]{50, 0, 0, 0, 134, 134, 0, 0, 0, 0, 0, 0};
		gbl_panelWrite.rowHeights = new int[]{25, 0};
		gbl_panelWrite.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelWrite.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelWrite.setLayout(gbl_panelWrite);
		
		JButton btnEmoji = new JButton("");
		btnEmoji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEmoji.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/smile.png")));
		GridBagConstraints gbc_btnEmoji = new GridBagConstraints();
		gbc_btnEmoji.anchor = GridBagConstraints.WEST;
		gbc_btnEmoji.fill = GridBagConstraints.VERTICAL;
		gbc_btnEmoji.insets = new Insets(0, 0, 0, 5);
		gbc_btnEmoji.gridx = 1;
		gbc_btnEmoji.gridy = 0;
		panelWrite.add(btnEmoji, gbc_btnEmoji);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 7;
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panelWrite.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Enviar");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnSend = new GridBagConstraints();
		gbc_btnSend.insets = new Insets(0, 0, 0, 5);
		gbc_btnSend.anchor = GridBagConstraints.EAST;
		gbc_btnSend.fill = GridBagConstraints.VERTICAL;
		gbc_btnSend.gridx = 9;
		gbc_btnSend.gridy = 0;
		panelWrite.add(btnSend, gbc_btnSend);
		
		panelText = new JScrollPane();
		panelChat.add(panelText, BorderLayout.CENTER);
		
		panelContacts = new JScrollPane();
		splitPane.setLeftComponent(panelContacts);
		
		DefaultListModel<Chats> listModel = new DefaultListModel<Chats>();
		
		for (Contacto c : user.getContactos()) {
			Chats chat = new Chats(c.getNombre(), null, null);
			listModel.addElement(chat);
		}
		
	    final JList<Chats> list = new JList<Chats>(listModel);

		panelContacts.add(list);
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
