package mineGame.Screen;

import mineGame.GameStart;
import mineGame.GameTimer;
import mineGame.SubmarineClient;
import mineGame.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen extends JFrame {

    private ArrayList<JPanel> userInfoTableList;

    private JPanel mineMapPanel;
    private GameStart gameStart;
    private RoomScreen roomScreen;
    private boolean myTurn=false;
    private boolean timerOn;
    private long userId;
    private JLabel timerLabel;
    private ArrayList<JButton> mineButtonList;

    public GameScreen(GameStart gameStart,RoomScreen roomScreen,long userId) {
        this.gameStart = gameStart;
        this.roomScreen = roomScreen;
        this.userId = userId;

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

        // 타이머 라벨 초기화 및 emptyPanel1에 추가
        timerLabel = new JLabel("Time remaining: 10", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emptyPanel1.setLayout(new BorderLayout());
        emptyPanel1.add(timerLabel, BorderLayout.CENTER);


        // JTable이 들어갈 패널
        userInfoTableList = new ArrayList<>();

        createTablePanel(gameStart.getGameUserList());

        // 10x10 버튼인 마인 맵이 들어갈 패널
        mineMapPanel = createButtonGridPanel(gameStart.getGameRoom().getMapSize(), gameStart.getGameRoom().getMapSize());

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

    // 마인 맵 버튼 그리드를 생성하는 메소드
    private JPanel createButtonGridPanel(int rows, int cols) {
        mineButtonList = new ArrayList<>();
        JPanel panel = new JPanel(new GridLayout(rows, cols, 1, 1)); // 간격을 거의 없도록 설정
        for (int i = 0; i < rows * cols; i++) {
            JButton button = new JButton();
            button.putClientProperty("id", i); // 버튼에 고유 ID 설정
            button.setMargin(new Insets(0, 0, 0, 0)); // 버튼 여백 제거
            button.setFont(new Font("Arial", Font.BOLD, 9)); // 폰트 크기 줄이고 Bold로 설정
            button.setPreferredSize(new Dimension(30, 30)); // 버튼 크기 조정

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton sourceButton = (JButton) e.getSource();
                    int choice = (Integer) sourceButton.getClientProperty("id"); // 설정된 ID 가져오기
                    System.out.println("버튼의 id = " + choice);
                    
                    if(timerOn && myTurn){
                        System.out.println("자신의 차례여서 타이머가 돌고 있는 것");
                        SubmarineClient.sendGameCommand("choiceButton",gameStart.getId(),choice,userId);
                        timerLabel.setText("");
                        timerOn=false;
                        myTurn=false;
                    } else {
                        System.out.println(" 내 차례 아닐 때, 버튼 누름");
                    }


                }
            });
            mineButtonList.add(button);
            panel.add(button);
        }
        return panel;
    }

    public void start() {

        myTurn = true;
        timerOn = true;

        // 비동기 작업 이후 타이머가 초과되었는지 확인
        Timer timer = new Timer(1000, new ActionListener() {
            private int timeRemaining = 10; // 초기 타이머 설정 시간과 동일하게 설정
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining > 0) {
                    timerLabel.setText("Time remaining: " + timeRemaining);
                    timeRemaining--;
                } else {
                    ((Timer) e.getSource()).stop();
                    // 타이머가 초과되었을 때 실행할 작업을 여기에 작성
                    myTurn = false;
                    timerOn = false;
                    timerLabel.setText("");
                    System.out.println("Timer has exceeded within start method!");
                    timeOutRandomChoice();
                }
            }
        });
        timer.start();
    }

    private void timeOutRandomChoice() {
        ArrayList<Integer> enableButton = gameStart.getMap().getEnableButton();

        if (enableButton == null || enableButton.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(enableButton.size()); // 0부터 list.size() - 1 사이의 무작위 인덱스 생성
        int choice = enableButton.get(randomIndex); // 무작위 요소 반환
        SubmarineClient.sendGameCommand("choiceButton",gameStart.getId(),choice,userId);
    }

    public void updateGameState(GameStart gameStart) {
        System.out.println("------게임 스크린 처리 과정 ------");
        this.gameStart = gameStart;

        // 선택한 버튼들에 대해서 결과를 화면에 표시
        ArrayList<Integer> disableButton = gameStart.getMap().getDisableButton();
        ArrayList<Integer> findMineList = gameStart.getMap().getFindMineList();

        for(JButton button : mineButtonList){
            int id = (Integer) button.getClientProperty("id"); // 설정된 ID 가져오기
            if (disableButton.contains(id)){
                if (findMineList.contains(id)){
                    button.setText("O");
                } else {
                    button.setText("X");
                }
//                button.setEnabled(false);
                button.setForeground(Color.red);
            }
        }


        // 유저들의 정보도 업데이트 ( 총 몇번  눌렀고, 각 유저는 몇개의 지뢰를 찾았는지, 누구의 차례인지)
    }
}