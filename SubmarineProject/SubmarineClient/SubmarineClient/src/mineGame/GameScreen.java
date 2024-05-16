package mineGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JFrame {
    private JList<User> userList;
    private DefaultListModel<User> userListModel;

    public GameScreen() {
        JPanel mainPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel centerNorthPanel = new JPanel();

        JToolBar toolBar = new JToolBar();


        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);


        //컴포넌트 생성
        JButton jb1 = new JButton("North");
        JButton jb2 = new JButton("South");


        //Center 구성
        initalToolBar(toolBar);
        centerPanel.add(new JScrollPane(userList), BorderLayout.CENTER);

        //컨테이너에 컴포넌트 추가
        mainPanel.setLayout(new BorderLayout(20,30));

        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(jb2, BorderLayout.SOUTH);
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