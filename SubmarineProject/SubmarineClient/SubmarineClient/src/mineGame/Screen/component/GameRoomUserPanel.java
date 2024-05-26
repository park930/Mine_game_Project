package mineGame.Screen.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mineGame.User;
import mineGame.tmp.tmpUserPanel;
import javax.swing.SwingConstants;

public class GameRoomUserPanel extends JPanel {

	private JLabel nameLabel;
    private JLabel idLabel;
    private ImageIcon originalIcon;
    private Image image;
    private BackgroundPanel bp;
    
    public GameRoomUserPanel(User user) {
        System.out.println("패널 생성 시작");
    	setForeground(new Color(255, 255, 255));
        setLayout(null);
        
        
        
    	bp = new BackgroundPanel("/mineGame/Screen/icon/userBackground.png");
    	bp.setBounds(0, 0, 304, 68);
    	bp.setBorder(new RoundedBorder(7, 2, Color.white, 2));
        add(bp);
        bp.setLayout(null);
        
        nameLabel = new JLabel(user.getUserName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setForeground(new Color(255, 255, 255));
        nameLabel.setBounds(45, 12, 138, 26);
        bp.add(nameLabel);
        
        idLabel = new JLabel("total:"+user.getTotal()+" win:"+user.getWin()+" lose:"+user.getLose()+" ("+ String.format("%.2f",user.getRating())+"%)");
        idLabel.setBounds(45, 42, 187, 17);
        idLabel.setForeground(new Color(255, 255, 255));
        idLabel.setFont(new Font("Arial", Font.BOLD, 13));
        idLabel.setIcon(null);
        bp.add(idLabel);
        
        
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setBounds(7, 18, 34, 35);
        bp.add(lblNewLabel);
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/person.png"))).getImage().getScaledInstance(34, 35, Image.SCALE_SMOOTH)));
        
        JLabel stateLabel = new JLabel("Ready");
        stateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        stateLabel.setForeground(new Color(214, 237, 254));
        stateLabel.setFont(new Font("Agency FB", Font.BOLD, 24));
        stateLabel.setBounds(235, 14, 59, 37);
        if(user.isReady()) stateLabel.setText("Ready");
        else stateLabel.setText("");
        bp.add(stateLabel);
        
        
        setOpaque(false);
        setPreferredSize(new Dimension(304, 68));
        setLayout(null);
        setBorder(null);
        System.out.println("끝");
    }

    public void updatePanel(String newName, int newId) {
        nameLabel.setText("Name: " + newName);
        idLabel.setText("ID: " + newId);
    }
}
