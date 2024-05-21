package mineGame.Screen;

import mineGame.GameRoom;
import mineGame.Listener.DoubleClickListener;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;
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
    private JList<GameRoom> gameRoomList;
    private DefaultListModel<GameRoom> gameRoomListModel;
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

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(201, 197, 179));
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setBounds(250, 46, 559, 472);
        JPanel westPanel = new JPanel();
        westPanel.setBounds(0, 0, 238, 528);


        panelListModel = new DefaultListModel<>();


        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setBounds(12, 68, 535, 33);






        //게임 방 목록 세팅
        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        //접속 중인 유저 목록 세팅
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setOpaque( false );

        
        

        centerPanel.setLayout(null);

        //Center 구성
        initialMenuPanel(menuPanel);
        centerPanel.add(menuPanel);
        JScrollPane scrollPane_1 = new JScrollPane(gameRoomList);
        scrollPane_1.setBounds(12, 111, 535, 173);
        centerPanel.add(scrollPane_1);
        mainPanel.setLayout(null);
        westPanel.setLayout(null);

        
        

        mainPanel.add(westPanel);

        
        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_1.setBounds(12, 10, 160, 34);
        westPanel.add(lblNewLabel_1);

        JPanel userPanel = new JPanel();
        userPanel.setBounds(12, 68, 212, 450);
        westPanel.add(userPanel);
        userPanel.setLayout(null);
        userPanel.setOpaque(false);
        userPanel.setBorder(new RoundedBorder(7, 0));
        
        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(12, 10, 97, 15);
        userPanel.add(lblNewLabel);

        panelList = new JList<>(panelListModel);
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setOpaque(false);
        panelList.setCellRenderer(new PanelListCellRenderer());

        JScrollPane panelListScrollPane = new JScrollPane(panelList);
        panelListScrollPane.setBounds(12, 44, 188, 396);
        panelListScrollPane.setOpaque(false);
        panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        userPanel.add(panelListScrollPane);
        mainPanel.add(centerPanel);
        
        
        // 컨테이너를 프레임에 올림.
        add(mainPanel);

        JPanel panel = new JPanel();
        panel.setBounds(251, 10, 558, 37);
        panel.setOpaque(false);
        mainPanel.add(panel);
        panel.setLayout(null);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setBounds(485, 10, 64, 23);
        panel.add(btnNewButton);

        setBounds(200,200,837,567);
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
            GameRoom selectedRoom = gameRoomList.getSelectedValue();
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
        JButton createRoom = new JButton("방 생성");
        createRoom.addActionListener(new CreateRoomListener());
        createRoom.setBounds(0, 0, 167, 33);
        JButton noUserRoom = new JButton("컴퓨터 대결");
        noUserRoom.setBounds(189, 0, 167, 33);
        JButton statistics = new JButton("통계");
        statistics.setBounds(368, 0, 167, 33);
        menuPanel.setLayout(null);
        panel.add(createRoom);
        panel.add(noUserRoom);
        panel.add(statistics);
    }


    public void setRoomList(ArrayList<GameRoom> roomList)
    {
        gameRoomListModel.clear();
        for(GameRoom gameRoom : roomList){
            gameRoomListModel.addElement(gameRoom);
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
                    if (visible) gameRoomListModel.addElement(newRoom);

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
