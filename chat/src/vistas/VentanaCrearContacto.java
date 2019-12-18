package vistas;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controlador.ControladorChat;
import modelo.Usuario;

public class VentanaCrearContacto {

	private JFrame frame;
	
	public VentanaCrearContacto(Usuario user) {
		frame = new JFrame();
		String nombre = JOptionPane.showInputDialog(frame, "Nombre");
		String tlf = JOptionPane.showInputDialog(frame, "Teléfono");
		ControladorChat.getUnicaInstancia().addContacto(user, tlf, nombre);
	}
}
