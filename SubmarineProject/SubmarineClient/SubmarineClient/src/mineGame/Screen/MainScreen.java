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
            System.out.println("111111111 방 더블 클릭 함");
            GameRoom selectedRoom = gameRoomList.getSelectedValue();
            if (selectedRoom != null) {
                System.out.println(selectedRoom);

                // 해당 클라이언트가 방에 들어갔다는 메세지 서버한테 보내야함
                SubmarineClient.sendJoinRoomCommand(selectedRoom,myUser);

                roomScreen = new RoomScreen(myUser,MainScreen.this, selectedRoom);
                roomScreen.setVisible(true);
                
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


    private class CreateRoomListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 새로운 창 생성
            JDialog createRoomDialog = new JDialog(MainScreen.this, "방 생성", true);
            createRoomDialog.setSize(300, 150);
            createRoomDialog.setLocationRelativeTo(null);
            createRoomDialog.setLayout(new BorderLayout());

            // 컴포넌트 생성
            JPanel centerPanel = new JPanel(new GridLayout(2, 2));
            JLabel roomNameLabel = new JLabel("방 이름:");
            JTextField roomNameField = new JTextField();
            JLabel playerCountLabel = new JLabel("플레이어 수:");
            JSpinner playerCountSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 4, 1));
            JCheckBox visibleCheckBox = new JCheckBox("Visible",true);

            centerPanel.add(roomNameLabel);
            centerPanel.add(roomNameField);
            centerPanel.add(playerCountLabel);
            centerPanel.add(playerCountSpinner);
            centerPanel.add(visibleCheckBox);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton createButton = new JButton("생성");
            JButton cancelButton = new JButton("취소");

            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String roomName = roomNameField.getText();
                    int playerCount = (int) playerCountSpinner.getValue();
                    boolean visible = visibleCheckBox.isSelected();
                    long roomId = System.currentTimeMillis();

                    // GameRoom 객체 생성 및 gameRoomListModel에 추가
                    GameRoom newRoom = new GameRoom(roomName, playerCount, visible,roomId,myUser);
                    if (visible) gameRoomListModel.addElement(newRoom);

                    createRoomDialog.dispose();
                    SubmarineClient.sendCommand("createRoom",newRoom);

                    roomScreen = new RoomScreen(MainScreen.this,newRoom);// 새로운 창 생성
                    roomScreen.setVisible(true);
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
