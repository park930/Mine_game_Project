package MineGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import MineGame.Component.RoomChatDetailDialog;
import MineGame.listCallRenderer.DoubleClickListener;
import room.GameRoom;

public class GameRoomDetailDialog extends JDialog {



    private JList<SubmarineServer.Client> userList;
    private DefaultListModel<SubmarineServer.Client> userListModel;
    private ArrayList<SubmarineServer.Client> clients;
    public GameRoomDetailDialog(GameRoom gameRoom, ArrayList<SubmarineServer.Client> clients) {
        this.clients = clients;
        System.out.println("방 상세 다이어로그 생성");


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);


        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setBounds(100, 100, 513, 311);
        setVisible(true);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 10, 494, 265);
        mainPanel.add(panel);
        panel.setLayout(null);

        System.out.println("여기1");
        JLabel roomIdLabel = new JLabel("Room ID");
        roomIdLabel.setFont(new Font("Arial", Font.BOLD, 13));
        roomIdLabel.setHorizontalAlignment(SwingConstants.LEFT);
        roomIdLabel.setBounds(222, 48, 95, 32);
        panel.add(roomIdLabel);

        JLabel RoomNameLabel = new JLabel("Room Name");
        RoomNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        RoomNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        RoomNameLabel.setBounds(222, 8, 95, 32);
        panel.add(RoomNameLabel);

        JLabel gameState = new JLabel("Game State");
        gameState.setHorizontalAlignment(SwingConstants.LEFT);
        gameState.setFont(new Font("Arial", Font.BOLD, 13));
        gameState.setBounds(222, 88, 95, 32);
        panel.add(gameState);

        System.out.println("여기2");
        JLabel roomNameResult = new JLabel(gameRoom.getRoomName());
        roomNameResult.setBounds(329, 8, 161, 32);
        panel.add(roomNameResult);

        System.out.println("여기3");
        JLabel roomIdResult = new JLabel(gameRoom.getId() + "");
        roomIdResult.setBounds(329, 48, 161, 32);
        panel.add(roomIdResult);

        System.out.println("여기4");
        JLabel gameStateResult = new JLabel("");
        if (gameRoom.isStart()) gameStateResult.setText("in Game");
        else gameStateResult.setText("waiting");
        gameStateResult.setBounds(329, 88, 161, 32);
        panel.add(gameStateResult);

        System.out.println("여기5");
        JLabel chairmanLabel = new JLabel("Chairman");
        chairmanLabel.setHorizontalAlignment(SwingConstants.LEFT);
        chairmanLabel.setFont(new Font("Arial", Font.BOLD, 13));
        chairmanLabel.setBounds(222, 128, 95, 32);
        panel.add(chairmanLabel);

        JLabel chairmanResult = new JLabel(gameRoom.getMapSize()+"x"+gameRoom.getMapSize());
        chairmanResult.setBounds(329, 168, 161, 32);
        panel.add(chairmanResult);
        System.out.println("여기6");

        JButton chatLogButton = new JButton("Room Chat Log");
        chatLogButton.setBounds(8, 180, 202, 60);
        panel.add(chatLogButton);

        userListModel = new DefaultListModel<>();
        updateUserList();

        userList = new JList<>(userListModel);
        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBounds(8, 6, 202, 165);
        panel.add(scrollPane);

        JLabel mapSizeLabel = new JLabel("Map Size");
        mapSizeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mapSizeLabel.setFont(new Font("Arial", Font.BOLD, 13));
        mapSizeLabel.setBounds(222, 168, 95, 32);
        panel.add(mapSizeLabel);

        JLabel chairmanResult_1 = new JLabel(gameRoom.getChairmanId()+"");
        chairmanResult_1.setBounds(329, 128, 161, 32);
        panel.add(chairmanResult_1);

        JLabel mineNumLabel = new JLabel("Mine Num");
        mineNumLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mineNumLabel.setFont(new Font("Arial", Font.BOLD, 13));
        mineNumLabel.setBounds(222, 208, 95, 32);
        panel.add(mineNumLabel);

        JLabel chairmanResult_1_1 = new JLabel(gameRoom.getMineNum()+"");
        chairmanResult_1_1.setBounds(329, 208, 161, 32);
        panel.add(chairmanResult_1_1);

        ////////////////////////////////////////////////////////////////////////
        userList.addMouseListener(new DoubleClickListener(() -> {
            SubmarineServer.Client client = userList.getSelectedValue();
            if (client != null) {
                UserDetailDialog userDetailDialog = new UserDetailDialog(client,SubmarineServer.filterUserChatList(client.getId()));
            }
        }));

        chatLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long roomId = gameRoom.getId();
                if (roomId != 0) {
                    ArrayList<ChatInfo> roomChatList = SubmarineServer.getRoomChatMap().computeIfAbsent(roomId, k -> new ArrayList<>());
                    RoomChatDetailDialog roomChatDetailDialog = new RoomChatDetailDialog(roomId,roomChatList);
                }
            }
        });
        ////////////////////////////////////////////////////////////

      setVisible(true);
      setLocationRelativeTo(null);
      getContentPane().setLayout(new BorderLayout());
      getContentPane().add(mainPanel, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void updateUserList() {
        for(SubmarineServer.Client c : clients){
            userListModel.addElement(c);
        }
    }
}