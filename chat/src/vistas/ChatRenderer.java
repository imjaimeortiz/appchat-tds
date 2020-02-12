package vistas;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Grupo;
import modelo.Usuario;


public class ChatRenderer extends JButton implements ListCellRenderer<Contacto> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1656778397280123692L;

	@SuppressWarnings("deprecation")
	public Component getListCellRendererComponent(JList <? extends Contacto> list, Contacto chat, int index,
        boolean isSelected, boolean cellHasFocus) {
        
		String name = "";

		if (!chat.getNombre().equals(null)) {
			name = chat.getNombre();
		}
		
        ImageIcon imageIcon;
        String date = "";
        
        if (chat instanceof ContactoIndividual) {
        	Usuario user = ((ContactoIndividual) chat).getUsuario();
			if (!user.getImagen().equals(null)) {
        		imageIcon = new ImageIcon(getClass().getResource(((ContactoIndividual) chat).getUsuario().getImagen()));
        	}
        	else {
        		imageIcon = new ImageIcon(getClass().getResource(("/vistas/avatar.png")));
        	}
        }
        
        else {
        	name = ((Grupo)chat).getNombre();
        	imageIcon = new ImageIcon(getClass().getResource(((Grupo) chat).getFoto()));
        }
        
        if (chat.getMensajes().size() > 0) 
        	date = chat.getMensajes().get(chat.getMensajes().size()-1).getHora().toLocaleString();
         
        setIcon(imageIcon);
        setText(name + " " + date);
        return this;
    }


}