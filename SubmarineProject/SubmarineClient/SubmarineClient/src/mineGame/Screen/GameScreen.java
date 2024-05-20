package mineGame.Screen;

import mineGame.GameStart;
import mineGame.GameTimer;
import mineGame.SubmarineClient;
import mineGame.User;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GameScreen extends JFrame {

    private LinkedHashMap<Long, JPanel> userInfoPanelMap;
    private ArrayList<JPanel> userInfoPanelList;

    private JPanel mineMapPanel;
    private JPanel mainPanel;
    private GameStart gameStart;
    private RoomScreen roomScreen;
    private boolean myTurn=false;
    private boolean timerOn;
    private long userId;
    private JLabel timerLabel;
    private JLabel remainMineLabel;
    private JLabel turnLabel;
    private ArrayList<JButton> mineButtonList;
    private HashMap<Long, JTable> userGameTableMap;
    private HashMap<Long, JLabel> turnUserMap;
    private Timer timer;
    private JPanel gameInfoPanel;


    public GameScreen(GameStart gameStart,RoomScreen roomScreen,long userId) {
        this.gameStart = gameStart;
        this.roomScreen = roomScreen;
        this.userId = userId;
        userGameTableMap=new HashMap<>();
        turnUserMap=new HashMap<>();

        roomScreen.setVisible(false);

        setTitle("Game Screen");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        // 메인 패널 설정
        // 3x3으로 나눈다.
        mainPanel = new JPanel(new GridLayout(3, 3, 0, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 빈 패널
        gameInfoPanel = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        JPanel emptyPanel3 = new JPanel();
        JPanel emptyPanel4 = new JPanel();

        // 타이머 라벨 초기화 및 emptyPanel1에 추가
        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        remainMineLabel = new JLabel("remain Mine:"+gameStart.getGameRoom().getMineNum(), SwingConstants.CENTER);
        remainMineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gameInfoPanel.setLayout(new BorderLayout());
        gameInfoPanel.add(timerLabel, BorderLayout.CENTER);
        gameInfoPanel.add(remainMineLabel, BorderLayout.SOUTH);


        // 각 유저의 정보 패널
        userInfoPanelMap = new LinkedHashMap<>();
        userInfoPanelList = new ArrayList<>();
        createTablePanel(gameStart.getGameUserList());

        // 10x10 버튼인 마인 맵이 들어갈 패널
        mineMapPanel = createButtonGridPanel(gameStart.getGameRoom().getMapSize(), gameStart.getGameRoom().getMapSize());

        // 패널 추가
        mainPanel.add(userInfoPanelList.get(0)); // 1
        mainPanel.add(gameInfoPanel); // 2
        mainPanel.add(userInfoPanelList.get(1)); // 3
        mainPanel.add(emptyPanel2); // 4
        mainPanel.add(mineMapPanel); // 5
        mainPanel.add(emptyPanel3); // 6
        mainPanel.add(userInfoPanelList.get(2)); // 7
        mainPanel.add(emptyPanel4); // 8
        mainPanel.add(userInfoPanelList.get(3)); // 9



        //////////////////////////// bottom 패널 생성 //////////////////
        JPanel bottomPanel = new JPanel(new GridLayout(3, 0, 0, 0));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton giveupButton = new JButton("Give Up");
        bottomPanel.add(giveupButton,BorderLayout.NORTH);
        
        // 항복 버튼 구현
        // 항복 버튼 구현
        giveupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "정말 나가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    SubmarineClient.sendGameCommand("gameExitClient", gameStart.getId(), -1,userId);
                }
            }
        });


        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    // JTable이 들어갈 패널을 생성하는 메소드
    private void createTablePanel(ArrayList<User> users) {

        for (User user : users){

            System.out.println(user.getTotal()+" "+user.getWin()+" "+user.getLose()+" "+user.getRating());
            System.out.println(user.getTotalChoice()+" "+user.getRight()+" "+user.getFindRate());


            JPanel panel = new JPanel(new BorderLayout());

            JLabel nameLabel = new JLabel(user.getUserName(), SwingConstants.CENTER);
            JLabel userIdLabel = new JLabel("id:"+user.getId(), SwingConstants.CENTER);
            JLabel userRatingLabel = new JLabel(user.getTotal()+"전 "+user.getWin()+"승 "+ user.getLose()+"패 ("+String.format("%.2f", user.getFindRate()) + "%)", SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            userRatingLabel.setFont(new Font("Arial", Font.BOLD, 14));
            userIdLabel.setFont(new Font("Arial", Font.BOLD, 10));

            JPanel userNamePanel = new JPanel();
            userNamePanel.setLayout(new BorderLayout());
            userNamePanel.add(nameLabel, BorderLayout.NORTH);
            userNamePanel.add(userRatingLabel, BorderLayout.CENTER);
            userNamePanel.add(userIdLabel, BorderLayout.SOUTH);
            panel.add(userNamePanel, BorderLayout.NORTH);


            System.out.println(" 테이블 생성 1");
            String[] columnNames = {"선택 횟수", "찾은 지뢰","성공률"};
            Object[][] data = {
                    {user.getTotalChoice(), user.getRight(),String.format("%.2f",user.getFindRate())+"%"}
            };

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);

//            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(null);
            panel.add(scrollPane, BorderLayout.CENTER);


            turnLabel = new JLabel("", SwingConstants.CENTER);
            if (user.isTurn()) turnLabel.setText("Turn");
            else turnLabel.setText("");
            turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
            panel.add(turnLabel, BorderLayout.SOUTH);


            userInfoPanelMap.put(user.getId(),panel);
            userInfoPanelList.add(panel);
            userGameTableMap.put(user.getId(),table);
            turnUserMap.put(user.getId(),turnLabel);
        }

        System.out.println("빈 칸 고려");
        if (users.size()!=4){
            int now = users.size();
            while (now!=4){
                userInfoPanelList.add(new JPanel(new BorderLayout()));
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
                        timer.stop();
                        System.out.println("서버에 누른 버튼 정보 전달 완료-------");
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
        timer = new Timer(1000, new ActionListener() {
            private int timeRemaining = 10; // 초기 타이머 설정 시간과 동일하게 설정
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timerOn) {
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
                } else {
                    ((Timer) e.getSource()).stop();
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

    public void showInfo(String s) {
        JOptionPane.showMessageDialog(this, s, "알림", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateGameState(GameStart gameStart) {
        System.out.println("------게임 스크린 처리 과정 ------");
        this.gameStart = gameStart;

        // 유저 인원에 변동있는지 확인
        ArrayList<Long> tmp = new ArrayList<>();
        for(Map.Entry<Long,JPanel> entry : userInfoPanelMap.entrySet()){
            if (!validUser(gameStart,entry.getKey())){

                tmp.add(entry.getKey());
            }
        }
        for(long id : tmp){
            System.out.println(" 제거하려는 유저 : "+id);
            JPanel targetPanel = userInfoPanelMap.get(id);
            userInfoPanelMap.remove(id);
            userInfoPanelList.remove(targetPanel);
            userInfoPanelList.add(new JPanel(new BorderLayout()));
        }

        mainPanel.removeAll(); // 기존 패널 모두 제거
        mainPanel.add(userInfoPanelList.get(0)); // 1
        mainPanel.add(gameInfoPanel); // 2
        mainPanel.add(userInfoPanelList.get(1)); // 3
        mainPanel.add(new JPanel(new BorderLayout())); // 4
        mainPanel.add(mineMapPanel); // 5
        mainPanel.add(new JPanel(new BorderLayout())); // 6
        mainPanel.add(userInfoPanelList.get(2)); // 7
        mainPanel.add(new JPanel(new BorderLayout())); // 8
        mainPanel.add(userInfoPanelList.get(3)); // 9


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
                button.setForeground(Color.red);
                button.setEnabled(false);
            }
        }

        remainMineLabel.setText("remain Mine:"+(gameStart.getGameRoom().getMineNum()-findMineList.size()));

        // 유저들의 정보도 업데이트 ( 총 몇번  눌렀고, 각 유저는 몇개의 지뢰를 찾았는지, 누구의 차례인지)
        for (User u : gameStart.getGameUserList()) {
            JTable table = userGameTableMap.get(u.getId());
            JLabel turnLabel = turnUserMap.get(u.getId());
            if (table != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setValueAt(u.getTotalChoice(), 0, 0);
                model.setValueAt(u.getRight(), 0, 1);
                model.setValueAt(String.format("%.2f", u.getFindRate()) + "%", 0, 2);
            }
            if (turnLabel!= null) {
                if (u.isTurn()) turnLabel.setText("Turn");
                else turnLabel.setText("");
            }
        }


    }

    private boolean validUser(GameStart gameStart, Long key) {
        for(User u : gameStart.getGameUserList()){
            if (u.getId() == key) return true;
        }
        return false;
    }

}