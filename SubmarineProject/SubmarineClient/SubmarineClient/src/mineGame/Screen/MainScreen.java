package mineGame.Screen;

import mineGame.GameRoom;
import mineGame.Listener.DoubleClickListener;
import mineGame.SubmarineClient;
import mineGame.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainScreen extends JFrame{
    private JList<User> userList;
    private DefaultListModel<User> userListModel;
    private JList<GameRoom> gameRoomList;
    private DefaultListModel<GameRoom> gameRoomListModel;
    public RoomScreen roomScreen;
    private long userId;
    private User myUser;

    public MainScreen(User myUser) {
        System.out.println("내 정보 = "+myUser);
        System.out.println("main 생성");
        this.myUser = myUser;

        JPanel mainPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel centerNorthPanel = new JPanel();
        JPanel westPanel = new JPanel();

        JToolBar toolBar = new JToolBar();


        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);

        //게임 방 목록 세팅
        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        //접속 중인 유저 목록 세팅
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);


        //컴포넌트 생성
        JButton jb1 = new JButton("North");
        JButton jb2 = new JButton("South");
        
        
        //Center 구성
        initalToolBar(toolBar);
        centerPanel.add(toolBar, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(gameRoomList), BorderLayout.CENTER);

        
        //West 구성
        westPanel.add(new JScrollPane(userList), BorderLayout.CENTER);


        //컨테이너에 컴포넌트 추가
        mainPanel.setLayout(new BorderLayout(20,30));

        mainPanel.add(jb1, BorderLayout.NORTH);
        mainPanel.add(jb2, BorderLayout.SOUTH);
        mainPanel.add(westPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        // 컨테이너를 프레임에 올림.
        add(mainPanel);

        setBounds(200,200,600,500);
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

    private void initalToolBar(JToolBar toolBar) {
        JButton createRoom = new JButton("방 생성");
        createRoom.addActionListener(new CreateRoomListener());
        JButton noUserRoom = new JButton("컴퓨터 대결");
        JButton statistics = new JButton("통계");
        toolBar.add(createRoom);
        toolBar.add(noUserRoom);
        toolBar.add(statistics);
    }


    public void setRoomList(ArrayList<GameRoom> roomList)
    {
        gameRoomListModel.clear();
        for(GameRoom gameRoom : roomList){
            gameRoomListModel.addElement(gameRoom);
        }
    }

    public void setUserList(ArrayList<User> userList) {
        userListModel.clear();
        for(User user : userList){
            userListModel.addElement(user);
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

                    roomScreen = new RoomScreen(MainScreen.this,newRoom);// 새로운 창 생성
                    roomScreen.setVisible(true);
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
