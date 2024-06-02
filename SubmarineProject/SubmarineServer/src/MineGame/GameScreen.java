package MineGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import MineGame.Component.InGameUserPanel;
import MineGame.listCallRenderer.InGameUserPanelListCellRenderer;
import room.User;

public class GameScreen extends JFrame {

	private LinkedHashMap<Long, InGameUserPanel> userInfoPanelMap;
    private ArrayList<JPanel> userInfoPanelList;

    private JPanel mineMapPanel;
    private JPanel mainPanel;
    private JPanel userPanel;
    private GameStart gameStart;
    private JLabel timerLabel;
    private JLabel remainMineLabel;
    private JLabel turnLabel;
    private ArrayList<JButton> mineButtonList;
    private HashMap<Long, JTable> userGameTableMap;
    private HashMap<Long, JLabel> turnUserMap;
    
    private JPanel gameInfoPanel;
    private JPanel mineNumPanel;

    private HashMap<Integer, Integer> mineHintMap;


    //////////////////////////////////////////////////////
    private JList<InGameUserPanel> panelList;
    private DefaultListModel<InGameUserPanel> panelListModel;
    //////////////////////////////////////////////////////





    public GameScreen(GameStart gs) {
        this.gameStart = gs;
        userGameTableMap=new HashMap<>();
        turnUserMap=new HashMap<>();
        mineHintMap = new HashMap<>();


        setTitle("Game Screen");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(927, 582);
        setLocationRelativeTo(null);

        // 메인 패널 설정
        // 3x3으로 나눈다.
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 각 유저의 정보 패널
        userInfoPanelMap = new LinkedHashMap<>();
        userInfoPanelList = new ArrayList<>();


        // 타이머, 잔여 마인 등 패널

        mineNumPanel = new JPanel();
        mineNumPanel.setLayout(null);
        mineNumPanel.setBounds(262, 15, 178, 66);
        mineNumPanel.setBackground(new Color(84, 84, 84));
        mainPanel.add(mineNumPanel);
        
        JLabel lblMine = new JLabel("", SwingConstants.CENTER);
        lblMine.setIcon(new ImageIcon(new ImageIcon(UserInfoPanel.class.getResource("/MineGame/icon/mineImg.png")).getImage().getScaledInstance(57, 42, Image.SCALE_SMOOTH)));
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
        userPanel = new JPanel();
        userPanel.setBounds(531, 10, 372, 515);
        userPanel.setBackground(new Color(84, 84, 84));
        userPanel.setLayout(null);
//        userPanel.setBorder(new RoundedBorder(15, 0, new Color(217, 214, 200), 3));

        
        JLabel lblNewLabel = new JLabel("User");
        lblNewLabel.setBounds(12, 10, 67, 31);
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        userPanel.add(lblNewLabel);


        panelListModel = new DefaultListModel<>();
        panelList = new JList<>(panelListModel);
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setBackground(new Color(84, 84, 84));
        panelList.setOpaque(false);
        panelList.setCellRenderer(new InGameUserPanelListCellRenderer());

        JScrollPane panelListScrollPane = new JScrollPane(panelList);
        panelListScrollPane.setBounds(12, 44, 322, 430);
        panelListScrollPane.setOpaque(false);
        panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        userPanel.add(panelListScrollPane);
        //////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("여기까지1");
        mainPanel.setLayout(null);
        mainPanel.add(userPanel);
        
        
        
        
        createTablePanel(gameStart.getGameUserList());

        // 10x10 버튼인 마인 맵이 들어갈 패널
        JPanel mineBackground = new JPanel();
        mineBackground.setBounds(64, 87, 376, 376);
        mineBackground.setLayout(null);
        mineMapPanel = createButtonGridPanel(gameStart.getMap());
        mineBackground.add(mineMapPanel);
        mainPanel.add(mineBackground); // 5



        //////////////////////////// bottom 패널 생성 //////////////////
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(74, 466, 366, 59);
        mainPanel.add(bottomPanel);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setLayout(null);
        bottomPanel.setOpaque(false);



        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);



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
    private JPanel createButtonGridPanel(Map map) {
        mineButtonList = new ArrayList<>();
        int rows = map.width,cols=map.width;

        JPanel panel = new JPanel(new GridLayout(rows, cols, 1, 1)); // 간격을 거의 없도록 설정
        panel.setOpaque(false);
        panel.setBounds(0, 0, 376, 376);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                JButton button = new JButton();

                if (map.mineMap[i][j] == 1) button.setText("O");
                else button.setText("X");

                button.setMargin(new Insets(0, 0, 0, 0)); // 버튼 여백 제거
                button.setFont(new Font("Arial", Font.BOLD, 9)); // 폰트 크기 줄이고 Bold로 설정
                button.setPreferredSize(new Dimension(30, 30)); // 버튼 크기 조정
                button.putClientProperty("id", (i * rows + j)); // 버튼에 고유 ID 설정
                panel.add(button);
                mineButtonList.add(button);
            }
        }
        return panel;
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
        for(JButton button : mineButtonList){
            int id = (Integer) button.getClientProperty("id"); // 설정된 ID 가져오기
            if (disableButton.contains(id)){
                button.setBackground(Color.gray);
            }
        }


        remainMineLabel.setText(""+(gameStart.getGameRoom().getMineNum()-gameStart.getMap().getFindMineList().size()));

    }


}
