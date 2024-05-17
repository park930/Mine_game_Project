package mineGame.Screen;

import mineGame.GameRoom;
import mineGame.ListCallRenderer.UserListCellRenderer;
import mineGame.SubmarineClient;
import mineGame.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class RoomScreen extends JFrame {
    private JList<User> userList;
    private DefaultListModel<User> userListModel;
    private MainScreen mainScreen;
    private GameRoom gameRoom;
    private User myUser;
    private ArrayList<User> users;

    public RoomScreen(MainScreen mainScreen,GameRoom gameRoom) {
        System.out.println(" 방 주인으로써 생성");
        this.mainScreen = mainScreen;
        this.gameRoom = gameRoom;
        users = new ArrayList<>();

        mainScreen.setVisible(false); // MainScreen 숨기기


        JPanel mainPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel centerNorthPanel = new JPanel();

        JToolBar topToolBar = new JToolBar();
        JToolBar BottomToolBar = new JToolBar();


        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setCellRenderer(new UserListCellRenderer()); // 사용자 정의 렌더러 설정

        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new Dimension(400, 300)); // Set desired width and height


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 창이 닫히기 전에 수행할 작업 작성
                SubmarineClient.sendCommand("deleteRoom",gameRoom);
                SubmarineClient.sendCommand("deleteClient",myUser.getId());
            }
        });

        //컴포넌트 생성
        JButton jb1 = new JButton("North");
        JButton jb2 = new JButton("South");


        //Center 구성
        initalToolBar(topToolBar);
        centerPanel.add(userScrollPane, BorderLayout.CENTER);

        //South 구성
        JButton startButton = new JButton("시작");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String roomName = roomNameField.getText();
//                int playerCount = (int) playerCountSpinner.getValue();
//                boolean visible = visibleCheckBox.isSelected();
                if (users.size() == gameRoom.getMaxPlayer()){
                    for(User u : users){
                        if (!u.isReady()){
                            // 플레이어가 다 준비하지 않았다고 알림 띄우기
                            System.out.println(" 플레이어 다 준비하지 않음");
                            showInfo("준비하지 않은 플레이어가 있습니다.");
                            return;
                        }

                    }

                    //모두 준비 완료하고, 플레이어 수가 다 채워진 경우, 게임 시작
                    System.out.println(" 모두 준비 완료");
                    
                    // 서버에게 게임 시작할거라고 메세지 보내기
                    SubmarineClient.sendCommand("startGame",gameRoom);
                    
//                    GameScreen gameScreen = new GameScreen();
//                    RoomScreen.this.setVisible(false); // MainScreen 숨기기

                } else {
                    // 플레이어가 다 채워지지 않았다고 알림
                    System.out.println(" 플레이어 부족");
                    showInfo("플레이어가 부족합니다.");
                }
                
                
            }
        });

        
        JButton exitButton = new JButton("나가기"); // 나가기 버튼 추가
        
        // 방장이 방을 나갔으므로, 서버에게 방을 삭제하겠다고 말해야함.
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // RoomScreen 닫기
                mainScreen.setVisible(true); // MainScreen 표시
                SubmarineClient.sendCommand("deleteRoom",gameRoom);
            }
        });

        BottomToolBar.add(startButton);
        BottomToolBar.add(exitButton);

        //컨테이너에 컴포넌트 추가
        mainPanel.setLayout(new BorderLayout(20,30));

        mainPanel.add(topToolBar, BorderLayout.NORTH);
        mainPanel.add(BottomToolBar, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        // 컨테이너를 프레임에 올림.
        add(mainPanel);

        setBounds(200,200,600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public RoomScreen(User user,MainScreen mainScreen,GameRoom gameRoom) {
        // 방장이 아닌 제 3자가 참여한 경우
        System.out.println("-------- 참가하는 방 화면 생성");
        this.mainScreen = mainScreen;
        this.gameRoom = gameRoom;
        System.out.println("   방 정보 : "+gameRoom);
        this.myUser = user;

        mainScreen.setVisible(false); // MainScreen 숨기기

        JPanel mainPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel centerNorthPanel = new JPanel();

        JToolBar topToolBar = new JToolBar();
        JToolBar BottomToolBar = new JToolBar();


        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setCellRenderer(new UserListCellRenderer()); // 사용자 정의 렌더러 설정


        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new Dimension(400, 300)); // Set desired width and height

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 창이 닫히기 전에 수행할 작업 작성
                SubmarineClient.sendRoomCommand("deleteRoomClient",gameRoom,myUser);
                SubmarineClient.sendCommand("deleteClient",myUser.getId());
            }
        });

        //컴포넌트 생성
        JButton jb1 = new JButton("North");
        JButton jb2 = new JButton("South");

        //Center 구성
        initalToolBar(topToolBar);
        centerPanel.add(userScrollPane, BorderLayout.CENTER);

        //South 구성
        JButton readyButton = new JButton("준비");
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 준비상태 flip함
                myUser.setReady(!myUser.isReady());
                
                //서버에게 해당 유저의 준비상태 메세지 보내야함
                SubmarineClient.sendRoomCommand("updateReadyState",gameRoom,myUser);
                
                // 화면 갱신

            }
        });

        JButton exitButton = new JButton("나가기"); // 나가기 버튼 추가
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // RoomScreen 닫기
                mainScreen.setVisible(true); // MainScreen 표시
                SubmarineClient.sendRoomCommand("deleteRoomClient",gameRoom,myUser);
            }
        });

        BottomToolBar.add(readyButton);
        BottomToolBar.add(exitButton);

        //컨테이너에 컴포넌트 추가
        mainPanel.setLayout(new BorderLayout(20,30));

        mainPanel.add(topToolBar, BorderLayout.NORTH);
        mainPanel.add(BottomToolBar, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        // 컨테이너를 프레임에 올림.
        add(mainPanel);

        setBounds(200,200,600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private void initalToolBar(JToolBar toolBar) {
        JButton createRoom = new JButton("방 설정");
        JButton statistics = new JButton("기타");
        toolBar.add(createRoom);
        toolBar.add(statistics);
    }

    public void setRoomUserList(ArrayList<User> users) {
        this.users = users;
        System.out.println("    화면의 방 참여자 목록 재설정");
        userListModel.clear();
        for(User user : users){
            userListModel.addElement(user);
        }
    }

    public void addUser(User myUser) {
        users.add(myUser);
        userListModel.addElement(myUser);
    }

    public void showInfo(String s) {
        JOptionPane.showMessageDialog(this, s, "알림", JOptionPane.INFORMATION_MESSAGE);
    }
}
