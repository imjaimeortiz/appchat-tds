package vistas;

import javax.swing.JDialog;

import controlador.ControladorChat;
import modelo.Usuario;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;

@SuppressWarnings("serial")
public class VentanaCrearContacto extends JDialog {
	private JTextField textField;
	private JTextField txtTelfono;
	private final Usuario usuario;
	private JLabel lblLosCamposNo;
	
	public VentanaCrearContacto(Usuario user) {
		this.usuario = user;
		initialize();
		this.setVisible(true);
	}
	
	public void initialize() {
		lblLosCamposNo = new JLabel("Los campos no son correctos");
		lblLosCamposNo.setVisible(false);
		lblLosCamposNo.setForeground(Color.RED);
		getContentPane().add(lblLosCamposNo, BorderLayout.SOUTH);
		
		setBounds(250, 250, 491, 150);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 50, 0, 50, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNombre = new JLabel("Nombre");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		GridBagConstraints gbc_lblTelfono = new GridBagConstraints();
		gbc_lblTelfono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelfono.anchor = GridBagConstraints.EAST;
		gbc_lblTelfono.gridx = 3;
		gbc_lblTelfono.gridy = 2;
		getContentPane().add(lblTelfono, gbc_lblTelfono);
		
		txtTelfono = new JTextField();
		GridBagConstraints gbc_txtTelfono = new GridBagConstraints();
		gbc_txtTelfono.insets = new Insets(0, 0, 5, 0);
		gbc_txtTelfono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelfono.gridx = 4;
		gbc_txtTelfono.gridy = 2;
		getContentPane().add(txtTelfono, gbc_txtTelfono);
		txtTelfono.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (checkCampos(txtTelfono.getText(), textField.getText())) {
					ControladorChat.getUnicaInstancia().addContacto(usuario, txtTelfono.getText(), textField.getText());
					dispose();
				}
				else lblLosCamposNo.setVisible(true);
			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptar.gridx = 2;
		gbc_btnAceptar.gridy = 3;
		getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridx = 4;
		gbc_btnCancelar.gridy = 3;
		getContentPane().add(btnCancelar, gbc_btnCancelar);
	}
	
	public boolean checkCampos(String tlf, String nombre) {
		if (tlf.equals(null) || nombre.equals(null)) return false;
		return ControladorChat.getUnicaInstancia().existeTlf(tlf);
	}
}
