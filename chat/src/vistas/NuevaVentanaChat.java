package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JMenuItem;

import javax.swing.JTextField;

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
import java.awt.GridLayout;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;

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
	private DefaultListModel<Chats> listModel;
	private JList<Chats> list;
	private JPanel panelCard;
	private JScrollPane scrollPane;
	
	/**
	 * Create the application.
	 */
	public NuevaVentanaChat(Usuario user) {
		this.user = user;
		this.listModel = new DefaultListModel<Chats>();
		this.list = new JList<Chats>(listModel);
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
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new MigLayout("", "[54px,fill][67px,fill][67px,fill][][][][grow][grow,fill][fill][fill]", "[25px][grow][]"));
		
		btnUser = new JButton("");
		btnUser.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/avatar.png")));
		panel.add(btnUser, "cell 0 0,growx,aligny top");
		
		final JPopupMenu popupMenu = new JPopupMenu();
		final JButton btnMenu = new JButton("");
		btnMenu.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		btnMenu.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/menu.png")));
		addPopup(btnMenu, popupMenu);
		
		JMenuItem mitemCrearContacto = new JMenuItem("Crear contacto");
		mitemCrearContacto.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					JDialog ventanaContact = new VentanaCrearContacto(user);
					ventanaContact.setModal(true);
					ventanaContact.setVisible(true);
					actualizarLista();
					scrollPaneContacts.repaint();
					scrollPaneContacts.revalidate();
			}
		});

		
		JMenuItem mitemMostrarContacto = new JMenuItem("Mostrar contactos");
		mitemMostrarContacto.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (Contacto c : user.getContactos()) {
					System.out.println(c.getNombre());
				}
				new VentanaMostrarContactos(user);
			}
		});
		
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
		popupMenu.add(mitemMostrarContacto);
		popupMenu.add(mitemCrearGrupo);
		popupMenu.add(mitemModificarGrupo);
		popupMenu.add(mitemEstadisticas);
		popupMenu.add(mitemPremium);
		popupMenu.add(mitemCerrarSesion);
		
		btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/chat.png")));
		panel.add(btnSearch, "cell 1 0,growx,aligny top");
		panel.add(btnMenu, "cell 2 0,growx,aligny top");
		
		btnContact = new JButton(user.getNombre());
		btnContact.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/avatar.png")));
		btnContact.addActionListener( new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new VentanaUser(user);
			}
		});
		panel.add(btnContact, "cell 7 0,growx,aligny top");
		
		btnDelete = new JButton("");
		btnDelete.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/delete-button.png")));
		panel.add(btnDelete, "cell 8 0,growx,aligny top");
		
		btnOptions = new JButton("");
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnOptions.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/menu.png")));
		panel.add(btnOptions, "cell 9 0,growx,aligny top");
		
		scrollPaneContacts = new JScrollPane();
		panel.add(scrollPaneContacts, "cell 0 1 3 2,grow");

		scrollPaneContacts.setViewportView(list);
		
		panelCard = new JPanel();
		panel.add(panelCard, "cell 4 1 6 1,grow");
		panelCard.setLayout(new CardLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panelCard.add(scrollPane, "name_1114751964500");
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/smile.png")));
		panel.add(btnNewButton, "cell 4 2");
		
		textField = new JTextField();
		panel.add(textField, "cell 5 2 4 1,growx");
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		panel.add(btnSend, "cell 9 2");
		
		gruposAdmin = new JComboBox<Grupo>();
		for (Grupo g : user.getGruposAdmin()) {
			gruposAdmin.addItem(g);	
		}
	}
	
	private void actualizarLista() {
		for (Contacto c : user.getContactos()) {
			if (c instanceof ContactoIndividual) {
				Chats chat = new Chats(c.getNombre(), null, null);
				listModel.addElement(chat);
				list = new JList<Chats>(listModel);
				list.setCellRenderer(new ChatRenderer());
				list.addMouseListener( new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 1) {
							int index = list.getSelectedIndex();
							if (index >= 0) {
								Chats chat = list.getModel().getElementAt(index);
								scrollPane.setViewportView(chat.getTextArea());
							}
					     }
					}
				});
			}
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