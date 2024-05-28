package MineGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import room.User;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;

public class UserDetailDialog extends JDialog {

    public static void main(String[] args) {
        try {
//            UserDetailDialog dialog = new UserDetailDialog(new User());
//            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UserDetailDialog(SubmarineServer.Client user, ArrayList<ChatInfo> chatList) {

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
        userIconLabel.setBounds(9, 27, 50, 50);
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
        gameRecordButton.setFont(new Font("Arial", Font.BOLD, 12));
        gameRecordButton.setBounds(9, 103, 149, 40);
        panel.add(gameRecordButton);
        
        JButton btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Arial", Font.BOLD, 12));
        btnDelete.setBounds(170, 103, 149, 40);
        panel.add(btnDelete);
        
        JButton chatLogButton = new JButton("Chat Log");
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


        setBounds(100, 100, 508, 193);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}



