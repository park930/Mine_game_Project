package mineGame.Screen.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mineGame.GameRoom;
import mineGame.User;

public class GameRoomPanel extends JPanel {

	private JLabel nameLabel;
    private ImageIcon originalIcon;
    private Image image;
    private JLabel mapSizeLabel;
    private JLabel userNumLabel;
    private JLabel mineNumLabel;
    
    public GameRoomPanel(GameRoom gameRoom) {
    	setBackground(new Color(23, 30, 43));
        setLayout(null);
        originalIcon = new ImageIcon(InGameUserPanel.class.getResource("/mineGame/Screen/icon/person.png"));
        image = originalIcon.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
       
        
        setOpaque(false);  // Ensure background is transparent
        
     // Set preferred size to ensure it is displayed correctly in JList
        setPreferredSize(new Dimension(569, 84));
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(221, 211, 187));
        mainPanel.setBounds(0, 0, 570, 83);
        add(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBorder(new RoundedBorder(7, 2, Color.BLACK, 1));
        nameLabel = new JLabel(gameRoom.getRoomName());
        nameLabel.setBounds(13, 8, 286, 33);
        mainPanel.add(nameLabel);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        
        JPanel roomInfoPanel = new JPanel();
        roomInfoPanel.setBackground(new Color(221, 211, 187));
        roomInfoPanel.setBounds(11, 46, 473, 31);
        mainPanel.add(roomInfoPanel);
        roomInfoPanel.setLayout(null);
        
        mapSizeLabel = new JLabel("Map Size : "+gameRoom.getMapSize()+"x"+gameRoom.getMapSize());
        mapSizeLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
        mapSizeLabel.setBounds(287, 10, 115, 15);
        roomInfoPanel.add(mapSizeLabel);
        
        userNumLabel = new JLabel("Player : "+gameRoom.getPlayerList().size()+"/"+gameRoom.getMaxPlayer());
        userNumLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
        userNumLabel.setBounds(32, 10, 82, 15);
        roomInfoPanel.add(userNumLabel);
        
        mineNumLabel = new JLabel("Mine : "+gameRoom.getMineNum());
        mineNumLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
        mineNumLabel.setBounds(166, 10, 69, 15);
        roomInfoPanel.add(mineNumLabel);
        
        VerticalLineSeparator lineSeparator = new VerticalLineSeparator();
        lineSeparator.setBackground(new Color(120, 92, 41));
        lineSeparator.setBounds(492, 5, 2, 69);
        mainPanel.add(lineSeparator);
        
        LineSeparator lineSeparator2 = new LineSeparator();
        lineSeparator2.setBounds(12, 44, 470, 2);
        mainPanel.add(lineSeparator2);
        
        JLabel roomStateLabel = new JLabel();
        roomStateLabel.setText("Waiting");
        if(gameRoom.isStart()) roomStateLabel.setText("Waiting");
        else roomStateLabel.setText("Gaming");
        roomStateLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        roomStateLabel.setBounds(502, 21, 63, 43);
        mainPanel.add(roomStateLabel);
        
        JLabel chairmanLabel = new JLabel("( creater:"+gameRoom.getChairmanId()+" )");
        chairmanLabel.setBounds(340, 23, 139, 15);
        mainPanel.add(chairmanLabel);
        chairmanLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
        
    }
    

    public void updatePanel(User user) {
        mapSizeLabel.setText("Trial:"+user.getTotalChoice());
        userNumLabel.setText("Find Mine:"+user.getRight());
        mineNumLabel.setText("("+String.format("%.2f",user.getFindRate())+"%)");
    }
    
}