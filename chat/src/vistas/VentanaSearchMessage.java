package vistas;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import modelo.Contacto;
import modelo.Grupo;
import modelo.Mensaje;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import controlador.ControladorChat;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.Panel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;

public class VentanaSearchMessage {

	private JFrame frame;
	private Contacto c;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the application.
	 */
	public VentanaSearchMessage(Contacto c) {
		this.c = c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		Panel panelFilters = new Panel();
		frame.getContentPane().add(panelFilters, BorderLayout.NORTH);
		GridBagLayout gbl_panelFilters = new GridBagLayout();
		gbl_panelFilters.columnWidths = new int[]{0, 46, 116, 105, 105, 55, 0};
		gbl_panelFilters.rowHeights = new int[]{24, 0, 0};
		gbl_panelFilters.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelFilters.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelFilters.setLayout(gbl_panelFilters);
		
		Label labelTexto = new Label("Texto");
		GridBagConstraints gbc_labelTexto = new GridBagConstraints();
		gbc_labelTexto.anchor = GridBagConstraints.NORTHWEST;
		gbc_labelTexto.insets = new Insets(0, 0, 5, 5);
		gbc_labelTexto.gridx = 1;
		gbc_labelTexto.gridy = 0;
		panelFilters.add(labelTexto, gbc_labelTexto);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panelFilters.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JDateChooser dateChooserMin = new JDateChooser();
		GridBagConstraints gbc_dateChooserMin = new GridBagConstraints();
		gbc_dateChooserMin.anchor = GridBagConstraints.WEST;
		gbc_dateChooserMin.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooserMin.gridx = 3;
		gbc_dateChooserMin.gridy = 0;
		panelFilters.add(dateChooserMin, gbc_dateChooserMin);
		
		JDateChooser dateChooserMax = new JDateChooser();
		GridBagConstraints gbc_dateChooserMax = new GridBagConstraints();
		gbc_dateChooserMax.anchor = GridBagConstraints.WEST;
		gbc_dateChooserMax.insets = new Insets(0, 0, 0, 5);
		gbc_dateChooserMax.gridx = 3;
		gbc_dateChooserMax.gridy = 1;
		panelFilters.add(dateChooserMax, gbc_dateChooserMax);
		
		Button button = new Button("Buscar");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.anchor = GridBagConstraints.NORTH;
		gbc_button.gridx = 4;
		gbc_button.gridy = 0;
		panelFilters.add(button, gbc_button);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		list.setModel(listModel);
		scrollPane.setViewportView(list);
		
		button.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// filtrar por fecha
				ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) ControladorChat.getUnicaInstancia().buscarMensaje(c, textField_1.getText(), textField.getText(), dateChooserMin.getDate(), dateChooserMax.getDate());
					for (Mensaje m : mensajes) {
						listModel.addElement(m.getContacto().getNombre() + "	" + m.getUsuario().getNombre() + "	" + m.getTexto() + "	" + m.getHora().toString());
					
				}
				
				scrollPane.revalidate();
				scrollPane.repaint();
			}
		});
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setVisible(false);
		if (c instanceof Grupo) lblNombre.setVisible(true);
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		panelFilters.add(lblNombre, gbc_lblNombre);
		
		textField_1 = new JTextField();
		textField_1.setVisible(false);
		if (c instanceof Grupo) textField_1.setVisible(true);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 1;
		panelFilters.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		frame.setVisible(true);
	}

}
