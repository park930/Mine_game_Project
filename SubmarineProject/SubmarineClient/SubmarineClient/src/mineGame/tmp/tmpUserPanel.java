package mineGame.tmp;

import mineGame.User;
import mineGame.Screen.component.BackgroundPanel;
import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.LineSeparator;
import mineGame.Screen.component.RoundedBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;

public class tmpUserPanel extends JPanel {
	private JLabel nameLabel;
    private JLabel idLabel;
    private ImageIcon originalIcon;
    private Image image;
    private BackgroundPanel bp;
    
    public tmpUserPanel(User user) {
    	setForeground(new Color(255, 255, 255));
        setLayout(null);
        
    	bp = new BackgroundPanel("/mineGame/Screen/icon/userBackground.png");
    	bp.setBounds(0, 0, 190, 47);
    	bp.setBorder(new RoundedBorder(7, 2, Color.white, 2));
        add(bp);
        bp.setLayout(null);
    	
        nameLabel = new JLabel(user.getUserName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(new Color(255, 255, 255));
        nameLabel.setBounds(40, 7, 121, 21);
        bp.add(nameLabel);
        
        idLabel = new JLabel("total:10 win:0 lose:0 (100.00%)");
        idLabel.setBounds(35, 28, 154, 12);
        idLabel.setForeground(new Color(255, 255, 255));
        idLabel.setFont(new Font("Arial", Font.BOLD, 10));
        idLabel.setIcon(null);
        
        bp.add(idLabel);
        setLayout(null);
        

        originalIcon = new ImageIcon(tmpUserPanel.class.getResource("/mineGame/Screen/icon/person.png"));
        image = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBounds(9, 11, 24, 24);
        bp.add(lblNewLabel);
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setIcon(new ImageIcon(image));
        setBorder(null);
        setOpaque(false);
        setPreferredSize(new Dimension(191, 47));
        
    }

    public void updatePanel(String newName, int newId) {
        nameLabel.setText("Name: " + newName);
        idLabel.setText("ID: " + newId);
    }
}