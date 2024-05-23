package mineGame.Screen.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class VerticalLineSeparator extends JPanel {

    public VerticalLineSeparator() {
    	setPreferredSize(new Dimension(1, 0)); // Set preferred width of 1 pixel
        setBackground(Color.BLACK); // Set background color to black
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground()); // Use the background color to draw the line
        g.fillRect(0, 0, getWidth(), getHeight()); // Draw a filled rectangle
    }
}
