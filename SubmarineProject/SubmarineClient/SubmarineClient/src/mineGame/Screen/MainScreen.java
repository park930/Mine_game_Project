package mineGame.Screen;

import mineGame.GameRoom;
import mineGame.ListCallRenderer.RoomListCellRenderer;
import mineGame.Listener.DoubleClickListener;
import mineGame.Screen.component.*;
import mineGame.SubmarineClient;
import mineGame.User;
import mineGame.ListCallRenderer.PanelListCellRenderer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainScreen extends JFrame{
    private JList<GameRoomPanel> gameRoomList;
    private DefaultListModel<GameRoomPanel> gameRoomListModel;
    public RoomScreen roomScreen;
    private long userId;
    private User myUser;
	private JPanel menuPanel;

	////////////////////////////////////////////
	private JList<UserPanel> panelList;
    private DefaultListModel<UserPanel> panelListModel;
	////////////////////////////////////////////

	
	
    public MainScreen(User myUser) {
        System.out.println("내 정보 = "+myUser);
        System.out.println("main 생성");
        this.myUser = myUser;

        BackgroundPanel mainPanel = new BackgroundPanel("/mineGame/Screen/icon/backgroundIMG.png");
        RoundPanel centerPanel = new RoundPanel(1,"/mineGame/Screen/icon/background.png");
        centerPanel.setBackground(new Color(201, 197, 179));
        centerPanel.setBorder(new RoundedBorder(2, 0, new Color(217, 214, 200), 2));
        centerPanel.setBounds(241, 69, 631, 456);


        panelListModel = new DefaultListModel<>();
        panelList = new JList<>(panelListModel);
        panelList.setCellRenderer(new PanelListCellRenderer());

        

        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setBounds(12, 5, 535, 50);






        //게임 방 목록 세팅
        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameRoomList.setCellRenderer(new RoomListCellRenderer());
        //접속 중인 유저 목록 세팅
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setOpaque( false );

        
        

        centerPanel.setLayout(null);

        //Center 구성
        initialMenuPanel(menuPanel);
        centerPanel.add(menuPanel);
        
        JScrollPane scrollPane_1 = new JScrollPane(gameRoomList);
        scrollPane_1.setBounds(12, 53, 606, 206);
        centerPanel.add(scrollPane_1);
        mainPanel.setLayout(null);
        mainPanel.add(centerPanel);

        

        JPanel infoManagePanel = new JPanel();
        infoManagePanel.setBounds(251, 10, 558, 37);
        infoManagePanel.setOpaque(false);
        mainPanel.add(infoManagePanel);
        infoManagePanel.setLayout(null);
        
        JButton infoButton = new JButton("");
        infoButton.setBounds(485, 10, 64, 23);
        infoManagePanel.add(infoButton);
        
        JLabel lblNewLabel_1 = new JLabel("Mine");
        lblNewLabel_1.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/titleLogo.png"))).getImage().getScaledInstance(187, 42, Image.SCALE_SMOOTH)));
        lblNewLabel_1.setBounds(15, 7, 187, 42);
        mainPanel.add(lblNewLabel_1);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));

        RoundPanel userPanel = new RoundPanel(1,"/mineGame/Screen/icon/background.png");
        userPanel.setBounds(12, 69, 212, 456);
        mainPanel.add(userPanel);
        userPanel.setBackground(new Color(201, 197, 179));
        userPanel.setLayout(null);
        userPanel.setBorder(new RoundedBorder(2, 0, new Color(217, 214, 200), 2));
        
        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/userLabelIcon.png"))).getImage().getScaledInstance(62, 23, Image.SCALE_SMOOTH)));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(12, 5, 62, 23);
        userPanel.add(lblNewLabel);

        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setOpaque(false);
        panelList.setCellRenderer(new PanelListCellRenderer());

        JScrollPane panelListScrollPane = new JScrollPane(panelList);
        panelListScrollPane.setBounds(12, 34, 192, 412);
        panelListScrollPane.setOpaque(false);
        panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        userPanel.add(panelListScrollPane);
        mainPanel.add(centerPanel);
        
        
        // 컨테이너를 프레임에 올림.
        add(mainPanel);

        setBounds(200,200,901,579);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 창이 닫히기 전에 수행할 작업 작성
                SubmarineClient.sendCommand("deleteClient",myUser.getId());
            }
        });

        // 게임 방 목록 더블 클릭 이벤트 처리
        gameRoomList.addMouseListener(new DoubleClickListener(() -> {
            System.out.println("------- 방 더블 클릭 함");
            GameRoom selectedRoom = gameRoomList.getSelectedValue().getGameRoom();
            if (selectedRoom != null) {

                // 해당 클라이언트가 방에 들어가길 원한다는 메세지 서버한테 보내야함
                SubmarineClient.sendRoomCommand("joinRoom",selectedRoom,myUser);

                for(User u : selectedRoom.getPlayerList()){
                    System.out.println("  방 안의 플레이어 :"+u.getUserName());
                }
                System.out.println(selectedRoom);

//                roomScreen = new RoomScreen(myUser,MainScreen.this, selectedRoom);
//                System.out.println("    main화면에서 room에 대한 객체 설정");
//                roomScreen.setVisible(true);

                ///////////////roomScreen에서 방 참가자 목록 나오게 해야함

            }
        }));


    }

    private void initialMenuPanel(JPanel panel) {
        JLabel createRoom = new JLabel("");
        createRoom.setBounds(35, 4, 153, 41);
//        createRoom.addActionListener(new CreateRoomListener());
        
        createRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new CreateRoomListener().actionPerformed(null);
            }
        });
        
        
        createRoom.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/createRoom.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(createRoom);
        
        JLabel noUserRoom = new JLabel("");
        noUserRoom.setBounds(193, 4, 153, 41);
        noUserRoom.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/practice.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(noUserRoom);
        
        JLabel statistics = new JLabel("");
        statistics.setBounds(351, 4, 153, 41);
        statistics.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/statistics.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(statistics);
        
    }


    public void setRoomList(ArrayList<GameRoom> roomList)
    {
        gameRoomListModel.clear();
        for(GameRoom gameRoom : roomList){
            gameRoomListModel.addElement(new GameRoomPanel(gameRoom));
        }
    }

    public void setUserList(ArrayList<User> userList) {
        panelListModel.clear();
        for(User user : userList){
            System.out.println("    다시 유저 채워 넣음");
            panelListModel.addElement(new UserPanel(user));
        }
    }

    public void createJoinRoomScreen(GameRoom gameRoom) {
        System.out.println(" 방 생성하라는 명령 받음");
        roomScreen = new RoomScreen(myUser,MainScreen.this, gameRoom);
        System.out.println("    main화면에서 room에 대한 객체 설정");
        roomScreen.setVisible(true);
    }

    public void showInfo(String s) {
        JOptionPane.showMessageDialog(this, s, "알림", JOptionPane.INFORMATION_MESSAGE);
    }


    private class CreateRoomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 새로운 창 생성
            JDialog createRoomDialog = new JDialog(MainScreen.this, "방 생성", true);
            createRoomDialog.setSize(400, 300);
            createRoomDialog.setLocationRelativeTo(null);
            createRoomDialog.setLayout(new BorderLayout());

            // 컴포넌트 생성
            JPanel centerPanel = new JPanel(new GridLayout(5, 1));
            centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

            JPanel roomNamePanel = new JPanel(new BorderLayout());
            JLabel roomNameLabel = new JLabel("방 이름:");
            JTextField roomNameField = new JTextField(10);
            roomNamePanel.add(roomNameLabel, BorderLayout.WEST);
            roomNamePanel.add(roomNameField, BorderLayout.CENTER);

            // 플레이어 수 패널
            JPanel playerCountPanel = new JPanel(new BorderLayout());
            JLabel playerCountLabel = new JLabel("플레이어 수:");
            JSpinner playerCountSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 4, 1));
            playerCountPanel.add(playerCountLabel, BorderLayout.WEST);
            playerCountPanel.add(playerCountSpinner, BorderLayout.CENTER);

            JPanel visiblePanel = new JPanel(new BorderLayout());
            JLabel visibleLabel = new JLabel("공개 여부 :");
            JCheckBox visibleCheckBox = new JCheckBox("",true);
            visiblePanel.add(visibleLabel, BorderLayout.WEST);
            visiblePanel.add(visibleCheckBox,BorderLayout.CENTER);

            JPanel mapWidthPanel = new JPanel(new BorderLayout());
            JLabel mapWidthLabel = new JLabel("맵 크기:");
            JSpinner mapWidthSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 50, 1));
            JLabel mapSizeLabel = new JLabel("(10 x 10)");
            mapWidthPanel.add(mapWidthLabel, BorderLayout.WEST);
            mapWidthPanel.add(mapWidthSpinner, BorderLayout.CENTER);
            mapWidthPanel.add(mapSizeLabel, BorderLayout.EAST);


            JPanel mineCountPanel = new JPanel(new BorderLayout());
            JLabel mineCountLabel = new JLabel("마인 수:");
            JComboBox<Integer> mineCountComboBox = new JComboBox<>();
            mineCountComboBox.addItem(20); // 기본 값 20 설정
            mineCountPanel.add(mineCountLabel, BorderLayout.WEST);
            mineCountPanel.add(mineCountComboBox, BorderLayout.CENTER);

            // 초기 마인 수 설정
            mineCountComboBox.addItem(20);

            // "맵 너비" 변경 시 "마인 수" 값 업데이트
            mapWidthSpinner.addChangeListener(event -> {
                int mapWidth = (int) mapWidthSpinner.getValue();
                int maxMines = mapWidth * mapWidth - 1;
                mineCountComboBox.removeAllItems();
                for (int i = 1; i < maxMines; i++) {
                    mineCountComboBox.addItem(i);
                }
                int midValue = maxMines / 2;
                mineCountComboBox.setSelectedItem(midValue);

                // 맵 크기 라벨 업데이트
                mapSizeLabel.setText("(" + mapWidth + "x" + mapWidth + ")");
            });

            centerPanel.add(roomNamePanel);
            centerPanel.add(playerCountPanel);
            centerPanel.add(visiblePanel);
            centerPanel.add(mapWidthPanel);
            centerPanel.add(mineCountPanel);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton createButton = new JButton("생성");
            JButton cancelButton = new JButton("취소");

            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String roomName = roomNameField.getText();
                    int playerCount = (int) playerCountSpinner.getValue();
                    boolean visible = visibleCheckBox.isSelected();
                    int mapWidth = (int) mapWidthSpinner.getValue();
                    int mineCount = (int) mineCountComboBox.getSelectedItem();
                    long roomId = System.currentTimeMillis();

                    // GameRoom 객체 생성 및 gameRoomListModel에 추가
                    GameRoom newRoom = new GameRoom(roomName, playerCount, visible,roomId,mapWidth,mineCount,myUser);
                    if (visible) gameRoomListModel.addElement(new GameRoomPanel(newRoom));

                    createRoomDialog.dispose();
                    SubmarineClient.sendCommand("createRoom",newRoom);

                    roomScreen = new RoomScreen(MainScreen.this,newRoom,myUser);// 새로운 창 생성
                    roomScreen.setVisible(true);
                    
                    // 방 생성자 준비상태 완료로 전환
                    System.out.println(" 준비상태 true로 전환");
                    myUser.setReady(true);
                    roomScreen.addUser(myUser);
                }
            });

            buttonPanel.add(createButton);
            buttonPanel.add(cancelButton);

            createRoomDialog.add(centerPanel, BorderLayout.CENTER);
            createRoomDialog.add(buttonPanel, BorderLayout.SOUTH);

            createRoomDialog.setVisible(true);

        }
    }

}
