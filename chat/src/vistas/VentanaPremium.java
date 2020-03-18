package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import controlador.ControladorChat;
import modelo.Usuario;

import java.awt.Font;

public class VentanaPremium {

	private JFrame frame;
	private JTextField textField;
	private Usuario user;

	/**
	 * Create the application.
	 */
	public VentanaPremium(Usuario user) {
		this.user = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 18, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblLaTarifaBase = new JLabel("Hazte premium por sólo 29'99 € / año !");
		lblLaTarifaBase.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblLaTarifaBase = new GridBagConstraints();
		gbc_lblLaTarifaBase.gridwidth = 4;
		gbc_lblLaTarifaBase.insets = new Insets(0, 0, 5, 5);
		gbc_lblLaTarifaBase.gridx = 0;
		gbc_lblLaTarifaBase.gridy = 1;
		frame.getContentPane().add(lblLaTarifaBase, gbc_lblLaTarifaBase);
		
		JLabel lblPrecioFinal = new JLabel("29.99€");
		lblPrecioFinal.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_lblPrecioFinal = new GridBagConstraints();
		gbc_lblPrecioFinal.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecioFinal.gridx = 3;
		gbc_lblPrecioFinal.gridy = 4;
		frame.getContentPane().add(lblPrecioFinal, gbc_lblPrecioFinal);
		
		JButton btnBuscarDescuento = new JButton("Buscar descuento");
		btnBuscarDescuento.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double precioFinal = ControladorChat.getUnicaInstancia().setPrecioFinal(user);
				lblPrecioFinal.setText(precioFinal + "");
			}
		});
		GridBagConstraints gbc_btnBuscarDescuento = new GridBagConstraints();
		gbc_btnBuscarDescuento.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscarDescuento.gridx = 3;
		gbc_btnBuscarDescuento.gridy = 3;
		frame.getContentPane().add(btnBuscarDescuento, gbc_btnBuscarDescuento);
		
		JLabel lblTarifaFinalA = new JLabel("Tarifa final a pagar");
		GridBagConstraints gbc_lblTarifaFinalA = new GridBagConstraints();
		gbc_lblTarifaFinalA.insets = new Insets(0, 0, 5, 5);
		gbc_lblTarifaFinalA.gridx = 2;
		gbc_lblTarifaFinalA.gridy = 4;
		frame.getContentPane().add(lblTarifaFinalA, gbc_lblTarifaFinalA);
		
		JLabel lblIntroduzcaSuNmero = new JLabel("Introduzca su número de tarjeta");
		GridBagConstraints gbc_lblIntroduzcaSuNmero = new GridBagConstraints();
		gbc_lblIntroduzcaSuNmero.anchor = GridBagConstraints.EAST;
		gbc_lblIntroduzcaSuNmero.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntroduzcaSuNmero.gridx = 2;
		gbc_lblIntroduzcaSuNmero.gridy = 5;
		frame.getContentPane().add(lblIntroduzcaSuNmero, gbc_lblIntroduzcaSuNmero);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 5;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ControladorChat.getUnicaInstancia().setPremium(user);
				frame.dispose();
			}
		});
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(0, 0, 0, 5);
		gbc_btnAceptar.gridx = 2;
		gbc_btnAceptar.gridy = 7;
		frame.getContentPane().add(btnAceptar, gbc_btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();	
			}
		}); 
		
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 7;
		frame.getContentPane().add(btnCancelar, gbc_btnCancelar);
	}

}
