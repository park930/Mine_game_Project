package mineGame.Screen.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mineGame.GameRecord;
import mineGame.GameRoom;
import javax.swing.SwingConstants;

public class GameRecordPanel extends JPanel {
    private ImageIcon originalIcon;
    private Image image;
    private JLabel findRateLabel;
    private JLabel totalChoiceLabel;
    private JLabel findMineLabel;
    private JLabel isWinLabel;
    private GameRoom gameRoom;
    
    //////////////////////////////////////////////////////
    private GameRecord gameRecord;
    private JLabel mapSizeLabel;
    private JLabel mineNumLabel;

    public GameRecordPanel(GameRecord gameRecord) {
        System.out.println("새로운 room 생성 : "+gameRoom);
        this.gameRecord = gameRecord;

    	setBackground(new Color(23, 30, 43));
        setLayout(null);
        originalIcon = new ImageIcon(InGameUserPanel.class.getResource("/mineGame/Screen/icon/person.png"));
        image = originalIcon.getImage().getScaledInstance(39, 39, Image.SCALE_SMOOTH);
       
        
        setOpaque(false);  // Ensure background is transparent
        
     // Set preferred size to ensure it is displayed correctly in JList
        setPreferredSize(new Dimension(429, 65));
        
        BackgroundPanel mainPanel = new BackgroundPanel("/mineGame/Screen/icon/roomListBackground.png");
        mainPanel.setBackground(new Color(221, 211, 187));
        mainPanel.setBounds(0, 0, 429, 66);
        add(mainPanel);
        mainPanel.setLayout(null);
        mainPanel.setBorder(new RoundedBorder(7, 3, Color.white, 1));
        
        LineSeparator lineSeparator2 = new LineSeparator();
        lineSeparator2.setBackground(new Color(255, 255, 255));
        lineSeparator2.setBounds(93, 38, 326, 3);
        mainPanel.add(lineSeparator2);
        
        isWinLabel = new JLabel();
        isWinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        isWinLabel.setText("Win");
        if(gameRecord.isWin()) {
        	isWinLabel.setText("Win");
            isWinLabel.setForeground(new Color(128, 163, 255));
        } else {
        	isWinLabel.setText("lose");
            isWinLabel.setForeground(new Color(255, 108, 112));
        }
        isWinLabel.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 23));
        isWinLabel.setBounds(9, 11, 75, 45);
        mainPanel.add(isWinLabel);

        findRateLabel = new JLabel("("+String.format("%.2f", gameRecord.getFindRate())+"%)");
        findRateLabel.setForeground(new Color(255, 255, 255));
        findRateLabel.setBounds(335, 10, 89, 22);
        findRateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(findRateLabel);

        totalChoiceLabel = new JLabel("Trial : "+gameRecord.getChoiceTotal());
        totalChoiceLabel.setForeground(new Color(255, 255, 255));
        totalChoiceLabel.setBounds(95, 10, 93, 22);
        totalChoiceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(totalChoiceLabel);

        findMineLabel = new JLabel("Find Mine : "+gameRecord.getFindMine());
        findMineLabel.setForeground(new Color(255, 255, 255));
        findMineLabel.setBounds(193, 10, 133, 22);
        findMineLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(findMineLabel);
        
        mapSizeLabel = new JLabel("Map : "+gameRecord.getMapSize()+"x"+gameRecord.getMapSize());
        mapSizeLabel.setForeground(Color.WHITE);
        mapSizeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mapSizeLabel.setBounds(98, 45, 82, 15);
        mainPanel.add(mapSizeLabel);
        
        mineNumLabel = new JLabel("Mine : "+gameRecord.getFindMine());
        mineNumLabel.setForeground(Color.WHITE);
        mineNumLabel.setFont(new Font("Arial", Font.BOLD, 12));
        mineNumLabel.setBounds(195, 45, 82, 15);
        mainPanel.add(mineNumLabel);

    }
    



}
