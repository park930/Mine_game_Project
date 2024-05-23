package mineGame.Screen.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;


public class RoundPanel extends JPanel {
    private int radius;

    public RoundPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // 투명 배경을 설정하여 둥근 모서리가 제대로 보이도록 함
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}