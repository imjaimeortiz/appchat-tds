package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JFrame;

import controlador.ControladorChat;
import modelo.Grupo;
import modelo.Usuario;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;

public class VentanaModificarGrupo {

	private JFrame frame;
	private JComboBox<String> comboBox;
	
	/**
	 * Create the application.
	 */
	public VentanaModificarGrupo(Usuario user) {
		this.comboBox = new JComboBox<String>();
		LinkedList<Grupo> grupos = ControladorChat.getUnicaInstancia().gruposAdmin(user);
		for (Grupo grupo : grupos) {
			comboBox.addItem(grupo.getNombre());
		}		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(200, 200, 250, 90);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		comboBox.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new VentanaGrupo((Grupo) comboBox.getSelectedItem());
			}
		});

		
		
		frame.getContentPane().add(comboBox);
		frame.setVisible(true);
	}

}
