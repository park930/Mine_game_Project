package MineGame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import MineGame.listCallRenderer.DoubleClickListener;
import MineGame.listCallRenderer.GameRecordPanelListCellRenderer;
import MineGame.listCallRenderer.UserPanelListCellRenderer;
import room.GameRoom;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class TmpMainScreen extends JFrame {


    private ArrayList<ChatInfo> mainChatList;
    private Map<Long, ArrayList<ChatInfo>> roomChatListMap;

    
	private JList<UserInfoPanel> clientList;
	private DefaultListModel<UserInfoPanel> clientListModel;

    private JList<GameRecordPanel> gameRecordList;

    private DefaultListModel<GameRecordPanel> gameRecordListModel;

    private JList<GameRoom> gameRoomList;
    private DefaultListModel<GameRoom> gameRoomListModel;

    private ArrayList<GameRoom> roomList;

    private Map<Long, ArrayList<SubmarineServer.Client>> gameRoomClientMap;
    private Map<GameRoom, ArrayList<GameRecord>> roomRecordListMap;

    public TmpMainScreen(java.util.Map<Long, ArrayList<SubmarineServer.Client>> gameRoomClientMap) {
        this.gameRoomClientMap = gameRoomClientMap;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(252, 10, 346, 499);
        centerPanel.setOpaque(false);
        JPanel westPanel = new JPanel();
        westPanel.setBounds(3, 53, 236, 456);
        westPanel.setOpaque(false);
        clientListModel = new DefaultListModel<>();
        clientList = new JList<>(clientListModel);
        clientList.setCellRenderer(new UserPanelListCellRenderer());

        gameRecordListModel = new DefaultListModel<>();
        gameRecordList = new JList<>(gameRecordListModel);
        gameRecordList.setCellRenderer(new GameRecordPanelListCellRenderer());

        gameRoomListModel = new DefaultListModel<>();
        gameRoomList = new JList<>(gameRoomListModel);
        gameRoomList.setFont(new Font("Arial", Font.PLAIN, 10));


        JScrollPane clientScrollPane = new JScrollPane(clientList);
        clientScrollPane.setBounds(12, 31, 220, 413);
        JScrollPane roomScrollPane = new JScrollPane(gameRoomList);
        roomScrollPane.setBounds(12, 81, 318, 151);
        JScrollPane recordScrollPane = new JScrollPane(gameRecordList);
        recordScrollPane.setBounds(12, 263, 318, 224);
        westPanel.setLayout(null);
        westPanel.add(clientScrollPane);
        centerPanel.setLayout(null);
        centerPanel.add(roomScrollPane);
        centerPanel.add(recordScrollPane);
        mainPanel.setLayout(null);
        mainPanel.add(westPanel);
        
        JLabel userListLabel = new JLabel("User List");
        userListLabel.setForeground(new Color(82, 82, 82));
        userListLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userListLabel.setBounds(12, 10, 67, 18);
        westPanel.add(userListLabel);
        mainPanel.add(centerPanel);
        
        JLabel roomListLabel = new JLabel("Room List");
        roomListLabel.setForeground(new Color(82, 82, 82));
        roomListLabel.setFont(new Font("Arial", Font.BOLD, 12));
        roomListLabel.setBounds(14, 61, 67, 18);
        centerPanel.add(roomListLabel);
        
        JLabel gameRecordLabel = new JLabel("Game Record");
        gameRecordLabel.setForeground(new Color(82, 82, 82));
        gameRecordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gameRecordLabel.setBounds(13, 242, 95, 18);
        centerPanel.add(gameRecordLabel);
        
        JButton btnNewButton = new JButton("Chat Log");
        btnNewButton.setFont(new Font("Arial", Font.BOLD, 15));
        btnNewButton.setBackground(new Color(211, 211, 211));
        btnNewButton.setBounds(12, 10, 134, 36);
        centerPanel.add(btnNewButton);

        ////////////////////////////////////////////////////////////
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatLogDialog chatLogDialog = new ChatLogDialog(SubmarineServer.getMainChat());
            }
        });
        ////////////////////////////////////////////////

        getContentPane().add(mainPanel);
        
        JLabel adminTitleLabel = new JLabel("Admin Main");
        adminTitleLabel.setForeground(new Color(82, 82, 82));
        adminTitleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 23));
        adminTitleLabel.setBounds(12, 10, 150, 37);
        mainPanel.add(adminTitleLabel);


        ////////////////////////////////////////////////////////////////////////////////
        clientList.addMouseListener(new DoubleClickListener(() -> {
            System.out.println("------- 유저 더블 클릭 함");
            SubmarineServer.Client client = clientList.getSelectedValue().getClient();
            if (client != null) {
                UserDetailDialog userDetailDialog = new UserDetailDialog(client,filterChatList(client.getId()));
            }
        }));


        gameRoomList.addMouseListener(new DoubleClickListener(() -> {
            System.out.println("------- 방 더블 클릭 함");
            GameRoom selectGameRoom = gameRoomList.getSelectedValue();
            if (selectGameRoom != null) {
                ArrayList<SubmarineServer.Client> clients = gameRoomClientMap.get(selectGameRoom.getId());
                GameRoomDetailDialog gameRoomDetailDialog = new GameRoomDetailDialog(selectGameRoom,clients);
            }
        }));

        gameRecordList.addMouseListener(new DoubleClickListener(() -> {
            System.out.println("------- 경기 기록 더블 클릭 함");
            GameRoom gameRoom = gameRecordList.getSelectedValue().getGameRoom();
            if (gameRoom != null) {
                ArrayList<GameRecord> gameRecordList = roomRecordListMap.get(gameRoom);
                GameRecordDetailDialog gameRecordDetailDialog = new GameRecordDetailDialog(gameRoom,gameRecordList);
            }
        }));
        ////////////////////////////////////////////////////////////////////////////////







        setBounds(200,200,630,547);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private ArrayList<ChatInfo> filterChatList(long userId) {
        ArrayList<ChatInfo> chatList = new ArrayList<>();
        if (mainChatList != null) {
            for (ChatInfo chatInfo : mainChatList) {
                if (chatInfo.getWriterId() == userId) chatList.add(chatInfo);
            }
        }
        return chatList;
    }

    public void updateRoomList(ArrayList<GameRoom> gameRoom) {
        gameRoomListModel.clear();
        for(GameRoom g : gameRoom) {
            gameRoomListModel.addElement(g);
        }
    }

    public void addClientList(SubmarineServer.Client c) {
        clientListModel.addElement(new UserInfoPanel(c));
    }

    public void removeClientList(int index){
        clientListModel.remove(index);
    }

    public void updateClientList(Vector<SubmarineServer.Client> clients){
        clientListModel.clear();
        for(SubmarineServer.Client client : clients){
            clientListModel.addElement(new UserInfoPanel(client));
        }
    }

    public void updateRecord(Map<GameRoom, ArrayList<GameRecord>> roomRecordListMap) {
        this.roomRecordListMap = roomRecordListMap;
        gameRecordListModel.clear();
        for (GameRoom room : roomRecordListMap.keySet()) {
            gameRecordListModel.addElement(new GameRecordPanel(room));
        }
    }


    public void updateMainChat(ArrayList<ChatInfo> mainChatList) {
        this.mainChatList = mainChatList;
    }

    public void updateRoomChat(Map<Long, ArrayList<ChatInfo>> roomChatListMap) {
        this.roomChatListMap = roomChatListMap;
    }
}
