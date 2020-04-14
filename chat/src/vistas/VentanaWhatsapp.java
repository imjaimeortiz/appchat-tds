package vistas;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

import controlador.ControladorChat;
import modelo.Plataforma;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaWhatsapp extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String filePath;
	/**
	 * Create the application.
	 */
	public VentanaWhatsapp(String filePath) {
		this.filePath = filePath;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 300, 160);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblSistemaOperativo = new JLabel("Sistema Operativo :");
		GridBagConstraints gbc_lblSistemaOperativo = new GridBagConstraints();
		gbc_lblSistemaOperativo.insets = new Insets(0, 0, 5, 0);
		gbc_lblSistemaOperativo.fill = GridBagConstraints.VERTICAL;
		gbc_lblSistemaOperativo.gridwidth = 9;
		gbc_lblSistemaOperativo.gridx = 0;
		gbc_lblSistemaOperativo.gridy = 1;
		panel.add(lblSistemaOperativo, gbc_lblSistemaOperativo);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Android 1", "Android 2", "IOS" }));
		comboBox.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String SO = (String) comboBox.getSelectedItem();
				if (SO.equals("IOS"))
					ControladorChat.getUnicaInstancia().cargarMensajes(filePath, Plataforma.IOS, 0);
				else if (SO.equals("Android 1"))
					ControladorChat.getUnicaInstancia().cargarMensajes(filePath, Plataforma.ANDROID, 1);
				else 
					ControladorChat.getUnicaInstancia().cargarMensajes(filePath, Plataforma.ANDROID, 2);

				//esta comentada en el controlador por eso falla
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 6;
		gbc_comboBox.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 3;
		panel.add(comboBox, gbc_comboBox);
	}

}
