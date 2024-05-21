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
import mineGame.Screen.RoomScreen;
import mineGame.Screen.component.UserList;
import mineGame.Screen.item.Item;

import javax.swing.border.EmptyBorder;

public class MainScreen2 extends JFrame {
	private JList<User> userList;
	private JList<User> userList_2;
	private JList<User> userList_1;
    private DefaultListModel<User> userListModel;
    private JList<GameRoom> gameRoomList;
    private DefaultListModel<GameRoom> gameRoomListModel;
    public RoomScreen roomScreen;
    private long userId;
    private User myUser;
    
    private UserList<Item> tmpUserList;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel menuPanel;

	
	
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
        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(250, 46, 559, 472);
        JPanel westPanel = new JPanel();
        westPanel.setBounds(0, 0, 238, 518);


        
        menuPanel = new JPanel();
        menuPanel.setBounds(12, 68, 535, 33);



        //게임 방 목록 세팅
        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


      //접속 중인 유저 목록 세팅
        ///////////////////////////////////////////////////////
        userListModel = new DefaultListModel<>();
        userList = new JList<>(new AbstractListModel() {
        	String[] values = new String[] {"asdasd | sadasds | dasdasd", "asdasd | sadasds | dasdasd", "asdasd | sadasds | dasdasd", "asdasd | sadasds | dasdasd"};
        	public int getSize() {
        		return values.length;
        	}
        	public Object getElementAt(int index) {
        		return values[index];
        	}
        });
        userList.setBorder(new EmptyBorder(0, 0, 0, 0));
        userList.setOpaque(false);
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setOpaque( false );
        userList.setCellRenderer( renderer );
        ///////////////////////////////////////////////////////

        
        centerPanel.setLayout(null);

        //Center 구성
        initialMenuPanel(menuPanel);
        centerPanel.add(menuPanel);
        JScrollPane scrollPane_1 = new JScrollPane(gameRoomList);
        scrollPane_1.setBounds(12, 111, 535, 173);
        centerPanel.add(scrollPane_1);
        mainPanel.setLayout(null);
        westPanel.setLayout(null);

        
        //West 구성
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBounds(12, 144, 212, 147);
        westPanel.add(scrollPane);


        mainPanel.add(westPanel);
        
        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setBounds(12, 119, 57, 15);
        westPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
        lblNewLabel_1.setBounds(12, 22, 160, 51);
        westPanel.add(lblNewLabel_1);
        mainPanel.add(centerPanel);
        
        

        // 컨테이너를 프레임에 올림.
        getContentPane().add(mainPanel);
        
        
        JPanel panel = new JPanel();
        panel.setBounds(248, 10, 561, 37);
        mainPanel.add(panel);
        panel.setLayout(null);
        
        JButton btnNewButton = new JButton("New button");
        btnNewButton.setBounds(485, 10, 64, 23);
        panel.add(btnNewButton);

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
