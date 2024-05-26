package mineGame.Screen;

import mineGame.ChatInfo;
import mineGame.GameRoom;
import mineGame.ListCallRenderer.ChatInfoListCellRenderer;
import mineGame.ListCallRenderer.RoomListCellRenderer;
import mineGame.Listener.DoubleClickListener;
import mineGame.Screen.component.*;
import mineGame.SubmarineClient;
import mineGame.User;
import mineGame.ListCallRenderer.PanelListCellRenderer;
import mineGame.tmp.RoomScreen2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainScreen extends JFrame{
    private JList<GameRoomPanel> gameRoomList;
    private DefaultListModel<GameRoomPanel> gameRoomListModel;
    public RoomScreen roomScreen;
    private long userId;
    private User myUser;
	private JPanel menuPanel;

	////////////////////////////////////////////
	private JList<UserPanel> panelList;
    private DefaultListModel<UserPanel> panelListModel;
    public UserInfoDialog userInfoDialog;
    public CreateRoomDialog createRoomDialog;
    public GameRecordDialog gameRecordDialog;
    private JList<ChatInfo> chatList;
    private DefaultListModel<ChatInfo> chatListModel;
    private JTextField chatField;
    private JLabel writerNameLabel;
    private JScrollPane scrollPane_1;
    private JScrollPane chatScrollPane;
    ////////////////////////////////////////////

	public void myInfoUpdate(User myUser){
        this.myUser = myUser;
        writerNameLabel.setText("["+myUser.getUserName()+"]");
    }
	
    public MainScreen(User user) {
        System.out.println("내 정보 = "+myUser);
        System.out.println("main 생성");
        this.myUser = user;

        BackgroundPanel mainPanel = new BackgroundPanel("/mineGame/Screen/icon/backgroundIMG.png");
        RoundPanel centerPanel = new RoundPanel(1,"/mineGame/Screen/icon/background.png");
        centerPanel.setBackground(new Color(201, 197, 179));
        centerPanel.setBorder(new RoundedBorder(2, 0, new Color(217, 214, 200), 2));
        centerPanel.setBounds(241, 69, 631, 456);


        panelListModel = new DefaultListModel<>();
        panelList = new JList<>(panelListModel);
        panelList.setCellRenderer(new PanelListCellRenderer());

        

        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setBounds(12, 5, 535, 50);






        //게임 방 목록 세팅
        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameRoomList.setCellRenderer(new RoomListCellRenderer());
        //접속 중인 유저 목록 세팅
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setOpaque( false );

        
        

        centerPanel.setLayout(null);

        //Center 구성
        initialMenuPanel(menuPanel);
        centerPanel.add(menuPanel);
        
        scrollPane_1 = new JScrollPane(gameRoomList);
        scrollPane_1.setBounds(12, 53, 605, 206);
        centerPanel.add(scrollPane_1);
        mainPanel.setLayout(null);
        mainPanel.add(centerPanel);

        

        JPanel infoManagePanel = new JPanel();
        infoManagePanel.setBounds(566, 10, 303, 49);
        infoManagePanel.setOpaque(false);
        mainPanel.add(infoManagePanel);
        infoManagePanel.setLayout(null);
        
        JLabel infoButton = new JLabel("");
        infoButton.setBounds(240, 6, 40, 40);
        infoButton.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/roundUserImg.png"))).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        infoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("정보창 열기 전, 내 정보 : "+myUser);
                userInfoDialog = new UserInfoDialog(myUser);
                userInfoDialog.setVisible(true);
                userInfoDialog.setLocationRelativeTo(null);
            }
        });
        infoManagePanel.add(infoButton);

        	
        
        

        
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        chatListModel = new DefaultListModel<>();
        chatListModel.addElement(new ChatInfo("<"+myUser.getUserName()+"님이 입장하셨습니다.>","",0L,0L,"#FFA500"));
        chatList = new JList<>(chatListModel);
        chatList.setCellRenderer(new ChatInfoListCellRenderer());
        chatScrollPane = new JScrollPane(chatList);
        chatScrollPane.setBounds(12, 266, 605, 158);
        centerPanel.add(chatScrollPane);
        
        chatField = new JTextField();
        chatField.setForeground(new Color(71, 71, 71));
        chatField.setFont(new Font("Arial", Font.BOLD, 14));
        chatField.setBounds(91, 423, 460, 24);
        chatField.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        centerPanel.add(chatField);
        chatField.setColumns(10);
        
        writerNameLabel = new JLabel("["+myUser.getUserName()+"]");
        writerNameLabel.setForeground(new Color(71, 71, 71));
        writerNameLabel.setBackground(new Color(255, 255, 255));
        writerNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        writerNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        writerNameLabel.setBounds(12, 423, 79, 24);
        writerNameLabel.setOpaque(true);
        writerNameLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        centerPanel.add(writerNameLabel);
        
        
        JButton sendButton = new JButton("");
        sendButton.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/sendButton.png"))).getImage().getScaledInstance(66, 23, Image.SCALE_SMOOTH)));
        sendButton.setBounds(551, 424, 66, 23);
        centerPanel.add(sendButton);
        
        ActionListener sendAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = chatField.getText();
                SubmarineClient.sendCommand("mainChatSend", new ChatInfo(content, myUser.getUserName(), myUser.getId(), 0L, "#000000"));
                chatField.setText("");
            }
        };
        
        // 채팅에서 엔터 누르면 동작할 것
        chatField.addActionListener(sendAction);
        sendButton.addActionListener(sendAction);
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        
        
        

        

        JLabel lblNewLabel_1 = new JLabel("Mine");
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

        panelList.setBorder(new EmptyBorder(0, 0, 0, 0));
        panelList.setOpaque(false);
        panelList.setCellRenderer(new PanelListCellRenderer());

        JScrollPane panelListScrollPane = new JScrollPane(panelList);
        panelListScrollPane.setBounds(12, 34, 192, 412);
        panelListScrollPane.setOpaque(false);
        panelListScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        userPanel.add(panelListScrollPane);
        mainPanel.add(centerPanel);
        
        
        // 컨테이너를 프레임에 올림.
        add(mainPanel);

        setBounds(200,200,901,579);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // 창이 닫히기 전에 수행할 작업 작성
                SubmarineClient.sendCommand("deleteClient",myUser.getId());
            }
        });

        // 게임 방 목록 더블 클릭 이벤트 처리
        gameRoomList.addMouseListener(new DoubleClickListener(() -> {
            System.out.println("------- 방 더블 클릭 함");
            GameRoom selectedRoom = gameRoomList.getSelectedValue().getGameRoom();
            if (selectedRoom != null) {

                // 해당 클라이언트가 방에 들어가길 원한다는 메세지 서버한테 보내야함
                SubmarineClient.sendRoomCommand("joinRoom",selectedRoom,myUser);

                for(User u : selectedRoom.getPlayerList()){
                    System.out.println("  방 안의 플레이어 :"+u.getUserName());
                }
                System.out.println(selectedRoom);

//                roomScreen = new RoomScreen(myUser,MainScreen.this, selectedRoom);
//                System.out.println("    main화면에서 room에 대한 객체 설정");
//                roomScreen.setVisible(true);

                ///////////////roomScreen에서 방 참가자 목록 나오게 해야함

            }
        }));


    }

    private void initialMenuPanel(JPanel panel) {
        JLabel createRoom = new JLabel("");
        createRoom.setBounds(35, 4, 153, 41);
        createRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                new CreateRoomListener().actionPerformed(null);
                createRoomDialog = new CreateRoomDialog(myUser);
            }
        });
        
        
        createRoom.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/createRoom.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(createRoom);
        
        JLabel noUserRoom = new JLabel("");
        noUserRoom.setBounds(193, 4, 153, 41);
        noUserRoom.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/practice.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(noUserRoom);
        
        JLabel statistics = new JLabel("");
        statistics.setBounds(351, 4, 153, 41);
        statistics.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // 서버에 전적 조회한다고 메세지 보내기
                SubmarineClient.sendCommand("getGameRecord",myUser.getId());
            }
        });

        statistics.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/statistics.png"))).getImage().getScaledInstance(153, 41, Image.SCALE_SMOOTH)));
        panel.add(statistics);
        
    }


    public void setRoomList(ArrayList<GameRoom> roomList)
    {
        gameRoomListModel.clear();
        for(GameRoom gameRoom : roomList){
            gameRoomListModel.addElement(new GameRoomPanel(gameRoom));
        }
    }

    public void setUserList(ArrayList<User> userList) {
        panelListModel.clear();
        for(User user : userList){
            System.out.println("    다시 유저 채워 넣음");
            panelListModel.addElement(new UserPanel(user));
        }
    }

    public void createJoinRoomScreen(GameRoom gameRoom) {
        System.out.println(" 방 생성하라는 명령 받음");
        roomScreen = new RoomScreen(myUser,MainScreen.this, gameRoom);
        System.out.println("    main화면에서 room에 대한 객체 설정");
        roomScreen.setVisible(true);
    }

    public void showInfo(String s) {
        JOptionPane.showMessageDialog(this, s, "알림", JOptionPane.INFORMATION_MESSAGE);
    }


    public void addMainChat(ChatInfo chatInfo,String type) {
        if(type.equals("userChat")){
            chatInfo.setColor("#000000");
        } else {
            chatInfo.setColor("#FFA500");
        }
        chatListModel.addElement(chatInfo);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JScrollBar verticalBar = chatScrollPane.getVerticalScrollBar();
                verticalBar.setValue(verticalBar.getMaximum());
            }
        });
    }
}
