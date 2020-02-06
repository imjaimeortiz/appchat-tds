package vistas;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class ChatRenderer extends JButton implements ListCellRenderer<Chats> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1656778397280123692L;

	public Component getListCellRendererComponent(JList <? extends Chats> list, Chats chat, int index,
        boolean isSelected, boolean cellHasFocus) {
          
        String code = chat.getName();
        //ImageIcon imageIcon = new ImageIcon(getClass().getResource(chat.getPhotoPath()));
        //String date = chat.getDate().toString();
         
        setIcon(null);
        //setIcon(imageIcon);
        setText(code);
        //setText(code+" "+date);
        return this;
    }
     
}