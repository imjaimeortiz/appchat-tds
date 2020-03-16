package vistas;

import javax.swing.JFrame;

import modelo.Usuario;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.ControladorChat;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;

public class VentanaUser {

	private JFrame frame;
	private Usuario user;
	private JLabel lblUserName;
	private JButton btnSalir;
	private JButton btnCambiarImagen;
	private JLabel labelImage;

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
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		lblUserName = new JLabel(user.getNombre());
		panel_1.add(lblUserName);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		labelImage = new JLabel("");
		labelImage.setIcon(redimensionar(new ImageIcon(user.getImagen())));
		panel_2.add(labelImage, BorderLayout.CENTER);
		
		btnCambiarImagen = new JButton("Cambiar foto perfil");
		panel.add(btnCambiarImagen);
		btnCambiarImagen.addActionListener( e -> {
			JFileChooser file = new JFileChooser();
			FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("PNG Images", "png");
			file.setFileFilter(imgFilter);
			file.setFileSelectionMode(JFileChooser.FILES_ONLY);
			file.showOpenDialog(frame);
			File f = file.getSelectedFile();
			if (f != null) {
				String path = file.getSelectedFile().getAbsolutePath();
				ImageIcon img = new ImageIcon(path);
				ImageIcon red = redimensionar(img);
				ControladorChat.getUnicaInstancia().setImage(path, user);
				labelImage.setIcon(red);
			}
		});
		
		btnSalir = new JButton("Salir");
		panel.add(btnSalir);
		btnSalir.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
	
	private ImageIcon redimensionar(ImageIcon imagen) {
		int heigth = imagen.getIconHeight();
		int width = imagen.getIconWidth();
		int newWidth = 200;
		int newHeigth = (heigth * newWidth) / width;
		Image image = imagen.getImage();
		Image nueva = image.getScaledInstance(newWidth, newHeigth, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(nueva);
	}

}
