package MineGame.listCallRenderer;

import MineGame.Component.RoomChatPanel;
import MineGame.RecordInUserPanel;

import javax.swing.*;
import java.awt.*;

public class ChatLogRoomListCellRenderer implements ListCellRenderer<RoomChatPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends RoomChatPanel> list, RoomChatPanel value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            value.setBackground(list.getSelectionBackground());
            value.setForeground(list.getSelectionForeground());
        } else {
            value.setBackground(list.getBackground());
            value.setForeground(list.getForeground());
        }
        value.setOpaque(true);  // Ensure the panel is opaque
        value.setPreferredSize(new Dimension(280, 23));  // Set preferred size
        return value;
    }
}