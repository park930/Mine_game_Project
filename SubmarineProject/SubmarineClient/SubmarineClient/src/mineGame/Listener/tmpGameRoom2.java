package mineGame.Listener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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

import mineGame.GameRoom;
import mineGame.GameStart;
import mineGame.Map;
import mineGame.SubmarineClient;
import mineGame.User;
import mineGame.ListCallRenderer.InGamePanelListCellRenderer;
import mineGame.Screen.MainScreen;
import mineGame.Screen.RoomScreen;
import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.RoundedBorder;

public class tmpGameRoom2 extends JFrame {

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

    //////////////////////////////////////////////////////
    private JList<InGameUserPanel> panelList;
    private DefaultListModel<InGameUserPanel> panelListModel;
    //////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User user = new User();
					MainScreen mainScreen = new MainScreen(user);
					GameRoom gameRoom = new GameRoom("room1",4,true,10L,9,20,user);
					Map map = new Map(9,20);
					RoomScreen roomScreen = new RoomScreen(mainScreen, gameRoom, user);
					ArrayList<User> userList = new ArrayList<>();
					userList.add(user);
					GameStart gameStart = new GameStart(true, 10L, gameRoom, 1, map, userList);
					tmpGameRoom frame = new tmpGameRoom(roomScreen);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public tmpGameRoom2(RoomScreen roomScreen) {
		//////////////////////////
		User user = new User();
		MainScreen mainScreen = new MainScreen(user);
		GameRoom gameRoom = new GameRoom("room1",4,true,10L,9,20,user);
		Map map = new Map(9,20);
		roomScreen = new RoomScreen(mainScreen, gameRoom, user);
		ArrayList<User> userList = new ArrayList<>();
		userList.add(new User());
		userList.add(new User());
		userList.add(new User());
		userList.add(new User());
		GameStart gameStart = new GameStart(true, 10L, gameRoom, 1, map, userList);
		userId = user.getId();
		
		//////////////////////////
		
		
        this.gameStart = gameStart;
        this.roomScreen = roomScreen;
        this.userId = userId;
        userGameTableMap=new HashMap<>();
        turnUserMap=new HashMap<>();

        roomScreen.setVisible(false);

        setTitle("Game Screen");
        getContentPane().setLayout(new BorderLayout());
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




        //////////////////////////////////////////////////////////////////////////////////////////
        JPanel userPanel = new JPanel();
        userPanel.setBounds(12, 54, 212, 450);
        userPanel.setBackground(new Color(104, 99, 74));
        userPanel.setLayout(null);
        userPanel.setBorder(new RoundedBorder(7, 0));

        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(12, 10, 97, 15);
        userPanel.add(lblNewLabel);


        panelListModel = new DefaultListModel<>();
        panelList = new JList<>(panelListModel);
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setOpaque(false);
        panelList.setCellRenderer(new InGamePanelListCellRenderer());

        JScrollPane panelListScrollPane = new JScrollPane(panelList);
        panelListScrollPane.setBounds(12, 44, 322, 500);
        panelListScrollPane.setOpaque(false);
        panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        userPanel.add(panelListScrollPane);
 
        panelListModel.addElement(new InGameUserPanel(user));
        
        //////////////////////////////////////////////////////////////////////////////////////////


        // 패널 추가
        mainPanel.add(userPanel);
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
        

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
	
	
	private void createTablePanel(ArrayList<User> users) {
		for(int i=0;i<4;i++) {
            JPanel panel = new JPanel(new BorderLayout());

            JLabel nameLabel = new JLabel("name", SwingConstants.CENTER);
            JLabel userIdLabel = new JLabel("id:"+100, SwingConstants.CENTER);
            JLabel userRatingLabel = new JLabel(0+"전 "+1+"승 "+ 0+"패 ("+2.2+ "%)", SwingConstants.CENTER);
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
                    {1, 1,2.2+"%"}
            };

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);

//            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(null);
            panel.add(scrollPane, BorderLayout.CENTER);


            turnLabel = new JLabel("", SwingConstants.CENTER);
            turnLabel.setText("Turn");
            turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
            panel.add(turnLabel, BorderLayout.SOUTH);


            userInfoPanelMap.put(100L,panel);
            userInfoPanelList.add(panel);
            userGameTableMap.put(100L,table);
            turnUserMap.put(100L,turnLabel);
		}
		
		
		
		
		
//        for (User user : users){
//
//            System.out.println(user.getTotal()+" "+user.getWin()+" "+user.getLose()+" "+user.getRating());
//            System.out.println(user.getTotalChoice()+" "+user.getRight()+" "+user.getFindRate());
//
//
//            JPanel panel = new JPanel(new BorderLayout());
//
//            JLabel nameLabel = new JLabel(user.getUserName(), SwingConstants.CENTER);
//            JLabel userIdLabel = new JLabel("id:"+user.getId(), SwingConstants.CENTER);
//            JLabel userRatingLabel = new JLabel(user.getTotal()+"전 "+user.getWin()+"승 "+ user.getLose()+"패 ("+String.format("%.2f", user.getFindRate()) + "%)", SwingConstants.CENTER);
//            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
//            userRatingLabel.setFont(new Font("Arial", Font.BOLD, 14));
//            userIdLabel.setFont(new Font("Arial", Font.BOLD, 10));
//
//            JPanel userNamePanel = new JPanel();
//            userNamePanel.setLayout(new BorderLayout());
//            userNamePanel.add(nameLabel, BorderLayout.NORTH);
//            userNamePanel.add(userRatingLabel, BorderLayout.CENTER);
//            userNamePanel.add(userIdLabel, BorderLayout.SOUTH);
//            panel.add(userNamePanel, BorderLayout.NORTH);
//
//
//            System.out.println(" 테이블 생성 1");
//            String[] columnNames = {"선택 횟수", "찾은 지뢰","성공률"};
//            Object[][] data = {
//                    {user.getTotalChoice(), user.getRight(),String.format("%.2f",user.getFindRate())+"%"}
//            };
//
//            DefaultTableModel model = new DefaultTableModel(data, columnNames);
//            JTable table = new JTable(model);
//
////            JTable table = new JTable(data, columnNames);
//            JScrollPane scrollPane = new JScrollPane(table);
//            scrollPane.setBorder(null);
//            panel.add(scrollPane, BorderLayout.CENTER);
//
//
//            turnLabel = new JLabel("", SwingConstants.CENTER);
//            if (user.isTurn()) turnLabel.setText("Turn");
//            else turnLabel.setText("");
//            turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
//            panel.add(turnLabel, BorderLayout.SOUTH);
//
//
//            userInfoPanelMap.put(user.getId(),panel);
//            userInfoPanelList.add(panel);
//            userGameTableMap.put(user.getId(),table);
//            turnUserMap.put(user.getId(),turnLabel);
//        }
//
//        System.out.println("빈 칸 고려");
//        if (users.size()!=4){
//            int now = users.size();
//            while (now!=4){
//                userInfoPanelList.add(new JPanel(new BorderLayout()));
//                now++;
//            }
//        }

    }

    // 마인 맵 버튼 그리드를 생성하는 메소드
    private JPanel createButtonGridPanel(int rows, int cols) {
        mineButtonList = new ArrayList<>();
        JPanel panel = new JPanel(new GridLayout(rows, cols, 1, 1)); // 간격을 거의 없도록 설정
        for (int i = 0; i < rows * cols; i++) {
            JButton button = new JButton();
            button.setMargin(new Insets(0, 0, 0, 0)); // 버튼 여백 제거
            button.setFont(new Font("Arial", Font.BOLD, 9)); // 폰트 크기 줄이고 Bold로 설정
            button.setPreferredSize(new Dimension(30, 30)); // 버튼 크기 조정
            mineButtonList.add(button);
            panel.add(button);
        }
        return panel;
    }

}
