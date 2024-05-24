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
    private JLabel roomStateLabel;
    private JLabel chairmanLabel;
    private GameRoom gameRoom;

    public GameRoomPanel(GameRoom gameRoom) {
        System.out.println("새로운 room 생성 : "+gameRoom);
        this.gameRoom = gameRoom;

    	setBackground(new Color(23, 30, 43));
        setLayout(null);
        originalIcon = new ImageIcon(InGameUserPanel.class.getResource("/mineGame/Screen/icon/person.png"));
        image = originalIcon.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
       
        
        setOpaque(false);  // Ensure background is transparent
        
     // Set preferred size to ensure it is displayed correctly in JList
        setPreferredSize(new Dimension(603, 65));
        
        BackgroundPanel mainPanel = new BackgroundPanel("/mineGame/Screen/icon/roomListBackground.png");
        mainPanel.setBackground(new Color(221, 211, 187));
        mainPanel.setBounds(0, 0, 602, 66);
        add(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBorder(new RoundedBorder(7, 3, Color.white, 1));
        nameLabel = new JLabel(gameRoom.getRoomName());
        nameLabel.setForeground(new Color(255, 255, 255));
        nameLabel.setBounds(13, 6, 286, 26);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        mainPanel.add(nameLabel);

        VerticalLineSeparator lineSeparator = new VerticalLineSeparator();
        lineSeparator.setBackground(new Color(255, 255, 255));
        lineSeparator.setBounds(525, 9, 3, 47);
        mainPanel.add(lineSeparator);
        
        LineSeparator lineSeparator2 = new LineSeparator();
        lineSeparator2.setBackground(new Color(255, 255, 255));
        lineSeparator2.setBounds(12, 34, 507, 3);
        mainPanel.add(lineSeparator2);
        
        roomStateLabel = new JLabel();
        roomStateLabel.setForeground(new Color(255, 255, 255));
        if(!gameRoom.isStart()) roomStateLabel.setText("Waiting");
        else roomStateLabel.setText("Gaming");
        roomStateLabel.setFont(new Font("Arial", Font.BOLD, 13));
        roomStateLabel.setBounds(535, 11, 61, 45);
        mainPanel.add(roomStateLabel);
        
        chairmanLabel = new JLabel("( creater:"+gameRoom.getChairmanId()+" )");
        chairmanLabel.setForeground(new Color(255, 255, 255));
        chairmanLabel.setBounds(357, 17, 165, 15);
        chairmanLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        mainPanel.add(chairmanLabel);

        mapSizeLabel = new JLabel("Map Size : "+gameRoom.getMapSize()+"x"+gameRoom.getMapSize());
        mapSizeLabel.setForeground(new Color(255, 255, 255));
        mapSizeLabel.setBounds(305, 43, 136, 15);
        mapSizeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(mapSizeLabel);

        userNumLabel = new JLabel("Player : "+gameRoom.getPlayerList().size()+"/"+gameRoom.getMaxPlayer());
        userNumLabel.setForeground(new Color(255, 255, 255));
        userNumLabel.setBounds(50, 43, 93, 15);
        userNumLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(userNumLabel);

        mineNumLabel = new JLabel("Mine : "+gameRoom.getMineNum());
        mineNumLabel.setForeground(new Color(255, 255, 255));
        mineNumLabel.setBounds(184, 43, 82, 15);
        mineNumLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mainPanel.add(mineNumLabel);

    }
    

    public void updatePanel(GameRoom gameRoom) {
        if (gameRoom.isStart()) roomStateLabel.setText("Gaming");
        else roomStateLabel.setText("Waiting");

        chairmanLabel.setText("( creater:"+gameRoom.getChairmanId()+" )");
        mapSizeLabel.setText("Map Size : "+gameRoom.getMapSize()+"x"+gameRoom.getMapSize());
        userNumLabel.setText("Player : "+gameRoom.getPlayerList().size()+"/"+gameRoom.getMaxPlayer());
        mineNumLabel.setText("Mine : "+gameRoom.getMineNum());

    }

    public long getRoomId(){
        return gameRoom.getId();
    }

    public GameRoom getGameRoom(){
        return gameRoom;
    }

}