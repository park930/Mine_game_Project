package MineGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import room.User;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Map;

public class UserDetailDialog extends JDialog {

    private SubmarineServer.Client user;
    public UserDetailDialog(SubmarineServer.Client client, ArrayList<ChatInfo> chatList) {
        this.user = client;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 490, 150);
        mainPanel.add(panel);
        panel.setLayout(null);
        
        JLabel userNameLabel = new JLabel("ID");
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userNameLabel.setBounds(74, 40, 61, 32);
        panel.add(userNameLabel);
        
        JLabel userIconLabel = new JLabel("");
        userIconLabel.setBounds(18, 28, 50, 50);
        userIconLabel.setIcon(new ImageIcon(new ImageIcon(UserDetailDialog.class.getResource("/MineGame/icon/roundUserImg.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        panel.add(userIconLabel);
        
        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Arial", Font.BOLD, 13));
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setBounds(74, 10, 61, 32);
        panel.add(lblName);
        
        JLabel lblState = new JLabel("State");
        lblState.setHorizontalAlignment(SwingConstants.CENTER);
        lblState.setFont(new Font("Arial", Font.BOLD, 13));
        lblState.setBounds(74, 70, 61, 32);
        panel.add(lblState);
        
        JLabel userNameLabel_1_1 = new JLabel(user.getUserName());
        userNameLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
        userNameLabel_1_1.setBounds(144, 10, 104, 32);
        panel.add(userNameLabel_1_1);
        
        JLabel userIDLabel_3 = new JLabel(user.getId()+"");
        userIDLabel_3.setFont(new Font("Arial", Font.PLAIN, 12));
        userIDLabel_3.setBounds(144, 40, 104, 32);
        panel.add(userIDLabel_3);
        
        JLabel userStateLabel_2_1 = new JLabel("");
        userStateLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 12));
        if(user.inGame) userStateLabel_2_1.setText("in Game");
        else userStateLabel_2_1.setText("waiting");
        userStateLabel_2_1.setBounds(144, 70, 104, 32);
        panel.add(userStateLabel_2_1);
        
        JLabel userIPLabel = new JLabel("IP");
        userIPLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userIPLabel.setFont(new Font("Arial", Font.BOLD, 13));
        userIPLabel.setBounds(276, 10, 61, 32);
        panel.add(userIPLabel);
        
        JLabel userIPLabel_1 = new JLabel(user.socket.getInetAddress()+"");
        userIPLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
        userIPLabel_1.setBounds(346, 10, 131, 32);
        panel.add(userIPLabel_1);
        
        JButton gameRecordButton = new JButton("GameRecord");
        gameRecordButton.setBackground(new Color(211, 211, 211));
        gameRecordButton.setFont(new Font("Arial", Font.BOLD, 12));
        gameRecordButton.setBounds(9, 103, 149, 40);
        panel.add(gameRecordButton);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(211, 211, 211));
        btnDelete.setFont(new Font("Arial", Font.BOLD, 12));
        btnDelete.setBounds(170, 103, 149, 40);
        panel.add(btnDelete);
        
        JButton chatLogButton = new JButton("Chat Log");
        chatLogButton.setBackground(new Color(211, 211, 211));
        chatLogButton.setFont(new Font("Arial", Font.BOLD, 12));
        chatLogButton.setBounds(328, 103, 149, 40);
        panel.add(chatLogButton);



        JLabel rateLabel = new JLabel("Statistics");
        rateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rateLabel.setFont(new Font("Arial", Font.BOLD, 13));
        rateLabel.setBounds(276, 40, 61, 32);
        panel.add(rateLabel);

        JLabel userIPLabel_1_1 = new JLabel(user.getStatistics());
        userIPLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
        userIPLabel_1_1.setBounds(346, 40, 140, 32);
        panel.add(userIPLabel_1_1);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        chatLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserChatLogDialog userChatLogDialog = new UserChatLogDialog(user,chatList);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("해당 유저의 인게임 상태:"+user.inGame);
                if (user.inGame) {
                    System.out.println("  forceQuitInGameUser 보냄");
                    user.sendCommand("forceQuitInGameUser",null);
                } else {
                    System.out.println("  forceQuitWaitingUser 보냄");
                    user.sendCommand("forceQuitWaitingUser",null);
                }
            }
        });

        gameRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 filterClientGameRecord(user.getId());
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        setBounds(100, 100, 508, 193);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void filterClientGameRecord(long id) {
        Map<Long, ArrayList<GameRecord>> gameRecordClientsMap = SubmarineServer.getGameRecordClientsMap();

        if (!gameRecordClientsMap.containsKey(id)){
            showInfo("게임 기록이 없습니다.");
        } else {
            ArrayList<GameRecord> gameRecords = gameRecordClientsMap.get(id);
            
            // 유저의 게임기록 화면 생성
            UserGameRecordDialog userGameRecordDialog = new UserGameRecordDialog(user,gameRecords);
        }
    }

    public void showInfo(String s) {
        JOptionPane.showMessageDialog(this, s, "알림", JOptionPane.INFORMATION_MESSAGE);
    }

}



