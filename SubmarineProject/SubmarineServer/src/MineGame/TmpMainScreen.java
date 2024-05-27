package MineGame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import MineGame.listCallRenderer.DoubleClickListener;
import MineGame.listCallRenderer.GameRecordPanelListCellRenderer;
import MineGame.listCallRenderer.UserPanelListCellRenderer;
import room.GameRoom;
import room.User;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class TmpMainScreen extends JFrame {

   
   public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
         public void run() {
            try {
//               TmpMainScreen frame = new TmpMainScreen();
//               frame.setVisible(true);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      });
   }

   
//	private JList<SubmarineServer.Client> clientList;
//    private DefaultListModel<SubmarineServer.Client> clientListModel;

    
	private JList<UserInfoPanel> clientList;
	private DefaultListModel<UserInfoPanel> clientListModel;

    private JList<GameRecordPanel> gameRecordList;

    private DefaultListModel<GameRecordPanel> gameRecordListModel;

    private JList<GameRoom> gameRoomList;
    private DefaultListModel<GameRoom> gameRoomListModel;

    private ArrayList<GameRoom> roomList;

    private java.util.Map<Long, ArrayList<SubmarineServer.Client>> gameRoomClientMap;

    public TmpMainScreen(java.util.Map<Long, ArrayList<SubmarineServer.Client>> gameRoomClientMap) {
        this.gameRoomClientMap = gameRoomClientMap;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JPanel centerPanel = new JPanel();
        centerPanel.setBounds(239, 10, 736, 466);
        centerPanel.setOpaque(false);
        JPanel westPanel = new JPanel();
        westPanel.setBounds(3, 53, 236, 423);
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
//        JButton jb3 = new JButton("East");
        JButton jb4 = new JButton("West");
        JButton jb5 = new JButton("Center");


        JScrollPane clientScrollPane = new JScrollPane(clientList);
        clientScrollPane.setBounds(12, 31, 220, 382);
        JScrollPane roomScrollPane = new JScrollPane(gameRoomList);
        roomScrollPane.setBounds(12, 77, 318, 380);
        JScrollPane recordScrollPane = new JScrollPane(gameRecordList);
        recordScrollPane.setBounds(412, 77, 137, 162);
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
        roomListLabel.setBounds(12, 54, 67, 18);
        centerPanel.add(roomListLabel);
        
        JLabel gameRecordLabel = new JLabel("Game Record");
        gameRecordLabel.setForeground(new Color(82, 82, 82));
        gameRecordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gameRecordLabel.setBounds(412, 54, 95, 18);
        centerPanel.add(gameRecordLabel);


        // 而⑦뀒 씠 꼫瑜   봽 젅 엫 뿉  삱由 .
        getContentPane().add(mainPanel);
        
        JLabel adminTitleLabel = new JLabel("Admin Main");
        adminTitleLabel.setForeground(new Color(82, 82, 82));
        adminTitleLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 23));
        adminTitleLabel.setBounds(7, 3, 150, 37);
        mainPanel.add(adminTitleLabel);


        ////////////////////////////////////////////////////////////////////////////////
        clientList.addMouseListener(new DoubleClickListener(() -> {
            System.out.println("------- 유저 더블 클릭 함");
            SubmarineServer.Client client = clientList.getSelectedValue().getClient();
            if (client != null) {
                UserDetailDialog userDetailDialog = new UserDetailDialog(client);
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
        ////////////////////////////////////////////////////////////////////////////////







        setBounds(200,200,1019,523);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
        gameRecordListModel.clear();
        for (GameRoom room : roomRecordListMap.keySet()) {
            gameRecordListModel.addElement(new GameRecordPanel(room));
        }
    }
}
