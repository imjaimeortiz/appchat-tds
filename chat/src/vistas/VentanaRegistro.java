package vistas;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;

import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.Color;
import com.toedter.calendar.JDateChooser;

import controlador.ControladorChat;

@SuppressWarnings("serial")
public class VentanaRegistro extends JFrame {

	private JFrame frame;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JFormattedTextField nameField;
	private JFormattedTextField phoneField;
	private JFormattedTextField userField;
	private JFormattedTextField emailField;
	private JLabel lblTodosLosCampos;
	private JLabel lblLasContraseasDeben;
	private JDateChooser dateChooser;
	private JLabel lblDuplicated;

	/**
	 * Create the application.
	 */
	public VentanaRegistro() {
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
		frame.setTitle("Registro");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		lblTodosLosCampos = new JLabel("TODOS LOS CAMPOS SON OBLIGATORIOS");
		lblTodosLosCampos.setForeground(Color.RED);
		lblTodosLosCampos.setVisible(false);
		GridBagConstraints gbc_lblTodosLosCampos = new GridBagConstraints();
		gbc_lblTodosLosCampos.gridwidth = 5;
		gbc_lblTodosLosCampos.insets = new Insets(0, 0, 5, 5);
		gbc_lblTodosLosCampos.gridx = 3;
		gbc_lblTodosLosCampos.gridy = 1;
		frame.getContentPane().add(lblTodosLosCampos, gbc_lblTodosLosCampos);
		
		JLabel lblEmai = new JLabel("Nombre");
		GridBagConstraints gbc_lblEmai = new GridBagConstraints();
		gbc_lblEmai.anchor = GridBagConstraints.EAST;
		gbc_lblEmai.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmai.gridx = 2;
		gbc_lblEmai.gridy = 2;
		frame.getContentPane().add(lblEmai, gbc_lblEmai);
		
		nameField = new JFormattedTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.gridwidth = 5;
		gbc_nameField.insets = new Insets(0, 0, 5, 5);
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.gridx = 3;
		gbc_nameField.gridy = 2;
		frame.getContentPane().add(nameField, gbc_nameField);
		
		JLabel lblNewLabel = new JLabel("Telefono");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 3;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		phoneField = new JFormattedTextField();
		GridBagConstraints gbc_phoneField = new GridBagConstraints();
		gbc_phoneField.gridwidth = 5;
		gbc_phoneField.insets = new Insets(0, 0, 5, 5);
		gbc_phoneField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneField.gridx = 3;
		gbc_phoneField.gridy = 3;
		frame.getContentPane().add(phoneField, gbc_phoneField);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha nacimiento");
		GridBagConstraints gbc_lblFechaNacimiento = new GridBagConstraints();
		gbc_lblFechaNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblFechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaNacimiento.gridx = 2;
		gbc_lblFechaNacimiento.gridy = 4;
		frame.getContentPane().add(lblFechaNacimiento, gbc_lblFechaNacimiento);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 4;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 3;
		gbc_dateChooser.gridy = 4;
		frame.getContentPane().add(dateChooser, gbc_dateChooser);
		
		JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario");
		GridBagConstraints gbc_lblNombreDeUsuario = new GridBagConstraints();
		gbc_lblNombreDeUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblNombreDeUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreDeUsuario.gridx = 2;
		gbc_lblNombreDeUsuario.gridy = 5;
		frame.getContentPane().add(lblNombreDeUsuario, gbc_lblNombreDeUsuario);
		
		userField = new JFormattedTextField();
		GridBagConstraints gbc_userField = new GridBagConstraints();
		gbc_userField.insets = new Insets(0, 0, 5, 5);
		gbc_userField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField.gridx = 3;
		gbc_userField.gridy = 5;
		frame.getContentPane().add(userField, gbc_userField);
		
		lblDuplicated = new JLabel("Ya existe un usuario con este nick o movil");
		lblDuplicated.setVisible(false);
		lblDuplicated.setForeground(Color.RED);
		GridBagConstraints gbc_lblDuplicated = new GridBagConstraints();
		gbc_lblDuplicated.gridwidth = 5;
		gbc_lblDuplicated.insets = new Insets(0, 0, 5, 5);
		gbc_lblDuplicated.gridx = 4;
		gbc_lblDuplicated.gridy = 5;
		frame.getContentPane().add(lblDuplicated, gbc_lblDuplicated);
		
		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 6;
		frame.getContentPane().add(lblEmail, gbc_lblEmail);
		
		emailField = new JFormattedTextField();
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.gridwidth = 4;
		gbc_emailField.insets = new Insets(0, 0, 5, 5);
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.gridx = 3;
		gbc_emailField.gridy = 6;
		frame.getContentPane().add(emailField, gbc_emailField);
		
		JLabel lblClave = new JLabel("Clave");
		GridBagConstraints gbc_lblClave = new GridBagConstraints();
		gbc_lblClave.anchor = GridBagConstraints.EAST;
		gbc_lblClave.insets = new Insets(0, 0, 5, 5);
		gbc_lblClave.gridx = 2;
		gbc_lblClave.gridy = 7;
		frame.getContentPane().add(lblClave, gbc_lblClave);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 2;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 3;
		gbc_passwordField.gridy = 7;
		frame.getContentPane().add(passwordField, gbc_passwordField);
		
		JLabel lblRepetirClave = new JLabel("Repetir clave");
		GridBagConstraints gbc_lblRepetirClave = new GridBagConstraints();
		gbc_lblRepetirClave.anchor = GridBagConstraints.EAST;
		gbc_lblRepetirClave.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepetirClave.gridx = 5;
		gbc_lblRepetirClave.gridy = 7;
		frame.getContentPane().add(lblRepetirClave, gbc_lblRepetirClave);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener( new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				if (checkFields() && checkPwd()) {
					// Creamos un usuario con los campos que tenemos y se lo pasamos a la ventana
					if (ControladorChat.getUnicaInstancia().addUsuario(nameField.getText().trim(), dateChooser.getDate(), phoneField.getText().trim(), userField.getText().trim(), new String (passwordField.getPassword()))) {
						new NuevaVentanaChat(ControladorChat.getUnicaInstancia().recuperarUsuario(userField.getText().trim()));
						frame.dispose();
					}
					else lblDuplicated.setVisible(true); 
				}
			}
		});
		
		passwordField_1 = new JPasswordField();
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.gridwidth = 2;
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridx = 6;
		gbc_passwordField_1.gridy = 7;
		frame.getContentPane().add(passwordField_1, gbc_passwordField_1);
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 3;
		gbc_btnAceptar.gridy = 8;
		frame.getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				new VentanaInicio();
				frame.dispose();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 7;
		gbc_btnCancelar.gridy = 8;
		frame.getContentPane().add(btnCancelar, gbc_btnCancelar);
		
		lblLasContraseasDeben = new JLabel("LAS CONTRASEÑAS DEBEN COINCIDIR");
		lblLasContraseasDeben.setVisible(false);
		lblLasContraseasDeben.setForeground(Color.RED);
		GridBagConstraints gbc_lblLasContraseasDeben = new GridBagConstraints();
		gbc_lblLasContraseasDeben.gridwidth = 5;
		gbc_lblLasContraseasDeben.insets = new Insets(0, 0, 0, 5);
		gbc_lblLasContraseasDeben.gridx = 3;
		gbc_lblLasContraseasDeben.gridy = 9;
		frame.getContentPane().add(lblLasContraseasDeben, gbc_lblLasContraseasDeben);
	}
	
	private boolean checkFields() {
		if ( nameField.getText().isEmpty() ||
			 phoneField.getText().isEmpty() ||
			 userField.getText().isEmpty() ||
			 emailField.getText().isEmpty()) {
				lblTodosLosCampos.setVisible(true); 
				return false;
		}
		return true;
	}
	
	private boolean checkPwd() {
		String pw1 = new String (passwordField.getPassword());
		String pw2 = new String (passwordField_1.getPassword());
		if (pw1.equals(pw2)) return true;
		else {
			lblLasContraseasDeben.setVisible(true);
			return false;
		}
	}
}