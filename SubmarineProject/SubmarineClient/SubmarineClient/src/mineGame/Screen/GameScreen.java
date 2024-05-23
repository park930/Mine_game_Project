package mineGame.Screen;

import mineGame.GameStart;
import mineGame.GameTimer;
import mineGame.ListCallRenderer.InGamePanelListCellRenderer;
import mineGame.ListCallRenderer.PanelListCellRenderer;
import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.RoundPanel;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;
import mineGame.SubmarineClient;
import mineGame.User;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class GameScreen extends JFrame {

    private LinkedHashMap<Long, InGameUserPanel> userInfoPanelMap;
    private ArrayList<JPanel> userInfoPanelList;

    private JPanel mineMapPanel;
    private JPanel mainPanel;
    private RoundPanel userPanel;
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




    //////////////////////////////////////////////////////
    private JList<InGameUserPanel> panelList;
    private DefaultListModel<InGameUserPanel> panelListModel;
    //////////////////////////////////////////////////////





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
        setSize(1200, 1200);
        setLocationRelativeTo(null);

        // 메인 패널 설정
        // 3x3으로 나눈다.
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 각 유저의 정보 패널
        userInfoPanelMap = new LinkedHashMap<>();
        userInfoPanelList = new ArrayList<>();


        // 타이머, 잔여 마인 등 패널
        gameInfoPanel = new JPanel();
        gameInfoPanel.setBounds(51, 10, 308, 63);
        mainPanel.add(gameInfoPanel);
       

        // 타이머 라벨 초기화 및 emptyPanel1에 추가
        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        remainMineLabel = new JLabel("remain Mine:"+gameStart.getGameRoom().getMineNum(), SwingConstants.CENTER);
        remainMineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gameInfoPanel.setLayout(new BorderLayout());
        gameInfoPanel.add(timerLabel, BorderLayout.CENTER);
        gameInfoPanel.add(remainMineLabel, BorderLayout.SOUTH);





        //////////////////////////////////////////////////////////////////////////////////////////
        userPanel = new RoundPanel(15);
        userPanel.setBounds(531, 10, 372, 515);
        userPanel.setBackground(new Color(104, 99, 74));
        userPanel.setLayout(null);
        userPanel.setBorder(new RoundedBorder(15, 0, new Color(217, 214, 200), 3));

        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(12, 10, 97, 15);
        userPanel.add(lblNewLabel);


        panelListModel = new DefaultListModel<>();
        panelList = new JList<>(panelListModel);
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setBackground(new Color(104, 99, 74)); 
        panelList.setOpaque(false);
        panelList.setCellRenderer(new InGamePanelListCellRenderer());

        JScrollPane panelListScrollPane = new JScrollPane(panelList);
        panelListScrollPane.setBounds(12, 44, 322, 449);
        panelListScrollPane.setOpaque(false);
        panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        userPanel.add(panelListScrollPane);
        //////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("여기까지1");
        mainPanel.setLayout(null);
        mainPanel.add(userPanel);
        
        
        
        
        createTablePanel(gameStart.getGameUserList());

        // 10x10 버튼인 마인 맵이 들어갈 패널
        mineMapPanel = createButtonGridPanel(gameStart.getGameRoom().getMapSize(), gameStart.getGameRoom().getMapSize());
        mainPanel.add(mineMapPanel); // 5



        //////////////////////////// bottom 패널 생성 //////////////////
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(76, 463, 280, 41);
        mainPanel.add(bottomPanel);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setLayout(null);
        JButton giveupButton = new JButton("Give Up");
        giveupButton.setBounds(10, 10, 260, 23);
        bottomPanel.add(giveupButton);
        
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
        System.out.println("여기까지2");
        for (User user : users){
            InGameUserPanel inGameUserPanel = new InGameUserPanel(user);
            System.out.println("여기까지3");
            inGameUserPanel.showTurnLabel(user.isTurn());
            System.out.println("여기까지4");
            userInfoPanelMap.put(user.getId(),inGameUserPanel);
            System.out.println("여기까지5");
            panelListModel.addElement(inGameUserPanel);
            System.out.println("여기까지6");
        }
        System.out.println("여기까지7");
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
        panel.setBounds(21, 79, 376, 376);
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

        // 유저 인원에 변동 적용
        panelListModel.clear();
        for(User user : gameStart.getGameUserList()){
            InGameUserPanel inGameUserPanel = new InGameUserPanel(user);
            inGameUserPanel.showTurnLabel(user.isTurn());

            userInfoPanelMap.put(user.getId(),inGameUserPanel);
            panelListModel.addElement(inGameUserPanel);
        }



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


}