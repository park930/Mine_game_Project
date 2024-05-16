import room.GameRoom;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.*;
public class MainScreen extends JFrame {
    private JList<SubmarineServer.Client> clientList;
    private DefaultListModel<SubmarineServer.Client> clientListModel;

    private JList<GameRecord> gameRecordList;

    private DefaultListModel<GameRecord> gameRecordListModel;

    private JList<GameRoom> gameRoomList;
    private DefaultListModel<GameRoom> gameRoomListModel;

    private ArrayList<GameRoom> roomList;

    public MainScreen() {
        JPanel mainPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel westPanel = new JPanel();
        clientListModel = new DefaultListModel<>();
        clientList = new JList<>(clientListModel);

        gameRecordListModel = new DefaultListModel<>();
        gameRecordList = new JList<>(gameRecordListModel);

        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);


        //컴포넌트 생성
        JButton jb1 = new JButton("North");
        JButton jb2 = new JButton("South");
//        JButton jb3 = new JButton("East");
        JButton jb4 = new JButton("West");
        JButton jb5 = new JButton("Center");

        centerPanel.add(new JScrollPane(gameRoomList), BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(gameRecordList), BorderLayout.CENTER);

        westPanel.add(new JScrollPane(clientList), BorderLayout.CENTER);

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
    }

    public void updateRoomList(ArrayList<GameRoom> gameRoom) {
        System.out.println("갱신 시작");
        gameRoomListModel.clear();
        for(GameRoom g : gameRoom) {
            gameRoomListModel.addElement(g);
        }
    }

    public void addClientList(SubmarineServer.Client c) {
        clientListModel.addElement(c);
    }
}
