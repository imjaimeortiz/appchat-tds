package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JFormattedTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

import controlador.ControladorChat;

import java.awt.Color;

public class VentanaInicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6160015426312007665L;
	private JFrame frame;
	private JFormattedTextField userField;
	private JPasswordField passwordField;
	private JLabel lblTodosLosCampos;
	private JLabel lblUsuarioOContrasea;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
                    //here you can put the selected theme class name in JTattoo
                    UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
         
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
				try {
					VentanaInicio window = new VentanaInicio();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaInicio() {
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
		frame.setTitle("Ventana de inicio");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 20, 0, 10, 0, 10, 0, 0, 20, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblEntradaAppchat = new JLabel("ENTRADA APPCHAT");
		lblEntradaAppchat.setFont(new Font("Tahoma", Font.BOLD, 20));
		GridBagConstraints gbc_lblEntradaAppchat = new GridBagConstraints();
		gbc_lblEntradaAppchat.gridwidth = 8;
		gbc_lblEntradaAppchat.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntradaAppchat.gridx = 0;
		gbc_lblEntradaAppchat.gridy = 1;
		frame.getContentPane().add(lblEntradaAppchat, gbc_lblEntradaAppchat);
		
		JLabel lblUsuario = new JLabel("Usuario");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 3;
		gbc_lblUsuario.gridy = 3;
		frame.getContentPane().add(lblUsuario, gbc_lblUsuario);
		
		userField = new JFormattedTextField();
		GridBagConstraints gbc_userField = new GridBagConstraints();
		gbc_userField.insets = new Insets(0, 0, 5, 5);
		gbc_userField.fill = GridBagConstraints.HORIZONTAL;
		gbc_userField.gridx = 4;
		gbc_userField.gridy = 3;
		frame.getContentPane().add(userField, gbc_userField);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 3;
		gbc_lblContrasea.gridy = 5;
		frame.getContentPane().add(lblContrasea, gbc_lblContrasea);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 4;
		gbc_passwordField.gridy = 5;
		frame.getContentPane().add(passwordField, gbc_passwordField);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkFields()) {
					if ( ControladorChat.getUnicaInstancia().usuarioTrue(userField.getText().trim(), new String(passwordField.getPassword()))) {
						new NuevaVentanaChat(ControladorChat.getUnicaInstancia().recuperarUsuario(userField.getText().trim()));
						frame.dispose();
					}
					else {
						lblUsuarioOContrasea.setVisible(true);
					}
				}
			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.fill = GridBagConstraints.VERTICAL;
		gbc_btnAceptar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAceptar.gridx = 3;
		gbc_btnAceptar.gridy = 7;
		frame.getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		JButton btnRegistro = new JButton("Registro");
		btnRegistro.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				new VentanaRegistro();
				frame.dispose();
			}
		});
		GridBagConstraints gbc_btnRegistro = new GridBagConstraints();
		gbc_btnRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_btnRegistro.gridx = 4;
		gbc_btnRegistro.gridy = 7;
		frame.getContentPane().add(btnRegistro, gbc_btnRegistro);
				
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				System.exit(0);
			}
		});
		GridBagConstraints gbc_btnSalir = new GridBagConstraints();
		gbc_btnSalir.gridwidth = 2;
		gbc_btnSalir.insets = new Insets(0, 0, 5, 5);
		gbc_btnSalir.gridx = 5;
		gbc_btnSalir.gridy = 7;
		frame.getContentPane().add(btnSalir, gbc_btnSalir);
		
		lblTodosLosCampos = new JLabel("TODOS LOS CAMPOS SON OBLIGATORIOS");
		lblTodosLosCampos.setVisible(false);
		
		lblUsuarioOContrasea = new JLabel("USUARIO O CONTRASEÑA INCORRECTOS");
		lblUsuarioOContrasea.setVisible(false);
		lblUsuarioOContrasea.setForeground(Color.RED);
		GridBagConstraints gbc_lblUsuarioOContrasea = new GridBagConstraints();
		gbc_lblUsuarioOContrasea.gridwidth = 9;
		gbc_lblUsuarioOContrasea.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsuarioOContrasea.gridx = 0;
		gbc_lblUsuarioOContrasea.gridy = 8;
		frame.getContentPane().add(lblUsuarioOContrasea, gbc_lblUsuarioOContrasea);
		lblTodosLosCampos.setForeground(Color.RED);
		GridBagConstraints gbc_lblTodosLosCampos = new GridBagConstraints();
		gbc_lblTodosLosCampos.gridwidth = 9;
		gbc_lblTodosLosCampos.gridx = 0;
		gbc_lblTodosLosCampos.gridy = 9;
		frame.getContentPane().add(lblTodosLosCampos, gbc_lblTodosLosCampos);
		
		
	}
	/*
	 * Comprueba que todos los campos han sido rellenados
	 */
	private boolean checkFields() {
		String pw1 = new String (passwordField.getPassword());
		if (userField.getText().isEmpty() ||
				pw1.isEmpty()) {
			lblTodosLosCampos.setVisible(true);
			return false;
		}
		else return true;
	}

}
