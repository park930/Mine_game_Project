package mineGame.ListCallRenderer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.UserPanel;

public class InGamePanelListCellRenderer implements ListCellRenderer<InGameUserPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends InGameUserPanel> list, InGameUserPanel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        value.setOpaque(true);  // Ensure the panel is opaque
        value.setPreferredSize(new Dimension(322, 121));  // Set preferred size
        return value;
    }
}