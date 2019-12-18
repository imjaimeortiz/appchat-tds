package vistas;

import javax.swing.JLabel;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFrame;

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

public class VentanaGrupo extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5697879337468550356L;
	protected static final int MAX_CLICKS = 10;
	private JButton btnEliminar;
	private JButton btnAadir;
	private JLabel lblListaContactos;
	private JScrollPane scrollLista;
	private JFrame frame;
	private JScrollPane scrollGrupo;
	private JTextField textGroupName;
	private DefaultListModel<ContactoIndividual> listContacts;
	private DefaultListModel<ContactoIndividual> listMembers;
	private List<ContactoIndividual> members;
	private List<ContactoIndividual> nuevos;
	private List<ContactoIndividual> eliminados;
	private Grupo group;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private boolean comprobar;

	/**
	 * Create the panel.
	 * @wbp.parser.constructor
	 */
	// MODIFICAR GRUPO
	public VentanaGrupo(Grupo group) {
		this.group = group;
		this.comprobar = false;
		for (ContactoIndividual contactoIndividual : group.getContactos()) {
			this.listMembers.addElement(contactoIndividual);
		}
		for (Contacto c : group.getAdmin().getContactos()) {
			if (!listMembers.contains(c)) listContacts.addElement((ContactoIndividual) c);
		}
		this.nuevos = new LinkedList<ContactoIndividual>();
		this.eliminados = new LinkedList<ContactoIndividual>();
		initialize();
		if (comprobar == true) {
			for(int i = 0; i < listMembers.getSize(); i++) {
				members.add(listMembers.get(i));
			}
			ControladorChat.getUnicaInstancia().agregarContactosGrupo(group, nuevos);
			ControladorChat.getUnicaInstancia().eliminarContactosGrupo(group, eliminados);
			ControladorChat.getUnicaInstancia().actualizarNombreGrupo(group, textGroupName.getText());
		}
		frame.setVisible(true);
	}
	
	// CREAR GRUPO
	public VentanaGrupo(Usuario admin) {
		this.comprobar = false;
		for (Contacto c : admin.getContactos()) {
			if (c instanceof ContactoIndividual) this.listContacts.addElement((ContactoIndividual) c);
		}
		this.listMembers = new DefaultListModel<ContactoIndividual>();
		this.nuevos = new LinkedList<ContactoIndividual>();
		this.eliminados = new LinkedList<ContactoIndividual>();
		initialize();
		if (comprobar == true) {
			for(int i = 0; i < listMembers.getSize(); i++) {
				members.add(listMembers.get(i));
			}
			ControladorChat.getUnicaInstancia().addGrupo(textGroupName.getText(), admin, (LinkedList<ContactoIndividual>)members);
		}
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{40, 100, 0, 20, 85, 20, 100, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{14, 0, 0, 65, 0, 50, 0, 20, 10, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		lblListaContactos = new JLabel("Lista contactos");
		GridBagConstraints gbc_lblListaContactos = new GridBagConstraints();
		gbc_lblListaContactos.gridwidth = 2;
		gbc_lblListaContactos.insets = new Insets(0, 0, 5, 5);
		gbc_lblListaContactos.gridx = 1;
		gbc_lblListaContactos.gridy = 1;
		frame.getContentPane().add(lblListaContactos, gbc_lblListaContactos);
		
		textGroupName = new JTextField();
		GridBagConstraints gbc_textGroupName = new GridBagConstraints();
		gbc_textGroupName.gridwidth = 3;
		gbc_textGroupName.insets = new Insets(0, 0, 5, 5);
		gbc_textGroupName.fill = GridBagConstraints.HORIZONTAL;
		gbc_textGroupName.gridx = 3;
		gbc_textGroupName.gridy = 1;
		frame.getContentPane().add(textGroupName, gbc_textGroupName);
		textGroupName.setColumns(10);
		//textGroupName.setText(group.getNombre());
		
		JLabel lblMiembros = new JLabel("Miembros");
		GridBagConstraints gbc_lblMiembros = new GridBagConstraints();
		gbc_lblMiembros.gridwidth = 2;
		gbc_lblMiembros.insets = new Insets(0, 0, 5, 5);
		gbc_lblMiembros.gridx = 6;
		gbc_lblMiembros.gridy = 1;
		frame.getContentPane().add(lblMiembros, gbc_lblMiembros);
		
		scrollLista = new JScrollPane();
		GridBagConstraints gbc_scrollLista = new GridBagConstraints();
		gbc_scrollLista.gridwidth = 2;
		gbc_scrollLista.fill = GridBagConstraints.BOTH;
		gbc_scrollLista.insets = new Insets(0, 0, 5, 5);
		gbc_scrollLista.gridheight = 5;
		gbc_scrollLista.gridx = 1;
		gbc_scrollLista.gridy = 2;
		frame.getContentPane().add(scrollLista, gbc_scrollLista);
		
		final JList<ContactoIndividual> list = new JList<ContactoIndividual>(listContacts);


		list.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					nuevos.add(list.getSelectedValue());
					eliminados.remove(list.getSelectedValue());
					listMembers.addElement(list.getSelectedValue());
					listContacts.removeElement(list.getSelectedValue());
				}
		     }
		});
		
		scrollLista.setViewportView(list);
		
		scrollGrupo = new JScrollPane();
		GridBagConstraints gbc_scrollGrupo = new GridBagConstraints();
		gbc_scrollGrupo.gridwidth = 2;
		gbc_scrollGrupo.insets = new Insets(0, 0, 5, 5);
		gbc_scrollGrupo.fill = GridBagConstraints.BOTH;
		gbc_scrollGrupo.gridheight = 5;
		gbc_scrollGrupo.gridx = 6;
		gbc_scrollGrupo.gridy = 2;
		frame.getContentPane().add(scrollGrupo, gbc_scrollGrupo);
		
		final JList<ContactoIndividual> list2 = new JList<ContactoIndividual>(listMembers);
		
		scrollGrupo.setViewportView(list2);

		list2.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					eliminados.add(list2.getSelectedValue());
					nuevos.remove(list2.getSelectedValue());
					listContacts.addElement(list2.getSelectedValue());
					listMembers.removeElement(list2.getSelectedValue());
				}
		     }
		});
		
		btnAadir = new JButton("Añadir >>");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (ContactoIndividual string : list.getSelectedValuesList()) {
					if (!listMembers.contains(string)) {
						nuevos.add(list.getSelectedValue());
						eliminados.remove(string);
						listContacts.removeElement(string);
						listMembers.addElement(string);
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
		gbc_btnAadir.gridy = 3;
		frame.getContentPane().add(btnAadir, gbc_btnAadir);
		
		btnEliminar = new JButton("<< Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for (ContactoIndividual string : list2.getSelectedValuesList()) {
					if (!listContacts.contains(string)) {
						nuevos.remove(string);
						eliminados.add(string);
						listContacts.addElement(string);
						listMembers.removeElement(string);
					}
				}
			}
		});
		
		GridBagConstraints gbc_btnEliminar = new GridBagConstraints();
		gbc_btnEliminar.gridwidth = 3;
		gbc_btnEliminar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEliminar.insets = new Insets(0, 0, 5, 5);
		gbc_btnEliminar.gridx = 3;
		gbc_btnEliminar.gridy = 4;
		frame.getContentPane().add(btnEliminar, gbc_btnEliminar);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comprobar = true;
			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 7;
		frame.getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 6;
		gbc_btnCancelar.gridy = 7;
		frame.getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

}
