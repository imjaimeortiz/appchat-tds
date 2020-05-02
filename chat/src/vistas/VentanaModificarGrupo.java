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
	private LinkedList<Grupo> grupos;
	
	/**
	 * Create the application.
	 */
	public VentanaModificarGrupo() {
		this.comboBox = new JComboBox<String>();
		this.grupos = ControladorChat.getUnicaInstancia().gruposAdmin(ControladorChat.usuarioActual);
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
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		comboBox.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String group = (String) comboBox.getSelectedItem();
				for (Grupo g : grupos) {
					if (g.getNombre().equals(group)) {
						VentanaGrupo ventanaGrupo = new VentanaGrupo(g);
						ventanaGrupo.setModal(true);
						ventanaGrupo.setVisible(true);
						frame.dispose();
					}
				}
			}
		});
		frame.getContentPane().add(comboBox);
		frame.setVisible(true);
	}

}
