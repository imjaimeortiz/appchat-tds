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
	private JScrollPane scrollPaneChat;
	private JScrollPane scrollPaneContacts;
	
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
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new MigLayout("", "[54px,fill][67px,fill][67px,fill][][][][grow][fill][fill][fill]", "[25px][grow][]"));
		
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
					JDialog contact = new VentanaCrearContacto(user);
					contact.setModal(true);
					contact.setVisible(true);
			}
		});
		JMenuItem mitemMostrarContacto = new JMenuItem("Mostrar contactos");
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
		panel.add(btnSearch, "cell 1 0,growx,aligny top");
		popupMenu.add(mitemModificarGrupo);
		popupMenu.add(mitemEstadisticas);
		popupMenu.add(mitemPremium);
		popupMenu.add(mitemCerrarSesion);
		panel.add(btnMenu, "cell 2 0,growx,aligny top");
		
		btnContact = new JButton("Name");
		btnContact.setIcon(new ImageIcon(NuevaVentanaChat.class.getResource("/vistas/avatar.png")));
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
		
		DefaultListModel<Chats> listModel = new DefaultListModel<Chats>();
		
		for (Contacto c : user.getContactos()) {
			Chats chat = new Chats(c.getNombre(), null, null);
			listModel.addElement(chat);
		}

		final JList<Chats> list = new JList<Chats>(listModel);
		list.setCellRenderer(new ChatRenderer());
		list.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					int index = list.getSelectedIndex();
					if (index >= 0) {
						Chats chat = list.getModel().getElementAt(index);
						scrollPaneChat.setViewportView(chat.getTextArea());
					}
				}
		     }
		});
		scrollPaneContacts.setViewportView(list);
		
		scrollPaneChat = new JScrollPane();
		panel.add(scrollPaneChat, "cell 4 1 6 1,grow");
		
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
