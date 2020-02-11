package vistas;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modelo.Contacto;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.TextArea;
import java.time.LocalDate;

public class Chats extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -585496333970497924L;
	private Contacto c;
	private String user;
	private JScrollPane scrollPane;
	private DefaultListModel<Contacto> listModel;
	/**
	 * Create the panel.
	 */
	public Chats(Contacto c, String user, JScrollPane scrollPane, DefaultListModel<Contacto> listModel) {
		this.c = c;
		this.user = user;
		this.scrollPane = scrollPane;
		this.listModel = listModel;
		initialize();
	}


	private void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(photoPath));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		this.add(btnNewButton, gbc_btnNewButton);
		
		JLabel lblJaimeOrtiz = new JLabel(name);
		GridBagConstraints gbc_lblJaimeOrtiz = new GridBagConstraints();
		gbc_lblJaimeOrtiz.gridwidth = 4;
		gbc_lblJaimeOrtiz.insets = new Insets(0, 0, 0, 5);
		gbc_lblJaimeOrtiz.gridx = 3;
		gbc_lblJaimeOrtiz.gridy = 0;
		this.add(lblJaimeOrtiz, gbc_lblJaimeOrtiz);
		
		JLabel label = new JLabel("date");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 7;
		gbc_label.gridy = 0;
		this.add(label, gbc_label);
		
		this.setVisible(true);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public LocalDate getDate() {
		return date;
	}
	
}
