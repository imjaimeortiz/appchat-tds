package vistas;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JList;
import javax.swing.JMenuItem;

import controlador.ControladorChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;

import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class NuevaVentanaChat {

	private JFrame frame;
	private JButton btnUser;
	private JButton btnSearch;
	private JButton btnContact;
	private JButton btnDelete;
	private JButton btnOptions;

	private Usuario user;
	private JScrollPane scrollPaneContacts;
	private DefaultListModel<Contacto> listModel;
	private JList<Contacto> list;
	private Map<String, Chats> mapa;
	private Chats chat;
	private JPanel panelCard;

	
	/**
	 * Create the application.
	 */
	public NuevaVentanaChat(Usuario user) {
		this.user = user;
		this.listModel = new DefaultListModel<Contacto>();
		this.mapa = new HashMap<String, Chats>();
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 577, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{200, 300, 0};
		gridBagLayout.rowHeights = new int[]{0, 26, 250, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		panelCard = new JPanel();
		GridBagConstraints gbc_panelCard = new GridBagConstraints();
		gbc_panelCard.gridheight = 2;
		gbc_panelCard.fill = GridBagConstraints.BOTH;
		gbc_panelCard.gridx = 1;
		gbc_panelCard.gridy = 1;
		frame.getContentPane().add(panelCard, gbc_panelCard);
		panelCard.setLayout(new CardLayout(0, 0));
		
		JPanel panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.gridwidth = 2;
		gbc_panelBotones.insets = new Insets(0, 0, 5, 0);
		gbc_panelBotones.fill = GridBagConstraints.BOTH;
		gbc_panelBotones.gridx = 0;
		gbc_panelBotones.gridy = 0;
		frame.getContentPane().add(panelBotones, gbc_panelBotones);
		GridBagLayout gbl_panelBotones = new GridBagLayout();
		gbl_panelBotones.columnWidths = new int[]{59, 100, 49, 49, 117, 0, 0, 49, 0, 0, 0};
		gbl_panelBotones.rowHeights = new int[]{25, 0};
		gbl_panelBotones.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelBotones.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelBotones.setLayout(gbl_panelBotones);
		
		for (Contacto c : ControladorChat.getUnicaInstancia().recuperarContactos(user)) {
			listModel.addElement(c);
			Chats chat = new Chats(c, user, listModel);
			mapa.put(c.toString(), chat);
			panelCard.add(c.toString(), chat);
		}
		panelCard.setVisible(false);
		
		btnSearch = new JButton("");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.fill = GridBagConstraints.BOTH;
		gbc_btnSearch.insets = new Insets(0, 0, 0, 5);
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 0;
		panelBotones.add(btnSearch, gbc_btnSearch);
		btnSearch.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/chat.png")));
		
		btnUser = new JButton(user.getNombre());
		GridBagConstraints gbc_btnUser = new GridBagConstraints();
		gbc_btnUser.fill = GridBagConstraints.BOTH;
		gbc_btnUser.insets = new Insets(0, 0, 0, 5);
		gbc_btnUser.gridx = 1;
		gbc_btnUser.gridy = 0;
		panelBotones.add(btnUser, gbc_btnUser);
		btnUser.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource(user.getImagen())));
		btnUser.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new VentanaUser(user);
			}
		});
		
		final JPopupMenu popupMenu = new JPopupMenu();
		final JButton btnMenu = new JButton("");
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.fill = GridBagConstraints.BOTH;
		gbc_btnMenu.insets = new Insets(0, 0, 0, 5);
		gbc_btnMenu.gridx = 2;
		gbc_btnMenu.gridy = 0;
		panelBotones.add(btnMenu, gbc_btnMenu);
		btnMenu.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		btnMenu.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/menu.png")));
		addPopup(btnMenu, popupMenu);
		
		JMenuItem mitemCrearContacto = new JMenuItem("Crear contacto");
		popupMenu.add(mitemCrearContacto);
		mitemCrearContacto.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					VentanaCrearContacto ventanaContact = new VentanaCrearContacto(user);
					ventanaContact.setModal(true);
					ventanaContact.setVisible(true);
					ControladorChat.getUnicaInstancia().recuperarContactos(user).stream()
																						.filter( c -> (!listModel.contains(c)))
																						.forEach(c -> {
																							listModel.addElement(c);
																							chat = new Chats(c, user, listModel);
																							mapa.put(c.toString(), chat);
																							panelCard.add(c.toString(), chat);
																							list.setModel(listModel);
																						});
			}
		});
		
				
				JMenuItem mitemMostrarContacto = new JMenuItem("Mostrar contactos");
				popupMenu.add(mitemMostrarContacto);
				mitemMostrarContacto.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new VentanaMostrarContactos(user);
					}
				});
				
				JMenuItem mitemCrearGrupo = new JMenuItem("Crear grupo");
				popupMenu.add(mitemCrearGrupo);
				mitemCrearGrupo.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							VentanaGrupo ventanaGrupo = new VentanaGrupo(user);
							ventanaGrupo.setModal(true);
							ventanaGrupo.setVisible(true);
							ControladorChat.getUnicaInstancia().recuperarContactos(user).stream()
								.filter(c -> (!listModel.contains(c)))
								.forEach(c -> {
									listModel.addElement(c);
									chat = new Chats(c, user, listModel);
									mapa.put(c.toString(), chat);
									panelCard.add(c.toString(), chat);
									list.setModel(listModel);			
								});
					}
				});
				JMenuItem mitemModificarGrupo = new JMenuItem("Modificar grupo");
				popupMenu.add(mitemModificarGrupo);
				mitemModificarGrupo.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new VentanaModificarGrupo(user);
					}
				});
				
				JMenuItem mitemEstadisticas = new JMenuItem("Mostrar estadísticas");
				popupMenu.add(mitemEstadisticas);
				//ControladorChat.mostrarEstadisticas(user)
				
				JMenuItem mitemPremium = new JMenuItem("Ir a premium");
				popupMenu.add(mitemPremium);
				//ControladorChat.setPremium(user);
				
				
				
				JMenuItem mitemCerrarSesion = new JMenuItem("Cerrar sesión");
				popupMenu.add(mitemCerrarSesion);
				mitemCerrarSesion.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							frame.dispose();
							new VentanaInicio();
					}
					
				});
		btnContact = new JButton("");
		GridBagConstraints gbc_btnContact = new GridBagConstraints();
		gbc_btnContact.fill = GridBagConstraints.BOTH;
		gbc_btnContact.insets = new Insets(0, 0, 0, 5);
		gbc_btnContact.gridx = 4;
		gbc_btnContact.gridy = 0;
		panelBotones.add(btnContact, gbc_btnContact);
		btnContact.setVisible(false);
		
		JButton btnNewButton_1 = new JButton("");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 7;
		gbc_btnNewButton_1.gridy = 0;
		panelBotones.add(btnNewButton_1, gbc_btnNewButton_1);
		btnNewButton_1.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/order.png")));
		
		btnDelete = new JButton("");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.fill = GridBagConstraints.BOTH;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 8;
		gbc_btnDelete.gridy = 0;
		panelBotones.add(btnDelete, gbc_btnDelete);
		btnDelete.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/delete-button.png")));
		
		btnOptions = new JButton("");
		GridBagConstraints gbc_btnOptions = new GridBagConstraints();
		gbc_btnOptions.fill = GridBagConstraints.BOTH;
		gbc_btnOptions.gridx = 9;
		gbc_btnOptions.gridy = 0;
		panelBotones.add(btnOptions, gbc_btnOptions);
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnOptions.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/menu.png")));
				
		this.list = new JList<Contacto>(listModel);
		list.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() > 0) {
					Contacto c = list.getSelectedValue();
					btnContact.setText(c.getNombre());
					if (c instanceof ContactoIndividual)
						btnContact.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource(((ContactoIndividual) c).getUsuario().getImagen())));
					else btnContact.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource(((Grupo) c).getFoto())));
					btnContact.setVisible(true);
					CardLayout card = (CardLayout) (panelCard.getLayout());
					card.show(panelCard, c.toString());
					panelCard.setVisible(true);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		
		this.scrollPaneContacts = new JScrollPane();
		GridBagConstraints gbc_scrollPaneContacts = new GridBagConstraints();
		gbc_scrollPaneContacts.gridheight = 2;
		gbc_scrollPaneContacts.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneContacts.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPaneContacts.gridx = 0;
		gbc_scrollPaneContacts.gridy = 1;
		frame.getContentPane().add(scrollPaneContacts, gbc_scrollPaneContacts);
		scrollPaneContacts.setViewportView(list);
		list.setCellRenderer(new ChatRenderer());
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
