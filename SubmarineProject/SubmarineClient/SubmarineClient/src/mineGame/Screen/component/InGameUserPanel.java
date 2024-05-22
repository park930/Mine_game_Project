package mineGame.Screen.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mineGame.User;

public class InGameUserPanel extends JPanel {
	private JLabel nameLabel;
    private JLabel statisticsInfoLabel;
    private ImageIcon originalIcon;
    private Image image;
    private JLabel turnLabel;
    private JLabel choiceTotalLabel;
    private JLabel rightLabel;
    private JLabel rateLabel;
    
    public InGameUserPanel(User user) {
    	setBackground(new Color(23, 30, 43));
        setLayout(null);
        originalIcon = new ImageIcon(InGameUserPanel.class.getResource("/mineGame/Screen/icon/person.png"));
        image = originalIcon.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
       
        
        setOpaque(false);  // Ensure background is transparent
        
     // Set preferred size to ensure it is displayed correctly in JList
        setPreferredSize(new Dimension(322, 121));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 0, 250, 119);
        add(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBorder(new RoundedBorder(7, 2));
        nameLabel = new JLabel(user.getUserName());
        nameLabel.setBounds(59, 5, 130, 23);
        mainPanel.add(nameLabel);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        statisticsInfoLabel = new JLabel();
        statisticsInfoLabel.setBounds(64, 55, 170, 15);
        mainPanel.add(statisticsInfoLabel);
        statisticsInfoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statisticsInfoLabel.setIcon(null);
        statisticsInfoLabel.setText("total:"+user.getTotal()+"   win:"+user.getWin() +"   lose:"+user.getLose() +"   ("+String.format("%.2f", user.getRating())+"%)");
        
        
        JLabel userIconLabel = new JLabel();
        userIconLabel.setBounds(9, 16, 39, 39);
        mainPanel.add(userIconLabel);
        
        userIconLabel.setIcon(new ImageIcon(image));
        
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setBounds(10, 72, 227, 2);
        mainPanel.add(lineSeparator);
        
        JPanel userGameInfoPanel = new JPanel();
        userGameInfoPanel.setBounds(11, 77, 227, 33);
        mainPanel.add(userGameInfoPanel);
        userGameInfoPanel.setLayout(null);
        
        choiceTotalLabel = new JLabel("Trial:"+user.getTotalChoice());
        choiceTotalLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
        choiceTotalLabel.setBounds(11, 10, 57, 15);
        userGameInfoPanel.add(choiceTotalLabel);
        
        rightLabel = new JLabel("Find Mine:"+user.getRight());
        rightLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
        rightLabel.setBounds(65, 10, 78, 15);
        userGameInfoPanel.add(rightLabel);
        
        rateLabel = new JLabel("(100.00%)");
        rateLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
        rateLabel.setBounds(142, 10, 69, 15);
        userGameInfoPanel.add(rateLabel);
        
        JLabel userIdLabel = new JLabel("(id:"+user.getId()+")");
        userIdLabel.setBounds(63, 30, 97, 16);
        mainPanel.add(userIdLabel);
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JPanel turnPanel = new JPanel();
        turnPanel.setBounds(251, 4, 65, 111);
        turnPanel.setOpaque(false);
        add(turnPanel);
        turnPanel.setLayout(null);
        
        turnLabel = new JLabel("");
        turnLabel.setBounds(0, 10, 65, 94);
        turnPanel.add(turnLabel);
        turnLabel.setIcon(new ImageIcon((new ImageIcon(InGameUserPanel.class.getResource("/mineGame/Screen/icon/turnIcon.png"))).getImage().getScaledInstance(65, 94, Image.SCALE_SMOOTH)));
        
    }
    

    public void updatePanel(User user) {
        choiceTotalLabel.setText("Trial:"+user.getTotalChoice());
        rightLabel.setText("Find Mine:"+user.getRight());
        rateLabel.setText("("+String.format("%.2f",user.getFindRate())+"%)");
    }
    
    public void showTurnLabel(boolean show) {
        turnLabel.setVisible(show);
    }
    
    
}