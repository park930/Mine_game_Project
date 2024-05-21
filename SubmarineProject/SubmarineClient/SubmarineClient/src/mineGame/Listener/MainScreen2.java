package mineGame.Listener;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import mineGame.GameRoom;
import mineGame.SubmarineClient;
import mineGame.User;
import mineGame.ListCallRenderer.PanelListCellRenderer;
import mineGame.Screen.RoomScreen;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;

import javax.swing.border.EmptyBorder;

public class MainScreen2 extends JFrame {
    private JList<GameRoom> gameRoomList;
    private DefaultListModel<GameRoom> gameRoomListModel;
    public RoomScreen roomScreen;
    private long userId;
    private User myUser;
	private JPanel menuPanel;

	////////////////////////////////////////////
    private JList<UserPanel> panelList;
    private DefaultListModel<UserPanel> panelListModel;
	////////////////////////////////////////////
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen2 frame = new MainScreen2(new User());
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
	public MainScreen2(User myUser) {
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
        menuPanel.setBounds(12, 68, 535, 33);





        //게임 방 목록 세팅
        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        

        centerPanel.setLayout(null);

        //Center 구성
        initialMenuPanel(menuPanel);
        centerPanel.add(menuPanel);
        JScrollPane scrollPane_1 = new JScrollPane(gameRoomList);
        scrollPane_1.setBounds(12, 111, 535, 173);
        centerPanel.add(scrollPane_1);
        mainPanel.setLayout(null);
        mainPanel.add(centerPanel);
        
        

        // 컨테이너를 프레임에 올림.
        getContentPane().add(mainPanel);
        
        
        JPanel panel = new JPanel();
        panel.setBounds(251, 10, 558, 37);
        panel.setOpaque(false);
        mainPanel.add(panel);
        panel.setLayout(null);
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setBounds(485, 10, 64, 23);
        panel.add(btnNewButton);
        
        JLabel lblNewLabel_1 = new JLabel("New label");
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
        panelList.setCellRenderer(new PanelListCellRenderer());
        
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
        JButton createRoom = new JButton("방 생성");
        createRoom.setBounds(0, 0, 167, 33);
        JButton noUserRoom = new JButton("컴퓨터 대결");
        noUserRoom.setBounds(189, 0, 167, 33);
        JButton statistics = new JButton("통계");
        statistics.setBounds(368, 0, 167, 33);
        menuPanel.setLayout(null);
        panel.add(createRoom);
        panel.add(noUserRoom);
        panel.add(statistics);
    }
}
