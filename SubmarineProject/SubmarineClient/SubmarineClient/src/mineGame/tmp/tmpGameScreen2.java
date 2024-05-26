package mineGame.tmp;

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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;
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
import mineGame.Screen.component.BackgroundPanel;
import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;

import javax.swing.ImageIcon;

public class tmpGameScreen2 extends JFrame {

	private LinkedHashMap<Long, JPanel> userInfoPanelMap;
    private ArrayList<JPanel> userInfoPanelList;

    private JPanel mineMapPanel;
    private BackgroundPanel mainPanel;
    private boolean myTurn=false;
    private boolean timerOn;
    private long userId;
    private JLabel timerLabel;
    private JLabel turnLabel;
    private ArrayList<JButton> mineButtonList;
    private HashMap<Long, JTable> userGameTableMap;
    private HashMap<Long, JLabel> turnUserMap;
    private Timer timer;
    private BackgroundPanel gameInfoPanel;
    private BufferedImage buttonImage;

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
					tmpGameScreen2 frame = new tmpGameScreen2();
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
	public tmpGameScreen2() {
		//////////////////////////
		User user = new User();
		user.setTurn(true);
		ArrayList<User> userList = new ArrayList<>();
		userList.add(new User());
		userList.add(new User());
		userList.add(new User());
		userList.add(new User());
		userId = user.getId();
		
		//////////////////////////
		
        this.userId = userId;
        userGameTableMap=new HashMap<>();
        turnUserMap=new HashMap<>();


        setTitle("Game Screen");
        getContentPane().setLayout(new BorderLayout());
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
        createTablePanel(userList);

        // 10x10 버튼인 마인 맵이 들어갈 패널
        mineButtonList = new ArrayList<>();


        mineMapPanel = createButtonGridPanel(10, 10);




        //////////////////////////////////////////////////////////////////////////////////////////
        JPanel userPanel = new JPanel();
        userPanel.setBounds(531, 10, 372, 515);
        userPanel.setBackground(new Color(104, 99, 74));
        userPanel.setLayout(null);
        userPanel.setBorder(new RoundedBorder(7, 2, Color.BLACK, 1));

        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(12, 10, 97, 15);
        userPanel.add(lblNewLabel);


        panelListModel = new DefaultListModel<>();
        panelList = new JList<>(panelListModel);
        panelList.setBackground(new Color(104, 99, 74));
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setCellRenderer(new InGamePanelListCellRenderer());

        JScrollPane panelListScrollPane = new JScrollPane(panelList);
        panelListScrollPane.setBounds(12, 44, 322, 449);
        panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        userPanel.add(panelListScrollPane);
 
        panelListModel.addElement(new InGameUserPanel(user));
        mainPanel.setLayout(null);
        
        //////////////////////////////////////////////////////////////////////////////////////////


        // 패널 추가
        mainPanel.add(userPanel);
        

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
                // 빈 패널
        		gameInfoPanel = new BackgroundPanel("/mineGame/Screen/icon/timerBackground.png");
                gameInfoPanel.setBounds(64, 15, 186, 66);
                gameInfoPanel.setOpaque(false);
                mainPanel.add(gameInfoPanel);
                
                        // 타이머 라벨 초기화 및 emptyPanel1에 추가
                        timerLabel = new JLabel("10", SwingConstants.CENTER);
                        timerLabel.setIcon(null);
                        timerLabel.setBounds(77, 13, 77, 42);
                        timerLabel.setForeground(new Color(255, 255, 255));
//                        timerLabel.setIcon(new ImageIcon(tmpGameRoom2.class.getResource("/mineGame/Screen/icon/timer.png")));
                        timerLabel.setFont(new Font("Arial", Font.BOLD, 34));
                        gameInfoPanel.setLayout(null);
                        gameInfoPanel.add(timerLabel);
                        
                        JLabel timerIcon = new JLabel("");
                        timerIcon.setForeground(new Color(255, 255, 255));
                        timerIcon.setIcon(new ImageIcon(new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/timer.png")).getImage().getScaledInstance(57, 42, Image.SCALE_SMOOTH)));
                        timerIcon.setBounds(21, 11, 57, 42);
                        gameInfoPanel.add(timerIcon);
                        
                        

                        BackgroundPanel panel_1 = new BackgroundPanel("/mineGame/Screen/icon/minePanelBackground.png");
                        JPanel panel = new JPanel(new GridLayout(5, 5, 1, 1));
                        panel.setBounds(0, 0, 376, 376);
                        panel_1.add(panel);
                        panel.setOpaque(false);
                        /////////////////////////////////////////////////////////////////////////////////////
                        buttonImage = loadImage("/mineGame/Screen/icon/mineButton_default.png");
                        for(int i=0;i<25;i++) {
                        JButton button1 = new JButton(){
                            @Override
                            protected void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                if (buttonImage != null) {
                                    // 버튼의 크기에 맞춰 이미지를 그립니다.
                                    g.drawImage(buttonImage, 0, 0, getWidth(), getHeight(), this);
                                }

                                // 버튼이 비활성화되었을 때의 처리
                                if (!isEnabled()) {
                                    g.setColor(new Color(0, 0, 0, 100)); // 반투명한 검정색
                                    g.fillRect(0, 0, getWidth(), getHeight());
                                }
                            }
                        };
                        
                        button1.setBorder(null);
                        button1.setOpaque(false);
                        button1.setContentAreaFilled(false);
                        panel.add(button1);
                        }
                       
                        
//                        JButton button2 = new JButton();
//                        JButton button3 = new JButton();
//                        JButton button4 = new JButton();
//                        JButton button5 = new JButton();
//                        JButton button6 = new JButton();
//                        JButton button7 = new JButton();
//                        JButton button8 = new JButton();
//                        JButton button9 = new JButton();
//                        panel.add(button2);
//                        panel.add(button3);
//                        panel.add(button4);
//                        panel.add(button5);
//                        panel.add(button6);
//                        panel.add(button7);
//                        panel.add(button8);
//                        panel.add(button9);
                        
                                
                                
                        
                                //////////////////////////// bottom 패널 생성 //////////////////
                                JPanel bottomPanel = new JPanel();
                                bottomPanel.setBounds(116, 473, 280, 41);
                                mainPanel.add(bottomPanel);
                                bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                bottomPanel.setLayout(null);
                                JButton giveupButton = new JButton("Give Up");
                                giveupButton.setBounds(10, 10, 260, 23);
                                bottomPanel.add(giveupButton);
                                
                                BackgroundPanel mineNumPanel = new BackgroundPanel("/mineGame/Screen/icon/timerBackground.png");
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
                                
                                JLabel remainMineLabel_1_1 = new JLabel("30", SwingConstants.CENTER);
                                remainMineLabel_1_1.setForeground(Color.WHITE);
                                remainMineLabel_1_1.setFont(new Font("Arial", Font.BOLD, 34));
                                remainMineLabel_1_1.setBounds(85, 13, 62, 42);
                                mineNumPanel.add(remainMineLabel_1_1);
                                
                                panel_1.setBounds(64, 87, 376, 376);
                                mainPanel.add(panel_1);
                                panel_1.setLayout(null);
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
    
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
