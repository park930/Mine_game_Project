package MineGame.listCallRenderer;

import MineGame.Component.UserGameRecordPanel;

import javax.swing.*;
import java.awt.*;

public class UserGameRecordPanelListCellRenderer implements ListCellRenderer<UserGameRecordPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends UserGameRecordPanel> list, UserGameRecordPanel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        value.setOpaque(true);  // Ensure the panel is opaque
        value.setPreferredSize(new Dimension(424, 21));  // Set preferred size
        return value;
    }
}