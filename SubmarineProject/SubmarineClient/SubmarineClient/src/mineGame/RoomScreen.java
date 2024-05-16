package mineGame;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomScreen extends JFrame {
    private JList<User> userList;
    private DefaultListModel<User> userListModel;
    private MainScreen mainScreen;
    private int roomId;
    private GameRoom gameRoom;

    public RoomScreen(MainScreen mainScreen,GameRoom gameRoom) {
        this.mainScreen = mainScreen;
        this.roomId = roomId;
        this.gameRoom = gameRoom;

        System.out.println(roomId);

        mainScreen.setVisible(false); // MainScreen 숨기기


        JPanel mainPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel centerNorthPanel = new JPanel();

        JToolBar topToolBar = new JToolBar();
        JToolBar BottomToolBar = new JToolBar();


        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);


        //컴포넌트 생성
        JButton jb1 = new JButton("North");
        JButton jb2 = new JButton("South");


        //Center 구성
        initalToolBar(topToolBar);
        centerPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

        //South 구성
        JButton startButton = new JButton("시작");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String roomName = roomNameField.getText();
//                int playerCount = (int) playerCountSpinner.getValue();
//                boolean visible = visibleCheckBox.isSelected();

                GameScreen gameScreen = new GameScreen();
                RoomScreen.this.setVisible(false); // MainScreen 숨기기

            }
        });

        JButton exitButton = new JButton("나가기"); // 나가기 버튼 추가
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // RoomScreen 닫기
                mainScreen.setVisible(true); // MainScreen 표시
                SubmarineClient.sendCommand("deleteRoom",gameRoom);
            }
        });

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

}
