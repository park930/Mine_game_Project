package MineGame.Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import MineGame.BackgroundPanel;
import room.User;

public class InGameUserPanel extends JPanel {

	private JLabel nameLabel;
    private JLabel statisticsInfoLabel;
    private ImageIcon originalIcon;
    private Image image;
    private JLabel turnLabel;
    private JLabel choiceTotalLabel;
    private JLabel rightLabel;
    private JLabel rateLabel;
    private JPanel bp;
    
    public InGameUserPanel(User user) {
    	

    	bp = new JPanel();
    	bp.setBackground(new Color(128, 128, 128));
    	bp.setBounds(0, 0, 322, 105);
    	add(bp);
    	bp.setLayout(null);
    	
       
        JPanel mainPanel = new JPanel();
    	mainPanel.setBounds(0, 0, 250, 104);
    	bp.add(mainPanel);
    	mainPanel.setOpaque(false);
    	mainPanel.setLayout(null);
//    	mainPanel.setBorder(new RoundedBorder(7, 2, Color.white, 2));
    	
    	nameLabel = new JLabel(user.getUserName());
    	nameLabel.setForeground(new Color(255, 255, 255));
    	nameLabel.setBounds(59, 5, 130, 23);
    	mainPanel.add(nameLabel);
    	nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
    	
        statisticsInfoLabel = new JLabel();
    	statisticsInfoLabel.setForeground(new Color(255, 255, 255));
    	statisticsInfoLabel.setBounds(58, 53, 185, 15);
        mainPanel.add(statisticsInfoLabel);
    	statisticsInfoLabel.setFont(new Font("Arial", Font.BOLD, 12));
    	statisticsInfoLabel.setIcon(null);
        statisticsInfoLabel.setText("total:"+user.getTotal()+"   win:"+user.getWin() +"   lose:"+user.getLose() +"   ("+String.format("%.2f", user.getRating())+"%)");
        
        
        JLabel userIconLabel = new JLabel();
    	userIconLabel.setBounds(11, 21, 39, 39);
    	userIconLabel.setIcon(new ImageIcon((new ImageIcon(InGameUserPanel.class.getResource("/MineGame/icon/roundUserImg.png"))).getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH)));
    	mainPanel.add(userIconLabel);
        
        LineSeparator lineSeparator = new LineSeparator();
    	lineSeparator.setBackground(new Color(255, 255, 255));
    	lineSeparator.setBounds(10, 72, 227, 2);
        mainPanel.add(lineSeparator);
        
        JPanel userGameInfoPanel = new JPanel();
    	userGameInfoPanel.setOpaque(false);
    	userGameInfoPanel.setBounds(11, 77, 235, 26);
    	mainPanel.add(userGameInfoPanel);
    	userGameInfoPanel.setLayout(null);
        
        choiceTotalLabel = new JLabel("Trial:"+user.getTotalChoice());
    	choiceTotalLabel.setForeground(new Color(255, 255, 255));
    	choiceTotalLabel.setFont(new Font("Arial", Font.BOLD, 13));
    	choiceTotalLabel.setBounds(4, 5, 57, 15);
    	userGameInfoPanel.add(choiceTotalLabel);

    	rightLabel = new JLabel("Find Mine:"+user.getRight());
    	rightLabel.setForeground(new Color(255, 255, 255));
    	rightLabel.setFont(new Font("Arial", Font.BOLD, 13));
    	rightLabel.setBounds(72, 5, 88, 15);
    	userGameInfoPanel.add(rightLabel);
        
        rateLabel = new JLabel("("+String.format("%.2f", user.getFindRate())+"%)");
    	rateLabel.setForeground(new Color(255, 255, 255));
    	rateLabel.setFont(new Font("Arial", Font.BOLD, 13));
    	rateLabel.setBounds(166, 4, 65, 15);
    	userGameInfoPanel.add(rateLabel);
        
        JLabel userIdLabel = new JLabel("(id:"+user.getId()+")");
    	userIdLabel.setForeground(new Color(255, 255, 255));
    	userIdLabel.setBounds(63, 30, 97, 16);
    	mainPanel.add(userIdLabel);
    	userIdLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JPanel turnPanel = new JPanel();
    	turnPanel.setBounds(251, 4, 65, 95);
    	bp.add(turnPanel);
    	turnPanel.setOpaque(false);
    	turnPanel.setLayout(null);
        
    	turnLabel = new JLabel("");
    	turnLabel.setBounds(1, 1, 65, 94);
    	turnPanel.add(turnLabel);
    	turnLabel.setIcon(new ImageIcon((new ImageIcon(InGameUserPanel.class.getResource("/MineGame/icon/turnIcon.png"))).getImage().getScaledInstance(65, 94, Image.SCALE_SMOOTH)));
    	
    	setBackground(new Color(23, 30, 43));
        setLayout(null);
        originalIcon = new ImageIcon(InGameUserPanel.class.getResource("/MineGame/icon/roundUserImg.png"));
        image = originalIcon.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
       

        setOpaque(false);
        setPreferredSize(new Dimension(322, 107));
        
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