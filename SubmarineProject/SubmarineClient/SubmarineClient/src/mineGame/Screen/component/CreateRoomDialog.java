package mineGame.Screen.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import mineGame.GameRoom;
import mineGame.Screen.MainScreen;
import mineGame.Screen.RoomScreen;
import mineGame.SubmarineClient;
import mineGame.User;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateRoomDialog extends JDialog {

	private JLabel mapSizeLabel;

	public CreateRoomDialog(User user) {
		

        
        
        
        // 컴포넌트 생성
        BackgroundPanel mainPanel = new BackgroundPanel("/mineGame/Screen/icon/createRoomBackground.png");
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        JPanel roomNamePanel = new JPanel();
        roomNamePanel.setBounds(30, 74, 324, 45);
        JLabel roomNameLabel = new JLabel("Room Name");
        roomNameLabel.setBounds(0, 6, 78, 34);
        roomNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        roomNameLabel.setForeground(new Color(255, 255, 255));
        JTextField roomNameField = new JTextField(10);
        roomNameField.setText("default");
        roomNameField.setFont(new Font("Book Antiqua", Font.BOLD, 13));
        roomNameField.setForeground(new Color(255, 255, 255));
        roomNameField.setOpaque(false);
        roomNameField.setBounds(103, 2, 211, 34);
        roomNameField.setBorder(null);
        
        roomNamePanel.setLayout(null);
        roomNamePanel.add(roomNameLabel);
        roomNamePanel.add(roomNameField);
        roomNamePanel.setOpaque(false);

        // 플레이어 수 패널
        JPanel playerCountPanel = new JPanel();
        playerCountPanel.setBounds(30, 129, 324, 45);
        JLabel playerCountLabel = new JLabel("Player");
        playerCountLabel.setBounds(0, 0, 41, 45);
        playerCountLabel.setFont(new Font("Arial", Font.BOLD, 13));
        playerCountLabel.setForeground(new Color(255, 255, 255));
        JSpinner playerCountSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 4, 1));
        playerCountSpinner.setFont(new Font("Arial", Font.BOLD, 14));
        playerCountSpinner.setBounds(103, 3, 56, 35);
        playerCountPanel.setLayout(null);
        playerCountPanel.add(playerCountLabel);
        playerCountPanel.add(playerCountSpinner);
        playerCountPanel.setOpaque(false);

        JPanel mapWidthPanel = new JPanel();
        mapWidthPanel.setBounds(30, 184, 324, 45);
        JLabel mapWidthLabel = new JLabel("Map Size");
        mapWidthLabel.setBounds(0, 0, 59, 45);
        mapWidthLabel.setFont(new Font("Arial", Font.BOLD, 13));
        mapWidthLabel.setForeground(new Color(255, 255, 255));
        JSpinner mapWidthSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 50, 1));
        mapWidthSpinner.setBounds(103, 2, 56, 35);
        mapWidthPanel.setLayout(null);
        mapWidthPanel.add(mapWidthLabel);
        mapWidthPanel.add(mapWidthSpinner);
        mapWidthPanel.setOpaque(false);


        JPanel mineCountPanel = new JPanel();
        mineCountPanel.setBounds(30, 239, 324, 45);
        JLabel mineCountLabel = new JLabel("Mine");
        mineCountLabel.setBounds(0, 0, 31, 45);
        mineCountLabel.setFont(new Font("Arial", Font.BOLD, 13));
        mineCountLabel.setForeground(new Color(255, 255, 255));
        
        JComboBox<Integer> mineCountComboBox = new JComboBox<>();
        mineCountComboBox.setBounds(103, 3, 56, 35);
        for (int i = 1; i < 100; i++) {
            mineCountComboBox.addItem(i);
        }
        mineCountComboBox.setSelectedItem(20); 
        
        mineCountPanel.setLayout(null);
        mineCountPanel.add(mineCountLabel);
        mineCountPanel.add(mineCountComboBox);
        mineCountPanel.setOpaque(false);
        
        

        mapSizeLabel = new JLabel("(10 x 10)");
        mapSizeLabel.setBounds(167, 1, 56, 45);
        mapWidthPanel.add(mapSizeLabel);
        mapSizeLabel.setForeground(new Color(255, 255, 255));
        mapSizeLabel.setFont(new Font("Arial", Font.BOLD, 13));
        

        // "맵 너비" 변경 시 "마인 수" 값 업데이트
        mapWidthSpinner.addChangeListener(event -> {
            int mapWidth = (int) mapWidthSpinner.getValue();
            int maxMines = mapWidth * mapWidth;
            mineCountComboBox.removeAllItems();
            for (int i = 1; i < maxMines; i++) {
                mineCountComboBox.addItem(i);
            }
            int midValue = maxMines / 2;
            mineCountComboBox.setSelectedItem(midValue);
            mapSizeLabel.setText("(" + mapWidth + "x" + mapWidth + ")");
        });
        mainPanel.setLayout(null);

        mainPanel.add(roomNamePanel);
        
                
                // 가로 줄 생성
                LineSeparator liseparator = new LineSeparator();
                liseparator.setBounds(102, 33, 216, 3);
                roomNamePanel.add(liseparator);
                liseparator.setBackground(new Color(255, 255, 255));

        mainPanel.add(playerCountPanel);


//        JCheckBox visibleCheckBox = new JCheckBox("",true);
//        visibleCheckBox.setBounds(277, 9, 26, 27);
//        playerCountPanel.add(visibleCheckBox);
//        visibleCheckBox.setOpaque(false);
//        JLabel visibleLabel = new JLabel("Visible");
//        visibleLabel.setBounds(231, 0, 43, 45);
//            visibleLabel.setFont(new Font("Arial", Font.BOLD, 13));
//            visibleLabel.setForeground(new Color(255, 255, 255));
//            playerCountPanel.add(visibleLabel);

        mainPanel.add(mapWidthPanel);
        
        // 맵 크기 라벨 업데이트
        mainPanel.add(mineCountPanel);

        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/roomSettingLabel.png"))).getImage().getScaledInstance(274, 60, Image.SCALE_SMOOTH)));
        lblNewLabel.setBounds(51, 4, 274, 60);
        mainPanel.add(lblNewLabel);
        
        mainPanel.add(lblNewLabel);
        
        
        JLabel createButton = new JLabel("");
        createButton.setBounds(79, 295, 234, 48);
        createButton.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/finalCreateRoom.png"))).getImage().getScaledInstance(234, 48, Image.SCALE_SMOOTH)));
        mainPanel.add(createButton);

            createButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                            String roomName = roomNameField.getText();
                            int playerCount = (int) playerCountSpinner.getValue();
                            boolean visible = true;
                            int mapWidth = (int) mapWidthSpinner.getValue();
                            int mineCount = (int) mineCountComboBox.getSelectedItem();
                            long roomId = System.currentTimeMillis();

                            // GameRoom 객체 생성 및 gameRoomListModel에 추가
                            GameRoom newRoom = new GameRoom(roomName, playerCount, visible, roomId, mapWidth, mineCount, user);
                            SubmarineClient.sendCommand("createRoom", newRoom);
                    }
            });



        setVisible(true);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
		
		
	}
}
