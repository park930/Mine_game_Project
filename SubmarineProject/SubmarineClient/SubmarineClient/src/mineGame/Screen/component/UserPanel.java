package mineGame.Screen.component;

import mineGame.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;

public class UserPanel extends JPanel {
	private JLabel nameLabel;
    private JLabel idLabel;
    private ImageIcon originalIcon;
    private Image image;
    
    public UserPanel(User user) {
    	setBackground(new Color(23, 30, 43));
        nameLabel = new JLabel(user.getUserName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setBounds(44, 4, 90, 17);
        idLabel = new JLabel("total:"+user.getTotal()+" win:"+user.getWin()+" lose:"+user.getLose()+" ("+ String.format("%.2f",user.getRating())+"%)");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 9));
        idLabel.setBounds(53, 19, 116, 14);
        idLabel.setIcon(null);
        setLayout(null);

        add(nameLabel);
        add(idLabel);
        setBorder(new RoundedBorder(7, 2, Color.BLACK, 1));
        
        JLabel lblNewLabel = new JLabel();
        originalIcon = new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/person.png"));
        image = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        
        lblNewLabel.setIcon(new ImageIcon(image));
        lblNewLabel.setBounds(9, 6, 24, 24);
        add(lblNewLabel);
        setOpaque(false);  // Ensure background is transparent
        
     // Set preferred size to ensure it is displayed correctly in JList
        setPreferredSize(new Dimension(183, 36));
        
    }

    public void updatePanel(String newName, int newId) {
        nameLabel.setText("Name: " + newName);
        idLabel.setText("ID: " + newId);
    }
}