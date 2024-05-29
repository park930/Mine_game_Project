package MineGame;

import MineGame.Component.UserGameRecordPanel;
import MineGame.listCallRenderer.UserGameRecordPanelListCellRenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class UserGameRecordDialog extends JDialog {


    private final JList<UserGameRecordPanel> gameRecordList;
    private final DefaultListModel<UserGameRecordPanel> gameRecordListModel;
    private ArrayList<GameRecord> gameRecords;

    public UserGameRecordDialog(SubmarineServer.Client user, ArrayList<GameRecord> grs) {
        this.gameRecords = grs;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        setBounds(100, 100, 411, 381);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        JLabel userNameLabel = new JLabel("ID");
        userNameLabel.setBounds(68, 71, 61, 32);
        mainPanel.add(userNameLabel);
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel userIconLabel = new JLabel("");
        userIconLabel.setBounds(12, 15, 50, 50);
        mainPanel.add(userIconLabel);
        userIconLabel.setIcon(new ImageIcon(new ImageIcon(UserDetailDialog.class.getResource("/MineGame/icon/roundUserImg.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(68, 10, 61, 32);
        mainPanel.add(lblName);
        lblName.setFont(new Font("Arial", Font.BOLD, 13));
        lblName.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel userNameLabel_1_1 = new JLabel(user.getUserName());
        userNameLabel_1_1.setBounds(138, 10, 104, 32);
        mainPanel.add(userNameLabel_1_1);
        userNameLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel userIDLabel_3 = new JLabel(user.getId() + "");
        userIDLabel_3.setBounds(138, 71, 104, 32);
        mainPanel.add(userIDLabel_3);
        userIDLabel_3.setFont(new Font("Arial", Font.PLAIN, 12));


        JLabel rateLabel = new JLabel("Statistics");
        rateLabel.setBounds(68, 41, 61, 32);
        mainPanel.add(rateLabel);
        rateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rateLabel.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel userIPLabel_1_1 = new JLabel(user.getStatistics());
        userIPLabel_1_1.setBounds(138, 41, 140, 32);
        mainPanel.add(userIPLabel_1_1);
        userIPLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 12));


        gameRecordListModel = new DefaultListModel<>();
        updateGameRecord(gameRecords);
        gameRecordList = new JList<>(gameRecordListModel);
        gameRecordList.setCellRenderer(new UserGameRecordPanelListCellRenderer());

        JScrollPane recordScrollPane = new JScrollPane(gameRecordList);
        recordScrollPane.setBounds(12, 129, 369, 207);
        mainPanel.add(recordScrollPane);

        JLabel gameRecordLabel = new JLabel("Game Record");
        gameRecordLabel.setForeground(new Color(82, 82, 82));
        gameRecordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gameRecordLabel.setBounds(15, 107, 95, 18);
        mainPanel.add(gameRecordLabel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showInfo(String s) {
        JOptionPane.showMessageDialog(this, s, "알림", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateGameRecord(ArrayList<GameRecord> gameRecords){
        if (gameRecords!=null) {
            gameRecordListModel.clear();
            for (GameRecord gameRecord : gameRecords) {
                gameRecordListModel.addElement(new UserGameRecordPanel(gameRecord));
            }
        }
    }
}



