package MineGame.listCallRenderer;

import MineGame.Component.InGameUserPanel;
import MineGame.UserInfoPanel;

import javax.swing.*;
import java.awt.*;

public class InGameUserPanelListCellRenderer implements ListCellRenderer<InGameUserPanel> {
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
        value.setPreferredSize(new Dimension(322, 107));  // Set preferred size
        return value;
    }
}