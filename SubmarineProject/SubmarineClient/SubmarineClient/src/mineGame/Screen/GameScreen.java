package mineGame.Screen;

import mineGame.GameStart;
import mineGame.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameScreen extends JFrame {

    private ArrayList<JPanel> userInfoTableList;

    private JPanel mineMapPanel;
    private GameStart gameStart;
    private RoomScreen roomScreen;

    public GameScreen(GameStart gameStart,RoomScreen roomScreen) {
        this.gameStart = gameStart;
        this.roomScreen = roomScreen;

        roomScreen.setVisible(false);

        setTitle("Game Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        // 메인 패널 설정
        // 3x3으로 나눈다.
        JPanel mainPanel = new JPanel(new GridLayout(3, 3, 0, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 빈 패널
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        JPanel emptyPanel3 = new JPanel();
        JPanel emptyPanel4 = new JPanel();

        // JTable이 들어갈 패널
        userInfoTableList = new ArrayList<>();

        createTablePanel(gameStart.getGameUserList());

        // 10x10 버튼인 마인 맵이 들어갈 패널
        mineMapPanel = createButtonGridPanel(10, 10);

        // 패널 추가
        mainPanel.add(userInfoTableList.get(0)); // 1
        mainPanel.add(emptyPanel1); // 2
        mainPanel.add(userInfoTableList.get(1)); // 3
        mainPanel.add(emptyPanel2); // 4
        mainPanel.add(mineMapPanel); // 5
        mainPanel.add(emptyPanel3); // 6
        mainPanel.add(userInfoTableList.get(2)); // 7
        mainPanel.add(emptyPanel4); // 8
        mainPanel.add(userInfoTableList.get(3)); // 9

        add(mainPanel);
        setVisible(true);
    }

    // JTable이 들어갈 패널을 생성하는 메소드
    private void createTablePanel(ArrayList<User> users) {

        for (User user : users){
            JPanel panel = new JPanel(new BorderLayout());
            String[] columnNames = {"총 판수", "숭리", "패배","승률"};
            Object[][] data = {
                    {"10", "8", "2","80.0%"}
            };
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            panel.add(scrollPane, BorderLayout.CENTER);
            userInfoTableList.add(panel);
        }

        if (users.size()!=4){
            int now = users.size();
            while (now!=4){
                JPanel emptypanel = new JPanel(new BorderLayout());
                String[] columnNames = {"   ", "   ","   ","   "};
                Object[][] data = {
                        {"  ","  ","  ","  "}
                };
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                emptypanel.add(scrollPane, BorderLayout.CENTER);
                userInfoTableList.add(emptypanel);
                now++;
            }
        }

    }

    // 10x10 버튼 그리드를 생성하는 메소드
    private JPanel createButtonGridPanel(int rows, int cols) {
        JPanel panel = new JPanel(new GridLayout(rows, cols, 1, 1)); // 간격을 거의 없도록 설정
        for (int i = 0; i < rows * cols; i++) {
            JButton button = new JButton();
            panel.add(button);
        }
        return panel;
    }
}