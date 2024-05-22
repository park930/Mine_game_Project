package mineGame.Listener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
import mineGame.User;
import mineGame.ListCallRenderer.PanelListCellRenderer;
import mineGame.Screen.MainScreen;
import mineGame.Screen.RoomScreen;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;

public class GameRoom2 extends JFrame {

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
    


	////////////////////////////////////////////
    private JList<UserPanel> panelList;
    private DefaultListModel<UserPanel> panelListModel;
	////////////////////////////////////////////
    
    
	
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
	public GameRoom2(RoomScreen roomScreen) {
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
        setSize(749, 773);
        setLocationRelativeTo(null);

        // 메인 패널 설정
        // 3x3으로 나눈다.
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 빈 패널
        gameInfoPanel = new JPanel();
        gameInfoPanel.setBounds(80, 28, 253, 79);
        JPanel emptyPanel3 = new JPanel();
        emptyPanel3.setBounds(10, 1018, 452, 504);
        JPanel emptyPanel4 = new JPanel();
        emptyPanel4.setBounds(462, 1018, 452, 504);

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


        
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        JPanel userPanel = new JPanel();
        userPanel.setBounds(442, 246, 212, 450);
        mainPanel.add(userPanel);
        userPanel.setBackground(new Color(104, 99, 74));
        userPanel.setLayout(null);
        userPanel.setBorder(new RoundedBorder(7, 0));
        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(12, 10, 97, 15);
        userPanel.add(lblNewLabel);

        panelListModel = new DefaultListModel<>();
        panelListModel.addElement(new UserPanel(new User()));

        panelList = new JList<>(panelListModel);
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setOpaque(false);
        panelList.setCellRenderer(new PanelListCellRenderer());
        
           JScrollPane panelListScrollPane = new JScrollPane(panelList);
           panelListScrollPane.setBounds(12, 44, 188, 396);
           panelListScrollPane.setOpaque(false);
           panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
           userPanel.add(panelListScrollPane);
           
           
        JPanel buttonPanel = new JPanel(new GridLayout(10, 10, 1, 1)); // 간격을 거의 없도록 설정
        buttonPanel.setBounds(10, 117, 420, 420);
        JButton button = new JButton();
        button.setMargin(new Insets(0, 0, 0, 0)); // 버튼 여백 제거
        button.setFont(new Font("Arial", Font.BOLD, 9)); // 폰트 크기 줄이고 Bold로 설정
        button.setPreferredSize(new Dimension(30, 30)); // 버튼 크기 조정
        buttonPanel.add(button);
        buttonPanel.add(button);
        buttonPanel.add(button);
        buttonPanel.add(button);
        buttonPanel.add(button);
//        for (int i = 0; i < 100; i++) {
//            JButton button = new JButton();
//            button.setMargin(new Insets(0, 0, 0, 0)); // 버튼 여백 제거
//            button.setFont(new Font("Arial", Font.BOLD, 9)); // 폰트 크기 줄이고 Bold로 설정
//            button.setPreferredSize(new Dimension(30, 30)); // 버튼 크기 조정
//            buttonPanel.add(button);
//        }

        
//        for(int i=0;i<4;i++) {
            JPanel panel = new JPanel();
            panel.setBounds(442, 117, 279, 119);
            panel.setLayout(null);
            JLabel nameLabel = new JLabel("name", SwingConstants.CENTER);
            JLabel userIdLabel = new JLabel("id:"+100, SwingConstants.CENTER);
            JLabel userRatingLabel = new JLabel(0+"전 "+1+"승 "+ 0+"패 ("+2.2+ "%)", SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            userRatingLabel.setFont(new Font("Arial", Font.BOLD, 14));
            userIdLabel.setFont(new Font("Arial", Font.BOLD, 10));

            JPanel userNamePanel = new JPanel();
            userNamePanel.setBounds(0, 0, 273, 52);
            userNamePanel.setLayout(new BorderLayout());
            userNamePanel.add(nameLabel, BorderLayout.NORTH);
            userNamePanel.add(userRatingLabel, BorderLayout.CENTER);
            userNamePanel.add(userIdLabel, BorderLayout.SOUTH);
            panel.add(userNamePanel);


            System.out.println(" 테이블 생성 1");
            String[] columnNames = {"선택 횟수", "찾은 지뢰","성공률"};
            Object[][] data = {
                    {1, 1,2.2+"%"}
            };

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            mainPanel.setLayout(null);
            JTable table = new JTable(model);

//            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(0, 52, 273, 43);
            scrollPane.setBorder(null);
            panel.add(scrollPane);


            turnLabel = new JLabel("", SwingConstants.CENTER);
            turnLabel.setBounds(0, 95, 273, 24);
            turnLabel.setText("Turn");
            turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
            panel.add(turnLabel);

            mainPanel.add(panel);
//		}

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        

        // 패널 추가
        mainPanel.add(gameInfoPanel);
        mainPanel.add(buttonPanel); // 5
        mainPanel.add(emptyPanel3); // 6
        mainPanel.add(emptyPanel4); // 8
        

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        
        
        
        
        
        
        //////////////////////////// bottom 패널 생성 //////////////////
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(10, 542, 420, 49);
        mainPanel.add(bottomPanel);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.setLayout(null);
        JButton giveupButton = new JButton("Give Up");
        giveupButton.setBounds(10, 10, 453, 23);
        bottomPanel.add(giveupButton);
        setVisible(true);
    }
	
	
	private void createTablePanel(ArrayList<User> users) {
		
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


}