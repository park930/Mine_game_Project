package MineGame.listCallRenderer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import MineGame.UserInfoPanel;

public class UserPanelListCellRenderer implements ListCellRenderer<UserInfoPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends UserInfoPanel> list, UserInfoPanel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        value.setOpaque(true);  // Ensure the panel is opaque
        value.setPreferredSize(new Dimension(208, 28));  // Set preferred size
        return value;
    }
}