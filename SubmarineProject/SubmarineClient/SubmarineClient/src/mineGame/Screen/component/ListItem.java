package mineGame.Screen.component;

import javax.swing.JPanel;

import mineGame.Screen.item.Item;

import java.awt.Color;

import javax.swing.JLabel;

public class ListItem extends JPanel {

	private JLabel lb;

	/**
	 * Create the panel.
	 */
	public ListItem() {
		setLayout(null);
		
		lb = new JLabel("New label");
		lb.setBounds(47, 10, 403, 25);
		add(lb);

	}
	
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if(lb!=null) {
			lb.setForeground(fg);
		}
	}
	
	
	public void setItem(Object object) {
		if(object instanceof Item) {
			Item item = (Item) object;
			lb.setIcon(item.getIcon());
			lb.setText(item.getText());
		} else {
			lb.setText(object+"");
		}
	}
	

}
