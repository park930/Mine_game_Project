package mineGame.ListCallRenderer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import mineGame.Screen.component.UserPanel;

public class PanelListCellRenderer implements ListCellRenderer<UserPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends UserPanel> list, UserPanel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        value.setOpaque(true);  // Ensure the panel is opaque
        value.setPreferredSize(new Dimension(191, 47));  // Set preferred size
        return value;
    }
}