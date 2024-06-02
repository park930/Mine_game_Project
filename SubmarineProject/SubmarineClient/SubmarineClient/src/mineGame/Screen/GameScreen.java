package mineGame.Screen;

import mineGame.*;
import mineGame.ListCallRenderer.InGamePanelListCellRenderer;
import mineGame.ListCallRenderer.PanelListCellRenderer;
import mineGame.Screen.component.BackgroundPanel;
import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.RoundPanel;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class GameScreen extends JFrame {

    private LinkedHashMap<Long, InGameUserPanel> userInfoPanelMap;
    private ArrayList<JPanel> userInfoPanelList;

    private JPanel mineMapPanel;
    private BackgroundPanel mainPanel;
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
    
    private BackgroundPanel gameInfoPanel;
    private BackgroundPanel mineNumPanel;

    private HashMap<Integer, Integer> mineHintMap;


    //////////////////////////////////////////////////////
    private JList<InGameUserPanel> panelList;
    private DefaultListModel<InGameUserPanel> panelListModel;
    //////////////////////////////////////////////////////





    public GameScreen(GameStart gs,RoomScreen rs,long ui) {
        this.gameStart = gs;
        this.roomScreen = rs;
        this.userId = ui;
        userGameTableMap=new HashMap<>();
        turnUserMap=new HashMap<>();
        mineHintMap = new HashMap<>();

        roomScreen.setVisible(false);

        setTitle("Game Screen");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(927, 582);
        setLocationRelativeTo(null);

        // 메인 패널 설정
        // 3x3으로 나눈다.
        mainPanel = new BackgroundPanel("/mineGame/Screen/icon/inGameBackground.png");
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 각 유저의 정보 패널
        userInfoPanelMap = new LinkedHashMap<>();
        userInfoPanelList = new ArrayList<>();


        // 타이머, 잔여 마인 등 패널
        gameInfoPanel = new BackgroundPanel("/mineGame/Screen/icon/timerBackground.png");
        gameInfoPanel.setBounds(64, 15, 186, 66);
        gameInfoPanel.setOpaque(false);
        mainPanel.add(gameInfoPanel);
        
        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setIcon(null);
        timerLabel.setBounds(77, 13, 77, 42);
        timerLabel.setForeground(new Color(255, 255, 255));
        timerLabel.setFont(new Font("Arial", Font.BOLD, 34));
        gameInfoPanel.setLayout(null);
        gameInfoPanel.add(timerLabel);
        JLabel timerIcon = new JLabel("");
        timerIcon.setForeground(new Color(255, 255, 255));
        timerIcon.setIcon(new ImageIcon(new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/timer.png")).getImage().getScaledInstance(57, 42, Image.SCALE_SMOOTH)));
        timerIcon.setBounds(21, 11, 57, 42);
        gameInfoPanel.add(timerIcon);
        
        
        mineNumPanel = new BackgroundPanel("/mineGame/Screen/icon/timerBackground.png");
        mineNumPanel.setLayout(null);
        mineNumPanel.setOpaque(false);
        mineNumPanel.setBounds(262, 15, 178, 66);
        mainPanel.add(mineNumPanel);
        
        JLabel lblMine = new JLabel("", SwingConstants.CENTER);
        lblMine.setIcon(new ImageIcon(new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/mineImg.png")).getImage().getScaledInstance(57, 42, Image.SCALE_SMOOTH)));
        lblMine.setForeground(Color.WHITE);
        lblMine.setFont(new Font("Arial", Font.BOLD, 13));
        lblMine.setBounds(25, 11, 57, 42);
        mineNumPanel.add(lblMine);
       
        
        remainMineLabel = new JLabel("30", SwingConstants.CENTER);
        remainMineLabel.setForeground(Color.WHITE);
        remainMineLabel.setFont(new Font("Arial", Font.BOLD, 34));
        remainMineLabel.setBounds(85, 13, 62, 42);
        mineNumPanel.add(remainMineLabel);
       

        //////////////////////////////////////////////////////////////////////////////////////////
        userPanel = new RoundPanel(15,"/mineGame/Screen/icon/background.png");
        userPanel.setBounds(531, 10, 372, 515);
        userPanel.setBackground(new Color(104, 99, 74));
        userPanel.setLayout(null);
        userPanel.setBorder(new RoundedBorder(15, 0, new Color(217, 214, 200), 3));

        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/userLabelIcon.png"))).getImage().getScaledInstance(62, 23, Image.SCALE_SMOOTH)));
        lblNewLabel.setBounds(12, 10, 67, 31);
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        userPanel.add(lblNewLabel);


        panelListModel = new DefaultListModel<>();
        panelList = new JList<>(panelListModel);
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setBackground(new Color(104, 99, 74)); 
        panelList.setOpaque(false);
        panelList.setCellRenderer(new InGamePanelListCellRenderer());

        JScrollPane panelListScrollPane = new JScrollPane(panelList);
        panelListScrollPane.setBounds(12, 44, 322, 430);
        panelListScrollPane.setOpaque(false);
        panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        userPanel.add(panelListScrollPane);
        mainPanel.setLayout(null);
        mainPanel.add(userPanel);
        
        
        
        
        createTablePanel(gameStart.getGameUserList());

        // 10x10 버튼인 마인 맵이 들어갈 패널
        BackgroundPanel mineBackground = new BackgroundPanel("/mineGame/Screen/icon/minePanelBackground.png");
        mineBackground.setBounds(64, 87, 376, 376);
        mineBackground.setLayout(null);
        mineMapPanel = createButtonGridPanel(gameStart.getGameRoom().getMapSize(), gameStart.getGameRoom().getMapSize());
        mineBackground.add(mineMapPanel);
        mainPanel.add(mineBackground); // 5



        //////////////////////////// bottom 패널 생성 //////////////////
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(74, 466, 366, 59);
        mainPanel.add(bottomPanel);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setLayout(null);
        bottomPanel.setOpaque(false);
        
        JLabel giveupButton = new JLabel("");
        giveupButton.setBounds(93, 10, 165, 54);
        giveupButton.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/giveUpButton.png"))).getImage().getScaledInstance(165, 54, Image.SCALE_SMOOTH)));
        giveupButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int response = JOptionPane.showConfirmDialog(null, "정말 나가시겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    timerStop();
                    SubmarineClient.sendGameCommand("gameExitClient", gameStart.getId(), -1,userId);
                }
            }
        });
        bottomPanel.add(giveupButton);


        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 창이 닫히기 전에 수행할 작업 작성
                int response = JOptionPane.showConfirmDialog(null, "프로그램을 종료하겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    timerStop();
                    SubmarineClient.sendGameCommand("gameExitClient", gameStart.getId(), -1,userId);
                    SubmarineClient.sendCommand("deleteClient",userId);
                }
            }
        });


    }

    // JTable이 들어갈 패널을 생성하는 메소드
    private void createTablePanel(ArrayList<User> users) {
        for (User user : users){
            InGameUserPanel inGameUserPanel = new InGameUserPanel(user);
            inGameUserPanel.showTurnLabel(user.isTurn());
            userInfoPanelMap.put(user.getId(),inGameUserPanel);
            panelListModel.addElement(inGameUserPanel);
        }
    }

    // 마인 맵 버튼 그리드를 생성하는 메소드
    private JPanel createButtonGridPanel(int rows, int cols) {
        BufferedImage initialImage = loadImage("/mineGame/Screen/icon/mineButton_default.png");
        
        mineButtonList = new ArrayList<>();
        JPanel panel = new JPanel(new GridLayout(rows, cols, 1, 1)); // 간격을 거의 없도록 설정
        panel.setOpaque(false);
        panel.setBounds(0, 0, 376, 376);
        for (int i = 0; i < rows * cols; i++) {
            JButton button = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    BufferedImage buttonImage = (BufferedImage) getClientProperty("buttonImage");
                    if (buttonImage != null) {
                        // 버튼의 크기에 맞춰 이미지를 그립니다.
                        g.drawImage(buttonImage, 0, 0, getWidth(), getHeight(), this);
                    }

                    // 버튼이 비활성화되었을 때의 처리
//                    if (!isEnabled()) {
//                        g.setColor(new Color(0, 0, 0, 100)); // 반투명한 검정색
//                        g.fillRect(0, 0, getWidth(), getHeight());
//                    }
                }
            };
            button.putClientProperty("buttonImage", initialImage);
            button.putClientProperty("id", i); // 버튼에 고유 ID 설정
            button.setMargin(new Insets(0, 0, 0, 0)); // 버튼 여백 제거
            button.setFont(new Font("Arial", Font.BOLD, 9)); // 폰트 크기 줄이고 Bold로 설정
            button.setPreferredSize(new Dimension(30, 30)); // 버튼 크기 조정
            button.setBorder(null);
            button.setOpaque(false);
            button.setContentAreaFilled(false);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton sourceButton = (JButton) e.getSource();
                    int choice = (Integer) sourceButton.getClientProperty("id"); // 설정된 ID 가져오기
                    
                    if(timerOn && myTurn){
                        SubmarineClient.sendGameCommand("choiceButton",gameStart.getId(),choice,userId);
                        timerLabel.setText("");
                        timer.stop();
                        timerOn=false;
                        myTurn=false;
                    }


                }
            });
            mineButtonList.add(button);
            panel.add(button);
        }
        return panel;
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
                        timerLabel.setText(timeRemaining+"");
                        timeRemaining--;
                    } else {
                        ((Timer) e.getSource()).stop();
                        // 타이머가 초과되었을 때 실행할 작업을 여기에 작성
                        myTurn = false;
                        timerOn = false;
                        timerLabel.setText("");
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


    public void timerStop(){
        if (timer!=null) {
            timer.stop();
            timerOn = false;
        }
    }

    public void updateGameState(GameStart gameStart) {
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
        BufferedImage mineImage = loadImage("/mineGame/Screen/icon/mineButton_mine.png"); // 새로운 이미지 파일을 불러옵니다.
        mineHintMap = gameStart.getMap().getMineHintMap();

        for(JButton button : mineButtonList){
            int id = (Integer) button.getClientProperty("id"); // 설정된 ID 가져오기
            if (disableButton.contains(id)){
                if (findMineList.contains(id)){
                    button.putClientProperty("buttonImage", mineImage);
                    button.repaint(); // 버튼을 다시 그리도록 repaint()를 호출합니다.
                } else {
                    int surroundMineNum = mineHintMap.get(id);
                    String path = "/mineGame/Screen/icon/"+"hint"+surroundMineNum+".png";
                    button.putClientProperty("buttonImage", loadImage(path));
                    button.repaint(); // 버튼을 다시 그리도록 repaint()를 호출합니다.
                }
//                button.setForeground(Color.red);
                button.setEnabled(false);
            }
        }

        remainMineLabel.setText((gameStart.getGameRoom().getMineNum()-findMineList.size())+"");

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


    public GameStart getGameStart() {
        return gameStart;
    }
}