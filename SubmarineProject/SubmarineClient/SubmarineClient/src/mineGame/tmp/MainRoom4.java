package mineGame.tmp;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
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
import mineGame.Screen.component.BackgroundPanel;
import mineGame.Screen.component.GameRoomPanel;
import mineGame.Screen.component.InGameUserPanel;
import mineGame.Screen.component.RoundPanel;
import mineGame.Screen.component.RoundedBorder;
import mineGame.Screen.component.UserPanel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainRoom4 extends JFrame {
	private JList<GameRoomPanel> gameRoomList;
    private DefaultListModel<GameRoomPanel> gameRoomListModel;
    public RoomScreen roomScreen;
    private long userId;
    private User myUser;
	private JPanel menuPanel;

	////////////////////////////////////////////
    private JList<InGameUserPanel> panelList;
    private DefaultListModel<InGameUserPanel> panelListModel;
	////////////////////////////////////////////

	////////////////////////////////////////////
    private JList<String> chatList;
    private DefaultListModel<String> chatListModel;
    private JTextField txtAsasd;
	////////////////////////////////////////////


	/**
	 * Create the frame.
	 */
	public MainRoom4 (User myUser) {
		System.out.println("내 정보 = "+myUser);
        System.out.println("main 생성");
        this.myUser = myUser;

        BackgroundPanel mainPanel = new BackgroundPanel("/mineGame/Screen/icon/backgroundIMG.png");
        mainPanel.setBackground(new Color(202, 190, 151));
        RoundPanel centerPanel = new RoundPanel(2,"/mineGame/Screen/icon/background.png");
        centerPanel.setBackground(new Color(201, 197, 179));
        centerPanel.setBorder(new RoundedBorder(2, 0, new Color(217, 214, 200), 2));
        centerPanel.setBounds(241, 69, 631, 456);



        
        /////////////////////////////////////////////////////////////
        panelListModel = new DefaultListModel<>();
        /////////////////////////////////////////////////////////////

        
        
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setBounds(12, 5, 535, 50);





        //게임 방 목록 세팅
        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        GameRoom gameRoom = new GameRoom("room1", 10, true, 100L, 10, 20, myUser);
        gameRoomListModel.addElement(new GameRoomPanel(gameRoom));


        

        centerPanel.setLayout(null);

        //Center 구성
        initialMenuPanel(menuPanel);
        centerPanel.add(menuPanel);
    
        JScrollPane scrollPane_1 = new JScrollPane(gameRoomList);
        scrollPane_1.setBounds(12, 53, 605, 206);
        centerPanel.add(scrollPane_1);
        mainPanel.setLayout(null);
        mainPanel.add(centerPanel);
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        chatListModel = new DefaultListModel<>();
        chatListModel.addElement("somdos");
        chatList = new JList<>(chatListModel);
        JScrollPane chatScrollPane = new JScrollPane(chatList);
        chatScrollPane.setBounds(12, 266, 605, 158);
        centerPanel.add(chatScrollPane);
        
        txtAsasd = new JTextField();
        txtAsasd.setForeground(new Color(71, 71, 71));
        txtAsasd.setFont(new Font("Arial", Font.BOLD, 14));
        txtAsasd.setText("asasd");
        txtAsasd.setBounds(91, 423, 460, 24);
        txtAsasd.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        centerPanel.add(txtAsasd);
        txtAsasd.setColumns(10);
        
        JLabel writerNameLabel = new JLabel("New label");
        writerNameLabel.setForeground(new Color(71, 71, 71));
        writerNameLabel.setBackground(new Color(255, 255, 255));
        writerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        writerNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        writerNameLabel.setBounds(12, 423, 79, 24);
        writerNameLabel.setOpaque(true);
        writerNameLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        centerPanel.add(writerNameLabel);
        
        JButton sendButton = new JButton("");
        sendButton.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/sendButton.png"))).getImage().getScaledInstance(72, 23, Image.SCALE_SMOOTH)));
        sendButton.setBounds(551, 424, 66, 23);
        sendButton.setOpaque(false);
        sendButton.setContentAreaFilled(false);
        centerPanel.add(sendButton);
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        
        

        // 컨테이너를 프레임에 올림.
        getContentPane().add(mainPanel);
        
        
        JPanel infoManagePanel = new JPanel();
        infoManagePanel.setBounds(566, 10, 303, 49);
        infoManagePanel.setOpaque(false);
        mainPanel.add(infoManagePanel);
        infoManagePanel.setLayout(null);
        
        JLabel infoButton = new JLabel("");
        infoButton.setBounds(240, 6, 40, 40);
        infoButton.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/roundUserImg.png"))).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        infoManagePanel.add(infoButton);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/titleLogo.png"))).getImage().getScaledInstance(187, 42, Image.SCALE_SMOOTH)));
        lblNewLabel_1.setBounds(15, 7, 187, 42);
        mainPanel.add(lblNewLabel_1);
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 18));
        
        RoundPanel userPanel = new RoundPanel(1,"/mineGame/Screen/icon/background.png");
        userPanel.setBounds(12, 69, 212, 456);
        mainPanel.add(userPanel);
        userPanel.setBackground(new Color(201, 197, 179));
        userPanel.setLayout(null);
        userPanel.setBorder(new RoundedBorder(2, 0, new Color(217, 214, 200), 2));
        
        JLabel lblNewLabel = new JLabel("User List");
        lblNewLabel.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/userLabelIcon.png"))).getImage().getScaledInstance(62, 23, Image.SCALE_SMOOTH)));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel.setBounds(12, 5, 62, 23);
        userPanel.add(lblNewLabel);
        
//        for (int i = 0; i < 1; i++) {
//            panelListModel.addElement(new UserPanel("User " + (i + 1), i));
//        }
        
        panelList = new JList<>(panelListModel);
        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setOpaque(false);
        panelList.setCellRenderer(new InGamePanelListCellRenderer());
        
           JScrollPane panelListScrollPane = new JScrollPane(panelList);
           panelListScrollPane.setBounds(12, 34, 188, 412);
           panelListScrollPane.setOpaque(false);
           panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
           userPanel.add(panelListScrollPane);

        setBounds(200,200,901,579);
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
