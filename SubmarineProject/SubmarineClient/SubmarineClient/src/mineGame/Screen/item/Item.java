package mineGame.Screen.item;

import javax.swing.*;

public class Item {
	String text;
    Icon icon;

    public Item(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
