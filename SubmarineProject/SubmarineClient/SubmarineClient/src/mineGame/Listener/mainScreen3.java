package mineGame.Listener;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import mineGame.GameRoom;
import mineGame.User;
import mineGame.ListCallRenderer.InGamePanelListCellRenderer;
import mineGame.ListCallRenderer.PanelListCellRenderer;
import mineGame.Screen.RoomScreen;
import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainScreen3 extends JFrame {
	private JList<GameRoom> gameRoomList;
    private DefaultListModel<GameRoom> gameRoomListModel;
    public RoomScreen roomScreen;
    private long userId;
    private User myUser;
	private JPanel menuPanel;

	////////////////////////////////////////////
    private JList<InGameUserPanel> panelList;
    private DefaultListModel<InGameUserPanel> panelListModel;
	////////////////////////////////////////////
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainScreen3 frame = new mainScreen3(new User());
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
	public mainScreen3(User myUser) {
		System.out.println("내 정보 = "+myUser);
        System.out.println("main 생성");
        this.myUser = myUser;

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(201, 197, 179));
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setBounds(250, 46, 559, 472);



        
        /////////////////////////////////////////////////////////////
        panelListModel = new DefaultListModel<>();
        /////////////////////////////////////////////////////////////

        
        
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setBounds(12, 23, 535, 50);





        //게임 방 목록 세팅
        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        

        centerPanel.setLayout(null);

        //Center 구성
        initialMenuPanel(menuPanel);
        centerPanel.add(menuPanel);
    
        JScrollPane scrollPane_1 = new JScrollPane(gameRoomList);
        scrollPane_1.setBounds(12, 73, 535, 173);
        centerPanel.add(scrollPane_1);
        mainPanel.setLayout(null);
        mainPanel.add(centerPanel);
        
        

        // 컨테이너를 프레임에 올림.
        getContentPane().add(mainPanel);
        
        
        JPanel infoManagePanel = new JPanel();
        infoManagePanel.setBounds(251, 10, 558, 37);
        infoManagePanel.setOpaque(false);
        mainPanel.add(infoManagePanel);
        infoManagePanel.setLayout(null);
        
        JButton infoButton = new JButton("");
        infoButton.setBounds(485, 10, 64, 23);
        infoManagePanel.add(infoButton);
        
        JLabel lblNewLabel_1 = new JLabel("Mine");
        lblNewLabel_1.setBounds(12, 10, 160, 34);
        mainPanel.add(lblNewLabel_1);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
        
        JPanel userPanel = new JPanel();
        userPanel.setBounds(12, 54, 212, 450);
        mainPanel.add(userPanel);
        userPanel.setBackground(new Color(104, 99, 74));
        userPanel.setLayout(null);
        userPanel.setBorder(new RoundedBorder(7, 0));
        
        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(12, 10, 97, 15);
        userPanel.add(lblNewLabel);
        
//        for (int i = 0; i < 1; i++) {
//            panelListModel.addElement(new UserPanel("User " + (i + 1), i));
//        }
        
        panelList = new JList<>(panelListModel);
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setOpaque(false);
        panelList.setCellRenderer(new InGamePanelListCellRenderer());
        
           JScrollPane panelListScrollPane = new JScrollPane(panelList);
           panelListScrollPane.setBounds(12, 44, 188, 396);
           panelListScrollPane.setOpaque(false);
           panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
           userPanel.add(panelListScrollPane);

        setBounds(200,200,837,567);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



    }
	
	private void initialMenuPanel(JPanel panel) {
        menuPanel.setLayout(null);
        
        
        
        JLabel createRoom = new JLabel("");
        createRoom.setBounds(35, 4, 153, 41);
        createRoom.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/createRoom.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(createRoom);
        
        JLabel noUserRoom = new JLabel("");
        noUserRoom.setBounds(193, 4, 153, 41);
        noUserRoom.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/practice.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(noUserRoom);
        
        JLabel statistics = new JLabel("");
        statistics.setBounds(351, 4, 153, 41);
        statistics.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/statistics.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(statistics);
        
        
        
        
        
        
        
    }
}
