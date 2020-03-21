package vistas;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import modelo.Contacto;
import modelo.Mensaje;
import modelo.Usuario;

import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JTextField;

import com.itextpdf.text.List;
import com.toedter.calendar.JDateChooser;

import controlador.ControladorChat;
import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.Panel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class VentanaSearchMessage {

	private JFrame frame;
	private Contacto c;
	private Usuario user;
	private JTextField textField;

	/**
	 * Create the application.
	 */
	public VentanaSearchMessage(Usuario user, Contacto c) {
		this.c = c;
		this.user = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		Panel panelFilters = new Panel();
		frame.getContentPane().add(panelFilters, BorderLayout.NORTH);
		
		Label labelTexto = new Label("Texto");
		panelFilters.add(labelTexto);
		
		textField = new JTextField();
		panelFilters.add(textField);
		textField.setColumns(10);
		
		JDateChooser dateChooserMin = new JDateChooser();
		panelFilters.add(dateChooserMin);
		
		JDateChooser dateChooserMax = new JDateChooser();
		panelFilters.add(dateChooserMax);
		
		Button button = new Button("Buscar");
		panelFilters.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JList<Mensaje> list = new JList<Mensaje>();
		scrollPane.setViewportView(list);
		
		DefaultListModel<Mensaje> listModel = new DefaultListModel<Mensaje>();
		
		list.setModel(listModel);
		scrollPane.setViewportView(list);
		
		button.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// filtrar por fecha
				if (textField.getText().equals(null) && !dateChooserMax.getDate().equals(null) && !dateChooserMin.getDate().equals(null)) {
					LinkedList<Mensaje> mensajes = (LinkedList<Mensaje>) ControladorChat.getUnicaInstancia().buscarMensaje(c, user.getNombre(), dateChooserMin.getDate(), dateChooserMax.getDate());
					for (Mensaje m : mensajes) {
						listModel.addElement(m);
					}
				}
				// filtrar por texto
				else if (!textField.getText().equals(null) && dateChooserMax.getDate().equals(null) && dateChooserMin.getDate().equals(null)) {
					LinkedList<Mensaje> mensajes = (LinkedList<Mensaje>) ControladorChat.getUnicaInstancia().buscarMensaje(c, user.getNombre(), dateChooserMin.getDate(), dateChooserMax.getDate());
					for (Mensaje m : mensajes) {
						listModel.addElement(m);
					}
				}
				// filtrar por todo
				else if (!textField.getText().equals(null) && !dateChooserMax.getDate().equals(null) && !dateChooserMin.getDate().equals(null)) {
					LinkedList<Mensaje> mensajes = (LinkedList<Mensaje>) ControladorChat.getUnicaInstancia().buscarMensaje(c, user.getNombre(), textField.getText(), dateChooserMin.getDate(), dateChooserMax.getDate());
					for (Mensaje m : mensajes) {
						listModel.addElement(m);
					}
				}
				
				scrollPane.revalidate();
				scrollPane.repaint();
			}
		});
		
		frame.setVisible(true);
	}

}
