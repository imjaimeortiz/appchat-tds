package vistas;

import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import controlador.ControladorChat;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import java.awt.Color;

public class VentanaGrupo extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5697879337468550356L;
	protected static final int MAX_CLICKS = 10;
	private JButton btnEliminar;
	private JButton btnAadir;
	private JLabel lblListaContactos;
	private JScrollPane scrollLista;
	private JScrollPane scrollGrupo;
	private JTextField textGroupName;
	private DefaultListModel<ContactoIndividual> listContactsModel;
	private DefaultListModel<ContactoIndividual> listMembersModel;
	private List<ContactoIndividual> nuevos;
	private List<ContactoIndividual> eliminados;
	private JList<ContactoIndividual> listContacts;
	private JList<ContactoIndividual> listMembers;
	private Grupo group;
	private Usuario admin;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private boolean comprobar;
	private JLabel lblIntroduceUnNombre;

	/**
	 * Create the panel.
	 * @wbp.parser.constructor
	 */
	// MODIFICAR GRUPO
	public VentanaGrupo(Grupo group) {
		this.group = group;
		this.comprobar = true;
		this.nuevos = new LinkedList<ContactoIndividual>();
		this.eliminados = new LinkedList<ContactoIndividual>();
		this.listContactsModel = new DefaultListModel<ContactoIndividual>();
		this.listMembersModel = new DefaultListModel<ContactoIndividual>();
		for (ContactoIndividual contactoIndividual : ControladorChat.getUnicaInstancia().miembrosGrupo(group)) {
			this.listMembersModel.addElement(contactoIndividual);
		}
		for (Contacto c : ControladorChat.getUnicaInstancia().recuperarContactos(group.getAdmin())) {
			if (c instanceof ContactoIndividual && !listMembersModel.contains(c)) listContactsModel.addElement((ContactoIndividual) c);
		}
		this.scrollLista = new JScrollPane();
		this.scrollGrupo = new JScrollPane();
		this.listContacts = new JList<ContactoIndividual>(listContactsModel);
		this.listMembers = new JList<ContactoIndividual>(listMembersModel);
		listContacts.setModel(listContactsModel);
		listMembers.setModel(listMembersModel);
		initialize();
	}
	
	// CREAR GRUPO
	public VentanaGrupo(Usuario admin) {
		this.comprobar = false;
		this.admin = admin;
		this.listContactsModel = new DefaultListModel<ContactoIndividual>();
		this.listMembersModel = new DefaultListModel<ContactoIndividual>();
		for (Contacto c : ControladorChat.getUnicaInstancia().recuperarContactos(admin)) {
			if (c instanceof ContactoIndividual) listContactsModel.addElement((ContactoIndividual) c);
		}
		this.nuevos = new LinkedList<ContactoIndividual>();
		this.eliminados = new LinkedList<ContactoIndividual>();
		this.scrollLista = new JScrollPane();
		this.scrollGrupo = new JScrollPane();
		this.listContacts = new JList<ContactoIndividual>(listContactsModel);
		this.listMembers = new JList<ContactoIndividual>(listMembersModel);
		listContacts.setModel(listContactsModel);
		listMembers.setModel(listMembersModel);
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 450, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 100, 0, 20, 85, 20, 100, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{14, 0, 0, 0, 65, 0, 50, 0, 20, 10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		lblIntroduceUnNombre = new JLabel("Introduce un nombre de grupo válido");
		lblIntroduceUnNombre.setVisible(false);
		lblIntroduceUnNombre.setForeground(Color.RED);
		GridBagConstraints gbc_lblIntroduceUnNombre = new GridBagConstraints();
		gbc_lblIntroduceUnNombre.gridwidth = 9;
		gbc_lblIntroduceUnNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntroduceUnNombre.gridx = 0;
		gbc_lblIntroduceUnNombre.gridy = 1;
		getContentPane().add(lblIntroduceUnNombre, gbc_lblIntroduceUnNombre);
		
		lblListaContactos = new JLabel("Lista contactos");
		GridBagConstraints gbc_lblListaContactos = new GridBagConstraints();
		gbc_lblListaContactos.gridwidth = 2;
		gbc_lblListaContactos.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaContactos.gridx = 1;
		gbc_lblListaContactos.gridy = 2;
		getContentPane().add(lblListaContactos, gbc_lblListaContactos);
		
		textGroupName = new JTextField();
		GridBagConstraints gbc_textGroupName = new GridBagConstraints();
		gbc_textGroupName.gridwidth = 3;
		gbc_textGroupName.insets = new Insets(0, 0, 5, 5);
		gbc_textGroupName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textGroupName.gridx = 3;
		gbc_textGroupName.gridy = 2;
		getContentPane().add(textGroupName, gbc_textGroupName);
		textGroupName.setColumns(10);
		if (comprobar) textGroupName.setText(group.getNombre());
		
		JLabel lblMiembros = new JLabel("Miembros");
		GridBagConstraints gbc_lblMiembros = new GridBagConstraints();
		gbc_lblMiembros.gridwidth = 2;
		gbc_lblMiembros.insets = new Insets(0, 0, 5, 5);
		gbc_lblMiembros.gridx = 6;
		gbc_lblMiembros.gridy = 2;
		getContentPane().add(lblMiembros, gbc_lblMiembros);
		
		GridBagConstraints gbc_scrollLista = new GridBagConstraints();
		gbc_scrollLista.gridwidth = 2;
		gbc_scrollLista.fill = GridBagConstraints.BOTH;
		gbc_scrollLista.insets = new Insets(0, 0, 5, 5);
		gbc_scrollLista.gridheight = 5;
		gbc_scrollLista.gridx = 1;
		gbc_scrollLista.gridy = 3;
		getContentPane().add(scrollLista, gbc_scrollLista);
		
		scrollLista.setViewportView(listContacts);
		listContacts.setCellRenderer(new ChatRenderer());

		listContacts.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					nuevos.add(listContacts.getSelectedValue());
					eliminados.remove(listContacts.getSelectedValue());
					listMembersModel.addElement(listContacts.getSelectedValue());
					listContactsModel.removeElement(listContacts.getSelectedValue());
					listContacts.setModel(listContactsModel);
					listMembers.setModel(listMembersModel);
					scrollLista.setViewportView(listContacts);
					scrollGrupo.setViewportView(listMembers);
				}
		     }
		});
		
		GridBagConstraints gbc_scrollGrupo = new GridBagConstraints();
		gbc_scrollGrupo.gridwidth = 2;
		gbc_scrollGrupo.insets = new Insets(0, 0, 5, 5);
		gbc_scrollGrupo.fill = GridBagConstraints.BOTH;
		gbc_scrollGrupo.gridheight = 5;
		gbc_scrollGrupo.gridx = 6;
		gbc_scrollGrupo.gridy = 3;
		getContentPane().add(scrollGrupo, gbc_scrollGrupo);
		
		scrollGrupo.setViewportView(listMembers);
		listMembers.setCellRenderer(new ChatRenderer());
		
		listMembers.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					eliminados.add(listMembers.getSelectedValue());
					nuevos.remove(listMembers.getSelectedValue());
					listContactsModel.addElement(listMembers.getSelectedValue());
					listMembersModel.removeElement(listMembers.getSelectedValue());
					listContacts.setModel(listContactsModel);
					listMembers.setModel(listMembersModel);
				}
		     }
		});
		
		btnAadir = new JButton("Añadir >>");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (ContactoIndividual string : listContacts.getSelectedValuesList()) {
					if (!listMembersModel.contains(string)) {
						nuevos.add(listContacts.getSelectedValue());
						eliminados.remove(string);
						listContactsModel.removeElement(string);
						listMembersModel.addElement(string);
						listContacts.setModel(listContactsModel);
						listMembers.setModel(listMembersModel);
						scrollLista.setViewportView(listContacts);
						scrollGrupo.setViewportView(listMembers);
					}
				}
			}
		});
		GridBagConstraints gbc_btnAadir = new GridBagConstraints();
		gbc_btnAadir.gridwidth = 3;
		gbc_btnAadir.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAadir.anchor = GridBagConstraints.SOUTH;
		gbc_btnAadir.insets = new Insets(0, 0, 5, 5);
		gbc_btnAadir.gridx = 3;
		gbc_btnAadir.gridy = 4;
		getContentPane().add(btnAadir, gbc_btnAadir);
		
		btnEliminar = new JButton("<< Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (ContactoIndividual string : listMembers.getSelectedValuesList()) {
					if (!listContactsModel.contains(string)) {
						nuevos.remove(string);
						eliminados.add(string);
						listContactsModel.addElement(string);
						listMembersModel.removeElement(string);
						listContacts.setModel(listContactsModel);
						listMembers.setModel(listMembersModel);
						scrollLista.setViewportView(listContacts);
						scrollGrupo.setViewportView(listMembers);
					}
				}
			}
		});
		
		GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
		gbc_btnEliminar.gridwidth = 3;
		gbc_btnEliminar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEliminar.insets = new Insets(0, 0, 5, 5);
		gbc_btnEliminar.gridx = 3;
		gbc_btnEliminar.gridy = 5;
		getContentPane().add(btnEliminar, gbc_btnEliminar);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textGroupName.getText().equals(null)) lblIntroduceUnNombre.setVisible(true);
				else {
					if (!comprobar) {
						group = new Grupo(textGroupName.getText(), admin, nuevos);
						ControladorChat.getUnicaInstancia().addGrupo(admin, group);
					} else {
						ControladorChat.getUnicaInstancia().agregarContactosGrupo(group, nuevos);
						ControladorChat.getUnicaInstancia().eliminarContactosGrupo(group, eliminados);
						ControladorChat.getUnicaInstancia().actualizarNombreGrupo(group, textGroupName.getText());	
					}
					dispose();
				}
			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 8;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 6;
		gbc_btnCancelar.gridy = 8;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

}
