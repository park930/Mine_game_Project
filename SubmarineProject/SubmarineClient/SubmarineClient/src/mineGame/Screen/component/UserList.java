package mineGame.Screen.component;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import mineGame.Screen.item.Item;

public class UserList<E extends Object> extends JList<E>{
	
	private final DefaultListModel model;
	
	public UserList() {
		model = new DefaultListModel();
		setModel(model);
	}
	
	
	@Override
	public ListCellRenderer getCellRenderer() {
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object o, int index, boolean selected, boolean focus) {
				ListItem item = new ListItem();
				item.setItem(o);
				item.setBackground(UserList.this.getBackground());
				item.setForeground(UserList.this.getForeground());
				return item;
			}
		};
	}
	
	public void addItem(Item item) {
		model.addElement(item);
	}
	
}
