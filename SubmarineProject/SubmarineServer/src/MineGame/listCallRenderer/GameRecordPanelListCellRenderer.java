package MineGame.listCallRenderer;

import MineGame.GameRecordPanel;
import MineGame.UserInfoPanel;

import javax.swing.*;
import java.awt.*;

public class GameRecordPanelListCellRenderer implements ListCellRenderer<GameRecordPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends GameRecordPanel> list, GameRecordPanel value, int index,
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