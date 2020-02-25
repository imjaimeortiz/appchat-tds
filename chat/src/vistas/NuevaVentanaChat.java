package vistas;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JList;
import javax.swing.JMenuItem;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import controlador.ControladorChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class NuevaVentanaChat {

	private JFrame frame;
	private JButton btnUser;
	private JButton btnSearch;
	private JButton btnContact;
	private JButton btnDelete;
	private JButton btnOptions;
	private JComboBox<Grupo> gruposAdmin;

	private Usuario user;
	private Grupo group;
	private JTextField textField;
	private JScrollPane scrollPaneContacts;
	private DefaultListModel<Contacto> listModel;
	private JList<Contacto> list;
	private Map<String, Chats> mapa;
	private Chats panelChat;
	private JPanel panelCard;

	
	/**
	 * Create the application.
	 */
	public NuevaVentanaChat(Usuario user) {
		this.user = user;
		this.listModel = new DefaultListModel<Contacto>();
		this.mapa = new HashMap<String, Chats>();
		for (Contacto c : ControladorChat.getUnicaInstancia().getTodosContactos(user)) {
			listModel.addElement(c);
		}
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
		gridBagLayout.columnWidths = new int[]{250, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 26, 26, 44, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		panelCard = new JPanel();
		
		JPanel panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.gridwidth = 2;
		gbc_panelBotones.insets = new Insets(0, 0, 5, 0);
		gbc_panelBotones.fill = GridBagConstraints.BOTH;
		gbc_panelBotones.gridx = 0;
		gbc_panelBotones.gridy = 0;
		frame.getContentPane().add(panelBotones, gbc_panelBotones);
		
		btnSearch = new JButton("");
		panelBotones.add(btnSearch);
		btnSearch.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/chat.png")));
		
		btnUser = new JButton("");
		panelBotones.add(btnUser);
		btnUser.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/avatar.png")));
		
		final JPopupMenu popupMenu = new JPopupMenu();
		final JButton btnMenu = new JButton("");
		panelBotones.add(btnMenu);
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
					ControladorChat.getUnicaInstancia().getTodosContactos(user).stream().filter( c -> (!listModel.contains(c)))
																						.forEach(c -> {
																							listModel.addElement(c);
																							panelChat = new Chats(c, user.getNick(), scrollPaneContacts, listModel);
																							mapa.put(c.toString(), panelChat);
																							panelCard.add(c.toString(), panelChat);
																							System.out
																									.println("Contacto añadido : " + c.toString());
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
							new VentanaGrupo(user);
					}
				});
				
				JMenuItem mitemModificarGrupo = new JMenuItem("Modificar grupo");
				popupMenu.add(mitemModificarGrupo);
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
				popupMenu.add(mitemEstadisticas);
				//ControladorChat.mostrarEstadisticas(user)
				
				JMenuItem mitemPremium = new JMenuItem("Ir a premium");
				popupMenu.add(mitemPremium);
				//ControladorChat.setPremium(user);
				
				
				
				JMenuItem mitemCerrarSesion = new JMenuItem("Cerrar sesión");
				popupMenu.add(mitemCerrarSesion);
				
				btnContact = new JButton(user.getNombre());
				panelBotones.add(btnContact);
				btnContact.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/avatar.png")));
				
				JButton btnNewButton_1 = new JButton("");
				panelBotones.add(btnNewButton_1);
				btnNewButton_1.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/order.png")));
				
				btnDelete = new JButton("");
				panelBotones.add(btnDelete);
				btnDelete.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/delete-button.png")));
				
				btnOptions = new JButton("");
				panelBotones.add(btnOptions);
				btnOptions.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				});
				btnOptions.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/menu.png")));
				btnContact.addActionListener( new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						new VentanaUser(user);
					}
				});
				mitemCerrarSesion.addActionListener( new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							frame.dispose();
							new VentanaInicio();
					}
					
				});
		this.list = new JList<Contacto>(listModel);
		list.addListSelectionListener( new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					Contacto c = list.getSelectedValue();
					System.out.println("Contacto seleccionado :" + c.toString());
					CardLayout card = (CardLayout) (panelCard.getLayout());
					card.show(panelCard, c.toString());
					panelCard.setVisible(true);
				}
				
			}
		});
		this.scrollPaneContacts = new JScrollPane();
		GridBagConstraints gbc_scrollPaneContacts = new GridBagConstraints();
		gbc_scrollPaneContacts.gridheight = 3;
		gbc_scrollPaneContacts.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneContacts.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPaneContacts.gridx = 0;
		gbc_scrollPaneContacts.gridy = 1;
		frame.getContentPane().add(scrollPaneContacts, gbc_scrollPaneContacts);
		
		scrollPaneContacts.setViewportView(list);
		list.setCellRenderer(new ChatRenderer());
		
		GridBagConstraints gbc_panelCard = new GridBagConstraints();
		gbc_panelCard.gridheight = 2;
		gbc_panelCard.insets = new Insets(0, 0, 5, 0);
		gbc_panelCard.fill = GridBagConstraints.BOTH;
		gbc_panelCard.gridx = 1;
		gbc_panelCard.gridy = 1;
		frame.getContentPane().add(panelCard, gbc_panelCard);
		panelCard.setLayout(new CardLayout(0, 0));
		
		JPanel panelSend = new JPanel();
		GridBagConstraints gbc_panelSend = new GridBagConstraints();
		gbc_panelSend.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelSend.gridx = 1;
		gbc_panelSend.gridy = 3;
		frame.getContentPane().add(panelSend, gbc_panelSend);
		
		JButton btnNewButton = new JButton("");
		panelSend.add(btnNewButton);
		btnNewButton.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/smile.png")));
		
		textField = new JTextField();
		panelSend.add(textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		panelSend.add(btnSend);
		btnSend.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mensaje = textField.getText();
				// CREAR BURBUJA
				panelChat.mostrarMensaje(mensaje);
				textField.setText(null);
			}
		});
		
		gruposAdmin = new JComboBox<Grupo>();
		for (Grupo g : user.getGruposAdmin()) {
			gruposAdmin.addItem(g);	
		}
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
