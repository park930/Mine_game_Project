package MineGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import MineGame.listCallRenderer.DoubleClickListener;
import MineGame.listCallRenderer.RecordinUserListCellRenderer;
import room.GameRoom;
import java.awt.Color;

public class GameRecordDetailDialog extends JDialog {

	private JList<RecordInUserPanel> userList;
    private DefaultListModel<RecordInUserPanel> userListModel;
    private ArrayList<SubmarineServer.Client> clients;
    
    
    public GameRecordDetailDialog(GameRoom gameRoom,ArrayList<GameRecord> gameRecordList) {
        this.clients = clients;
        System.out.println("방 상세 다이어로그 생성");


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);


        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setBounds(100, 100, 456, 334);
        setVisible(true);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 445, 342);
        mainPanel.add(panel);
        panel.setLayout(null);

        System.out.println("여기1");
        JLabel roomIdLabel = new JLabel("Room Id");
        roomIdLabel.setFont(new Font("Arial", Font.BOLD, 13));
        roomIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
        roomIdLabel.setBounds(12, 10, 95, 32);
        panel.add(roomIdLabel);

        JLabel RoomNameLabel = new JLabel("Map Size");
        RoomNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        RoomNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        RoomNameLabel.setBounds(12, 80, 95, 32);
        panel.add(RoomNameLabel);

        System.out.println("여기2");
        JLabel mapSizeResult = new JLabel(gameRoom.getMapSize()+"x"+gameRoom.getMapSize());
        mapSizeResult.setBounds(119, 80, 64, 32);
        panel.add(mapSizeResult);

        System.out.println("여기3");
        JLabel roomIdResult = new JLabel(gameRoom.getId() + "");
        roomIdResult.setBounds(119, 10, 174, 32);
        panel.add(roomIdResult);


        System.out.println("여기5");
        System.out.println("여기6");

        JButton chatLogButton = new JButton("Chat Log");
        chatLogButton.setBackground(new Color(255, 255, 255));
        chatLogButton.setBounds(12, 248, 149, 40);
        panel.add(chatLogButton);

        userListModel = new DefaultListModel<>();
        updateUserList(gameRecordList);

        userList = new JList<>(userListModel);
        userList.setCellRenderer(new RecordinUserListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBounds(12, 122, 423, 116);
        panel.add(scrollPane);

        JLabel mineNumLabel = new JLabel("Mine Num");
        mineNumLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mineNumLabel.setFont(new Font("Arial", Font.BOLD, 13));
        mineNumLabel.setBounds(240, 80, 95, 32);
        panel.add(mineNumLabel);

        JLabel mineNumResult = new JLabel(gameRoom.getMineNum()+"");
        mineNumResult.setBounds(347, 80, 45, 32);
        panel.add(mineNumResult);
        
        JLabel roomNameLabel = new JLabel("Room Name");
        roomNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        roomNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        roomNameLabel.setBounds(12, 43, 95, 32);
        panel.add(roomNameLabel);
        
        JLabel roomNameResult = new JLabel(gameRoom.getRoomName());
        roomNameResult.setBounds(119, 43, 174, 32);
        panel.add(roomNameResult);

        ////////////////////////////////////////////////////////////////////////
        userList.addMouseListener(new DoubleClickListener(() -> {
            GameRecord gameRecord = userList.getSelectedValue().getGameRecord();
            if (gameRecord != null) {
                UserDetailDialog userDetailDialog = new UserDetailDialog(SubmarineServer.findClient(gameRecord.getUser().getId()),SubmarineServer.filterUserChatList(gameRecord.getUser().getId()));
            }
        }));
        ////////////////////////////////////////////////////////////

      setVisible(true);
      setLocationRelativeTo(null);
      getContentPane().setLayout(new BorderLayout());
      getContentPane().add(mainPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void updateUserList(ArrayList<GameRecord> records) {
    	userListModel.clear();
        for(GameRecord gameRecord : records){
            userListModel.addElement(new RecordInUserPanel(gameRecord));
        }
    }
}