package mineGame.ListCallRenderer;

import mineGame.ChatInfo;

import javax.swing.*;
import java.awt.*;

public class ChatInfoListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof ChatInfo) {
            ChatInfo chatInfo = (ChatInfo) value;
            setText(chatInfo.toString());
            setForeground(parseColor(chatInfo.getColor()));
        }
        return component;
    }

    private Color parseColor(String colorString) {
        try {
            return Color.decode(colorString);
        } catch (NumberFormatException e) {
            // 기본 색상을 반환 (색상 문자열이 잘못된 경우)
            return Color.BLACK;
        }
    }
}