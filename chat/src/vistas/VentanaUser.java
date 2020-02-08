package vistas;

import javax.swing.JFrame;

import modelo.Usuario;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;

public class VentanaUser {

	private JFrame frame;
	private Usuario user;
	private JLabel lblUserName;
	private JButton btnNewButton;
	private JButton btnSalir;
	private JButton btnCambiarImagen;

	/**
	 * Create the application.
	 */
	public VentanaUser(Usuario user) {
		this.user = user;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 330, 296);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnCambiarImagen = new JButton("Cambiar foto perfil");
		panel.add(btnCambiarImagen);
		btnCambiarImagen.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		btnSalir = new JButton("Salir");
		panel.add(btnSalir);
		btnSalir.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		lblUserName = new JLabel(user.getNombre());
		panel_1.add(lblUserName);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(VentanaUser.class.getResource(user.getImagen())));
		panel_2.add(btnNewButton);
	}

}
