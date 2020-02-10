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

	public Component getListCellRendererComponent(JList <? extends Contacto> list, Contacto chat, int index,
        boolean isSelected, boolean cellHasFocus) {
          
        String name = chat.getNombre();
        ImageIcon imageIcon;
        if (chat instanceof ContactoIndividual) {
        	Usuario user = ((ContactoIndividual) chat).getUsuario();
			if (!user.getImagen().equals(null)) {
        		imageIcon = new ImageIcon(getClass().getResource(((ContactoIndividual) chat).getUsuario().getImagen()));
        	}
        	else {
        		imageIcon = new ImageIcon(getClass().getResource(("/vistas/avatar.png")));
        	}
			 setIcon(imageIcon);
        }
       // else if (chat instanceof Grupo)
        //	imageIcon = ((Grupo) chat).getFoto();
        //}
        //String date = chat.getDate().toString();
         
       // setIcon(imageIcon);
        //setIcon(imageIcon);
        setText(name);
        //setText(code+" "+date);
        return this;
    }


}