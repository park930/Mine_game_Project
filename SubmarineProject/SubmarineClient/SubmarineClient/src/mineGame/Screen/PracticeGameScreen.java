package mineGame.Screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import mineGame.*;
import mineGame.ListCallRenderer.InGamePanelListCellRenderer;
import mineGame.Screen.component.BackgroundPanel;
import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.RoundPanel;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;

public class PracticeGameScreen extends JFrame {

	private LinkedHashMap<Long, InGameUserPanel> userInfoPanelMap;
    private ArrayList<JPanel> userInfoPanelList;

    private JPanel mineMapPanel;
    private BackgroundPanel mainPanel;
    private RoundPanel userPanel;
    private boolean myTurn=false;
    private boolean timerOn;
    private User myUser;
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
    private PracticeMap practiceMap;
    private MainScreen mainScreen;


    //////////////////////////////////////////////////////
    private JList<InGameUserPanel> panelList;
    private DefaultListModel<InGameUserPanel> panelListModel;
    //////////////////////////////////////////////////////





    public PracticeGameScreen(User user, int size, int num,MainScreen ms) {
        this.myUser = user;
        userGameTableMap=new HashMap<>();
        turnUserMap=new HashMap<>();
        mineHintMap = new HashMap<>();
        this.mainScreen = ms;

        practiceMap = new PracticeMap(size,num);

        mainScreen.setVisible(false);
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
        //////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("여기까지1");
        mainPanel.setLayout(null);
        mainPanel.add(userPanel);
        
        
        
        
        createTablePanel(myUser);

        // 10x10 버튼인 마인 맵이 들어갈 패널
        BackgroundPanel mineBackground = new BackgroundPanel("/mineGame/Screen/icon/minePanelBackground.png");
        mineBackground.setBounds(64, 87, 376, 376);
        mineBackground.setLayout(null);
        mineMapPanel = createButtonGridPanel(practiceMap.getWidth());
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
                    // mainScreen의 메소드로 해당 practiceScreen 닫고, mainScreen을 다시 visible로
                    mainScreen.exitPractice();
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
                int response = JOptionPane.showConfirmDialog(null, "연습을 종료하겠습니까?", "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    timerStop();
                    // mainScreen의 메소드로 해당 practiceScreen 닫고, mainScreen을 다시 visible로
                    mainScreen.exitPractice();
                }
            }
        });


    }

    // JTable이 들어갈 패널을 생성하는 메소드
    private void createTablePanel(User user) {
        InGameUserPanel inGameUserPanel = new InGameUserPanel(user);
        inGameUserPanel.showTurnLabel(true);
        userInfoPanelMap.put(user.getId(),inGameUserPanel);
        panelListModel.addElement(inGameUserPanel);
    }

    // 마인 맵 버튼 그리드를 생성하는 메소드
    private JPanel createButtonGridPanel(int rows) {
        BufferedImage initialImage = loadImage("/mineGame/Screen/icon/mineButton_default.png");
        
        mineButtonList = new ArrayList<>();
        
        // 여기 수정 필요 rows를 매개변수로
        JPanel panel = new JPanel(new GridLayout(rows, rows, 1, 1)); // 간격을 거의 없도록 설정
        panel.setOpaque(false);
        panel.setBounds(0, 0, 376, 376);
        for (int i = 0; i < rows * rows; i++) {
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
                    System.out.println("버튼의 id = " + choice);
                    
                    if(timerOn && myTurn){
                        System.out.println("자신의 차례여서 타이머가 돌고 있는 것");
                        
                        // 자체적으로 마인 판단하는 로직 필요
                        timerLabel.setText("");
                        timer.stop();
                        timerOn=false;
                        myTurn=false;
                        choiceLogic(choice);

                    }


                }
            });
            mineButtonList.add(button);
            panel.add(button);
        }
        return panel;
    }

    private void choiceLogic(int choice) {
        ArrayList<Integer> enableButton = practiceMap.getEnableButton();
        ArrayList<Integer> findMineList = practiceMap.getFindMineList();

        if(practiceMap.checkMine(choice) > 0 ){
            findMineList.add(choice);
            myUser.setRight(myUser.getRight()+1);
        } else {
            practiceMap.putMineHint(choice);
        }
        myUser.setTotalChoice(myUser.getTotalChoice()+1);
        myUser.setFindRate( ((myUser.getRight()*1.0)/myUser.getTotalChoice())*100 );

        practiceMap.getDisableButton().add(choice);
        for(int i=0;i<enableButton.size();i++){
            if (enableButton.get(i) == choice) {
                enableButton.remove(i);
                break;
            }
        }
        updateGameState();
        if(findMineList.size() == practiceMap.getMineNum()){
            // 게임 종료
            showInfo("모든 지뢰를 찾았습니다!");
            timer.stop();
            mainScreen.exitPractice();
        } else {
            start();
        }
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
        ArrayList<Integer> enableButton = practiceMap.getEnableButton();

        if (enableButton == null || enableButton.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be null or empty");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(enableButton.size()); // 0부터 list.size() - 1 사이의 무작위 인덱스 생성
        int choice = enableButton.get(randomIndex); // 무작위 요소 반환
        
        // 해당 랜덤 선택값으로 마인 체크하고 화면에 표시하는 로직 필요
        choiceLogic(choice);

    }

    public void showInfo(String s) {
        JOptionPane.showMessageDialog(this, s, "알림", JOptionPane.INFORMATION_MESSAGE);
    }


    public void timerStop(){
        System.out.println("타이머 stop하려고 함");
        if (timer!=null) {
            timer.stop();
            timerOn = false;
        }
        System.out.println("타이머 종료 완료함");
    }

    public void updateGameState() {
        System.out.println("------게임 스크린 처리 과정 ------");

        // 선택한 버튼들에 대해서 결과를 화면에 표시
        ArrayList<Integer> disableButton = practiceMap.getDisableButton();
        ArrayList<Integer> findMineList = practiceMap.getFindMineList();
        BufferedImage mineImage = loadImage("/mineGame/Screen/icon/mineButton_mine.png");
        System.out.println("   확인 부분");
        mineHintMap = practiceMap.getMineHintMap();

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

        remainMineLabel.setText((practiceMap.getMineNum()-practiceMap.getFindMineList().size())+"");

        panelListModel.clear();
        panelListModel.addElement(new InGameUserPanel(myUser));

        // 유저들의 정보도 업데이트 ( 총 몇번  눌렀고, 각 유저는 몇개의 지뢰를 찾았는지, 누구의 차례인지)
//        JTable table = userGameTableMap.get(myUser.getId());
//        JLabel turnLabel = turnUserMap.get(myUser.getId());
//        if (table != null) {
//            DefaultTableModel model = (DefaultTableModel) table.getModel();
//            model.setValueAt(myUser.getTotalChoice(), 0, 0);
//            model.setValueAt(myUser.getRight(), 0, 1);
//            model.setValueAt(String.format("%.2f", myUser.getFindRate()) + "%", 0, 2);
//        }
//        if (turnLabel!= null) {
//            if (myUser.isTurn()) turnLabel.setText("Turn");
//            else turnLabel.setText("");
//        }



    }


}