package mineGame.ListCallRenderer;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import mineGame.Screen.component.GameRoomUserPanel;
import mineGame.Screen.component.UserPanel;

public class GameRoomUserCellRenderer implements ListCellRenderer<GameRoomUserPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends GameRoomUserPanel> list, GameRoomUserPanel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        value.setOpaque(true);  // Ensure the panel is opaque
        value.setPreferredSize(new Dimension(304, 68));  // Set preferred size
        return value;
    }
}