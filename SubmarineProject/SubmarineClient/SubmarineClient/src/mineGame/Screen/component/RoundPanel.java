package mineGame.Screen.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class RoundPanel extends JPanel {
    private int radius;
    private Image backgroundImage;

    public RoundPanel(int radius, String imagePath) {
        this.radius = radius;
        setOpaque(false); // 투명 배경을 설정하여 둥근 모서리가 제대로 보이도록 함
        try {
        	backgroundImage = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 배경 이미지 그리기
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // 둥근 모서리 배경 그리기
//        g2d.setClip(null);
//        g2d.setColor(getBackground());
//        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}