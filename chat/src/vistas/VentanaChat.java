package vistas;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.ControladorChat;
import modelo.Grupo;
import modelo.Usuario;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class VentanaChat extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5012959026523213732L;
	private JFrame frame;
	private JButton btnUser;
	private JButton btnDelete;
	private JButton btnOptions;
	private JButton btnSearch;
	private JTextField textField;
	private JScrollPane scrollPaneChat;
	private JScrollPane scrollPane;
	@SuppressWarnings("unused")
	private int textAreaIndex;
	private Grupo group;
	private Usuario user;
	
	/**
	 * Create the application.
	 */
	// Deberíamos pasarle el usuario propietario de este chat para poder obtener los contactos
	public VentanaChat(Usuario user) {
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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		scrollPaneChat = new JScrollPane();
		scrollPaneChat.setBounds(218, 24, 217, 211);
		frame.getContentPane().add(scrollPaneChat);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 435, 25);
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{5, 0, 49, 49, 140, 49, 49, 0, 0};
		gbl_panel.rowHeights = new int[]{25, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		final JPopupMenu popupMenu = new JPopupMenu();
		final JButton btnMenu = new JButton("");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnMenu.setIcon(new ImageIcon(VentanaChat.class.getResource("/vistas/menu.png")));
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
					new VentanaGrupo(null);
			}
		});
			
		/*final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setVisible(false);
		comboBox.addItem("grupo1");
		comboBox.addItem("grupo2");
		comboBox.addItem("grupo3");
		scrollPane.setColumnHeaderView(comboBox);
		*/
		JMenuItem mitemModificarGrupo = new JMenuItem("Modificar grupo");
		mitemModificarGrupo.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//comboBox.setVisible(true);
				// mostrar en un comboBox los grupos creados
				final JComboBox<Grupo> comboBox = new JComboBox<Grupo>();
				//comboBox.addItem(new Grupo("grupo1"));
				//comboBox.addItem(new Grupo("grupo2"));
				//comboBox.addItem(new Grupo("grupo3"));
				scrollPane.setColumnHeaderView(comboBox);
				//frame.getContentPane().add(comboBox);
				comboBox.addMouseListener( new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						if (arg0.getClickCount() == 1) {
							int index = comboBox.getSelectedIndex();
							if (index >= 0) {
								group = comboBox.getModel().getElementAt(index);
							}
						}
					}
				});
				new VentanaGrupo(group);
				comboBox.setVisible(false);
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
		popupMenu.add(mitemModificarGrupo);
		popupMenu.add(mitemEstadisticas);
		popupMenu.add(mitemPremium);
		popupMenu.add(mitemCerrarSesion);
				
		btnMenu.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
				
		btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon(VentanaChat.class.getResource("/vistas/chat.png")));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
				
		btnUser = new JButton("");
		btnUser.setIcon(new ImageIcon(VentanaChat.class.getResource("/vistas/avatar.png")));
		btnUser.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaUser(user);
			}
		});
		GridBagConstraints gbc_btnUser = new GridBagConstraints();
		gbc_btnUser.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnUser.insets = new Insets(0, 0, 0, 5);
		gbc_btnUser.gridx = 1;
		gbc_btnUser.gridy = 0;
		panel.add(btnUser, gbc_btnUser);	
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 0;
		panel.add(btnSearch, gbc_btnSearch);
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnMenu.insets = new Insets(0, 0, 0, 5);
		gbc_btnMenu.gridx = 3;
		gbc_btnMenu.gridy = 0;
		panel.add(btnMenu, gbc_btnMenu);
						
		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnDelete.setIcon(new ImageIcon(VentanaChat.class.getResource("/vistas/delete-button.png")));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 6;
		gbc_btnDelete.gridy = 0;
		panel.add(btnDelete, gbc_btnDelete);
		
		btnOptions = new JButton("");
		GridBagConstraints gbc_btnOptions = new GridBagConstraints();
		gbc_btnOptions.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnOptions.gridx = 7;
		gbc_btnOptions.gridy = 0;
		panel.add(btnOptions, gbc_btnOptions);
		btnOptions.setIcon(new ImageIcon(VentanaChat.class.getResource("/vistas/menu.png")));
		
		DefaultListModel<Chats> listModel = new DefaultListModel<Chats>();

		
	    final JList<Chats> list = new JList<Chats>(listModel);
	    list.setCellRenderer(new ChatRenderer());
		list.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = list.getSelectedIndex();
					if (index >= 0) {
						Chats chat = list.getModel().getElementAt(index);
						textAreaIndex = index;
						scrollPaneChat.setViewportView(chat.getTextArea());
					}
				}
		     }
		});
	    
	    scrollPane = new JScrollPane(list);
		scrollPane.setBounds(0, 24, 217, 237);
		frame.getContentPane().add(scrollPane);
		
		JPanel panelKeyboard = new JPanel();
		panelKeyboard.setBounds(218, 236, 217, 25);
		frame.getContentPane().add(panelKeyboard);
		panelKeyboard.setLayout(null);
		
		JButton btnEmoji = new JButton("");
		btnEmoji.setBounds(0, 0, 37, 25);
		btnEmoji.setIcon(new ImageIcon(VentanaChat.class.getResource("/vistas/smile.png")));
		panelKeyboard.add(btnEmoji);
		
		textField = new JTextField();
		textField.setBounds(37, 0, 127, 25);
		panelKeyboard.add(textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//textArea[textAreaIndex].append(textField.getText());
			}
		});
		btnSend.setBounds(160, 0, 57, 25);
		panelKeyboard.add(btnSend);

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
