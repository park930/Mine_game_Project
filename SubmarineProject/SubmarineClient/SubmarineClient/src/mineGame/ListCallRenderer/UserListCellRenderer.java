package mineGame.ListCallRenderer;

import mineGame.User;

import javax.swing.*;
import java.awt.*;

public class UserListCellRenderer extends JLabel implements ListCellRenderer<User> {
    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
        setText(user.getDisplayName());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);

        return this;
    }
}
