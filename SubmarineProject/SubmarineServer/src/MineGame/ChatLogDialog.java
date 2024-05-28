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

import MineGame.Component.RoomChatDetailDialog;
import MineGame.Component.RoomChatPanel;
import MineGame.listCallRenderer.ChatLogRoomListCellRenderer;
import MineGame.listCallRenderer.DoubleClickListener;
import room.GameRoom;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;

public class ChatLogDialog extends JDialog {

    private JList<ChatInfo> mainChatJList;
    private DefaultListModel<ChatInfo> mainChatListModel;

    private JList<RoomChatPanel> roomChatJList;
    private DefaultListModel<RoomChatPanel> roomChatListModel;
    private Map<Long, ArrayList<ChatInfo>> roomChatListMap;


    public ChatLogDialog(ArrayList<ChatInfo> mainChatList) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        roomChatListMap = SubmarineServer.getRoomChatMap();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setBounds(100, 100, 671, 555);
        setVisible(true);
        setLocationRelativeTo(null);
        mainChatListModel = new DefaultListModel<>();
        roomChatListModel = new DefaultListModel<>();
        updateMainChatList(mainChatList);
        updateRoomChatList();
        ////////////////////////////////////////////////////////////

        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        mainChatJList = new JList<>(mainChatListModel);
        roomChatJList = new JList<>(roomChatListModel);
        JScrollPane mainChatScrollPane = new JScrollPane(mainChatJList);
        mainChatScrollPane.setBounds(12, 107, 305, 402);
        mainPanel.add(mainChatScrollPane);

        JLabel lblNewLabel = new JLabel("Chat Log");
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 19));
        lblNewLabel.setBounds(14, 10, 108, 37);
        mainPanel.add(lblNewLabel);

        JLabel lblMainscreenChat = new JLabel("MainScreen Chat");
        lblMainscreenChat.setForeground(new Color(71, 71, 71));
        lblMainscreenChat.setFont(new Font("Arial", Font.BOLD, 14));
        lblMainscreenChat.setBounds(15, 78, 161, 26);
        mainPanel.add(lblMainscreenChat);


        roomChatJList.setCellRenderer(new ChatLogRoomListCellRenderer());
        JScrollPane GameRoomListScrollPane = new JScrollPane(roomChatJList);
        GameRoomListScrollPane.setBounds(340, 107, 295, 402);
        mainPanel.add(GameRoomListScrollPane);

        JLabel lblGameroomChat = new JLabel("GameRoom Chat");
        lblGameroomChat.setForeground(new Color(71, 71, 71));
        lblGameroomChat.setFont(new Font("Arial", Font.BOLD, 14));
        lblGameroomChat.setBounds(343, 78, 161, 26);
        mainPanel.add(lblGameroomChat);

        ////////////////////////////////////////////////////////////////////////
        roomChatJList.addMouseListener(new DoubleClickListener(() -> {
            long roomId = roomChatJList.getSelectedValue().getRoomId();
            if (roomId != 0) {
                ArrayList<ChatInfo> roomChatList = roomChatListMap.computeIfAbsent(roomId, k -> new ArrayList<>());
                RoomChatDetailDialog roomChatDetailDialog = new RoomChatDetailDialog(roomId,roomChatList);
            }
        }));
        ////////////////////////////////////////////////////////////////////////
        
        
        
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void updateMainChatList(ArrayList<ChatInfo> chatList) {
        for (ChatInfo c : chatList) {
            mainChatListModel.addElement(c);
        }
    }

    private void updateRoomChatList() {
        for (long roomId : roomChatListMap.keySet()) {
            System.out.println("   roomChat에 기록된 방 아이디 : "+roomId);
            roomChatListModel.addElement(new RoomChatPanel(roomId));
        }
    }

}