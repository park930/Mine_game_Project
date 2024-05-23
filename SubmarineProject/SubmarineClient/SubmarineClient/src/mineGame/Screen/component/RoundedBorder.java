package mineGame.Screen.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {
    private int radius;
    private int padding;
    private Color borderColor;
    private int thickness;
    public RoundedBorder(int radius, int padding, Color borderColor, int thickness) {
        this.radius = radius;
        this.padding = padding;
        this.borderColor = borderColor;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(borderColor);
        
        g2d.setStroke(new java.awt.BasicStroke(thickness));
        g2d.drawRoundRect(x + padding, y + padding, width - 2 * padding - 1, height - 2 * padding - 1, radius, radius);
    }


    @Override
    public Insets getBorderInsets(Component c) {
        int totalPadding = padding + thickness;
        return new Insets(totalPadding, totalPadding, totalPadding, totalPadding);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        int totalPadding = padding + thickness;
        insets.left = insets.right = insets.top = insets.bottom = totalPadding;
        return insets;
    }
}