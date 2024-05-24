package mineGame.ListCallRenderer;

import mineGame.GameRoom;
import mineGame.Screen.component.GameRoomPanel;
import mineGame.User;

import javax.swing.*;
import java.awt.*;

public class RoomListCellRenderer implements ListCellRenderer<GameRoomPanel> {

    @Override
    public Component getListCellRendererComponent(JList<? extends GameRoomPanel> list, GameRoomPanel value, int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        value.setOpaque(true);  // Ensure the panel is opaque
        value.setPreferredSize(new Dimension(603, 65));  // Set preferred size
        return value;
    }
}
