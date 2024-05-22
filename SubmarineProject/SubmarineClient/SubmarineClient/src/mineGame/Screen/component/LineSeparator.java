package mineGame.Screen.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class LineSeparator extends JPanel {

    public LineSeparator() {
        setPreferredSize(new Dimension(0, 1)); // Set preferred height of 1 pixel
        setBackground(Color.BLACK); // Set background color to black
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground()); // Use the background color to draw the line
        g.fillRect(0, 0, getWidth(), getHeight()); // Draw a filled rectangle
    }
}
