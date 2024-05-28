package MineGame.listCallRenderer;

import MineGame.GameRecordPanel;
import MineGame.RecordInUserPanel;

import javax.swing.*;
import java.awt.*;

public class RecordinUserListCellRenderer implements ListCellRenderer<RecordInUserPanel> {
    @Override
    public Component getListCellRendererComponent(JList<? extends RecordInUserPanel> list, RecordInUserPanel value, int index,
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