package mineGame.tmp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mineGame.GameRoom;
import mineGame.SubmarineClient;
import mineGame.User;
import mineGame.ListCallRenderer.GameRoomUserCellRenderer;
import mineGame.ListCallRenderer.UserListCellRenderer;
import mineGame.Screen.MainScreen;
import mineGame.Screen.component.BackgroundPanel;
import mineGame.Screen.component.GameRoomUserPanel;
import mineGame.Screen.component.LineSeparator;
import mineGame.Screen.component.UserPanel;

public class RoomScreen3 extends JFrame {

	private JList<GameRoomUserPanel> userList;
    private DefaultListModel<GameRoomUserPanel> userListModel;
    private MainScreen mainScreen;
    private GameRoom gameRoom;
    private User myUser;
    private ArrayList<User> users;
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User user = new User();
					ArrayList<User> users = new ArrayList<>();
					users.add(new User());
					users.add(new User());
					users.add(new User());
					MainScreen main = new MainScreen(user);
					GameRoom gameRoom = new GameRoom("name", 4, isDefaultLookAndFeelDecorated(), 100L, 10, 20, user);
					RoomScreen3 frame = new RoomScreen3(user,main,gameRoom);
					frame.setRoomUserList(users);
					frame.addUser(user);
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
	public RoomScreen3(User user,MainScreen mainScreen,GameRoom gameRoom) {
        // 방장이 아닌 제 3자가 참여한 경우
        System.out.println("-------- 참가하는 방 화면 생성");
        this.mainScreen = mainScreen;
        this.gameRoom = gameRoom;
        this.myUser = myUser;

        mainScreen.setVisible(false); // MainScreen 숨기기


        LineSeparator line = new LineSeparator();
        line.setBackground(new Color(74, 74, 74));
        line.setBounds(151, 44, 319, 3);
        
        BackgroundPanel mainPanel = new BackgroundPanel("/mineGame/Screen/icon/background.png");
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(12, 67, 559, 491);
        centerPanel.setOpaque(false);
        
        JPanel topPanel = new JPanel();
        topPanel.setBounds(13, 13, 555, 51);
        topPanel.setOpaque(false);
        topPanel.add(line);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(13, 568, 559, 57);
        bottomPanel.setOpaque(false);

        userListModel = new DefaultListModel<>();
        


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 창이 닫히기 전에 수행할 작업 작성
                SubmarineClient.sendRoomCommand("deleteRoomClient",gameRoom,myUser);
                SubmarineClient.sendCommand("deleteClient",myUser.getId());
            }
        });
        
        centerPanel.setLayout(null);
        bottomPanel.setLayout(null);
        mainPanel.setLayout(null);

        mainPanel.add(topPanel);
        topPanel.setLayout(null);
        
        

        JLabel roomNameLabel = new JLabel("");
        roomNameLabel.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/titleLogo.png"))).getImage().getScaledInstance(114, 44, Image.SCALE_SMOOTH)));
        roomNameLabel.setBounds(2, -1, 114, 44);
        topPanel.add(roomNameLabel);
        
        JLabel roomNameValue = new JLabel(gameRoom.getRoomName());
        roomNameValue.setForeground(new Color(74, 74, 74));
        roomNameValue.setFont(new Font("Arial", Font.BOLD, 16));
        roomNameValue.setBounds(150, 14, 319, 31);
        topPanel.add(roomNameValue);
        mainPanel.add(bottomPanel);
        
        

        //South 구성
        JLabel readyButton = new JLabel("");
        readyButton.setBounds(2, 12, 155, 44);
        bottomPanel.add(readyButton);
        readyButton.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/gameRoomReady.png"))).getImage().getScaledInstance(155, 44, Image.SCALE_SMOOTH)));
        readyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	myUser.setReady(!myUser.isReady());
                
                	//서버에게 해당 유저의 준비상태 메세지 보내야함
            	SubmarineClient.sendRoomCommand("updateReadyState",gameRoom,myUser);
                
                	// 화면 갱신
                
            }
        });
        

        JLabel exitButton = new JLabel("나가기"); // 나가기 버튼 추가
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose(); // RoomScreen 닫기
                mainScreen.setVisible(true); // MainScreen 표시
                SubmarineClient.sendRoomCommand("deleteRoomClient",gameRoom,myUser);
            }
        });
        exitButton.setBounds(169, 12, 155, 44);
        bottomPanel.add(exitButton);
        exitButton.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/gameRoomExit.png"))).getImage().getScaledInstance(155, 44, Image.SCALE_SMOOTH)));
        
        
        mainPanel.add(centerPanel);

        userList = new JList<>(userListModel);
        userList.setBackground(new Color(0, 14, 30));
        userList.setOpaque(false);
        userList.setCellRenderer(new GameRoomUserCellRenderer());

        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setBounds(12, 24, 304, 293);
        centerPanel.add(userScrollPane);
        userScrollPane.setPreferredSize(new Dimension(400, 300));
        userScrollPane.setBorder(null);
        userScrollPane.setOpaque(false);
        BackgroundPanel mapPanel = new BackgroundPanel("/mineGame/Screen/icon/userBackground.png");
        mapPanel.setBounds(327, 24, 211, 293);
        centerPanel.add(mapPanel);
        mapPanel.setLayout(null);
        
        
        
        ////////////////////////////////////////////
        JLabel mapLabel = new JLabel("10 X 10");
        mapLabel.setForeground(new Color(255, 255, 255));
        mapLabel.setFont(new Font("Nirmala UI", Font.BOLD, 30));
        mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mapLabel.setBounds(41, 10, 126, 126);
        mapLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        mapPanel.add(mapLabel);
        
        JLabel lblMine = new JLabel("Mine");
        lblMine.setHorizontalAlignment(SwingConstants.LEFT);
        lblMine.setForeground(Color.WHITE);
        lblMine.setFont(new Font("Nirmala UI", Font.BOLD, 16));
        lblMine.setBounds(31, 158, 65, 28);
        mapPanel.add(lblMine);
        
        JLabel lblMine_1 = new JLabel(gameRoom.getMineNum()+"");
        lblMine_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblMine_1.setForeground(Color.WHITE);
        lblMine_1.setFont(new Font("Nirmala UI", Font.BOLD, 16));
        lblMine_1.setBounds(97, 158, 38, 28);
        mapPanel.add(lblMine_1);
        
        JLabel lblPlayer = new JLabel("Player");
        lblPlayer.setHorizontalAlignment(SwingConstants.LEFT);
        lblPlayer.setForeground(Color.WHITE);
        lblPlayer.setFont(new Font("Nirmala UI", Font.BOLD, 16));
        lblPlayer.setBounds(30, 196, 65, 28);
        mapPanel.add(lblPlayer);
        
        JLabel lblPlayer_1 = new JLabel(gameRoom.getMaxPlayer()+"");
        lblPlayer_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblPlayer_1.setForeground(Color.WHITE);
        lblPlayer_1.setFont(new Font("Nirmala UI", Font.BOLD, 16));
        lblPlayer_1.setBounds(96, 196, 38, 28);
        mapPanel.add(lblPlayer_1);
        
        JLabel lblCreater = new JLabel("Creater");
        lblCreater.setHorizontalAlignment(SwingConstants.LEFT);
        lblCreater.setForeground(Color.WHITE);
        lblCreater.setFont(new Font("Nirmala UI", Font.BOLD, 16));
        lblCreater.setBounds(30, 238, 65, 28);
        mapPanel.add(lblCreater);
        
        JLabel lblCreater_1 = new JLabel(gameRoom.getChairmanId()+"");
        lblCreater_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblCreater_1.setForeground(Color.WHITE);
        lblCreater_1.setFont(new Font("Nirmala UI", Font.BOLD, 13));
        lblCreater_1.setBounds(96, 240, 101, 28);
        mapPanel.add(lblCreater_1);
        userScrollPane.getViewport().setOpaque(false);
        
        
        
        //컨테이너에 컴포넌트 추가
        // 컨테이너를 프레임에 올림.
        getContentPane().add(mainPanel);

        setBounds(200,200,597,674);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


    }

    private void initalToolBar(JToolBar toolBar) {
        JButton createRoom = new JButton("방 설정");
        JButton statistics = new JButton("기타");
        toolBar.add(createRoom);
        toolBar.add(statistics);
    }

    public void setRoomUserList(ArrayList<User> users) {
        this.users = users;
        System.out.println("    화면의 방 참여자 목록 재설정");
        userListModel.clear();
        for(User user : users){
            userListModel.addElement(new GameRoomUserPanel(user));
        }
    }

    public void addUser(User myUser) {
        users.add(myUser);
        System.out.println("여기까지1");
        userListModel.addElement(new GameRoomUserPanel(myUser));
        System.out.println("여기까지2");
    }

    public void showInfo(String s) {
        JOptionPane.showMessageDialog(this, s, "알림", JOptionPane.INFORMATION_MESSAGE);
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