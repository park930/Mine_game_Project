package mineGame.Screen.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import mineGame.GameRecord;
import mineGame.ListCallRenderer.RecordListCellRenderer;
import mineGame.ListCallRenderer.RoomListCellRenderer;
import mineGame.User;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class GameRecordDialog extends JDialog {

	private BackgroundPanel bp;
	
	private JList<GameRecordPanel> gameRecordList;
    private DefaultListModel<GameRecordPanel> gameRecordListModel;
    private JPanel userInfoPanel;
    private JLabel winRatingLabel;
    private JLabel lblNewLabel;
    private JLabel totalLabel;
    private JLabel winLabel;
    private JLabel loseLabel;
    private JLabel totalValue;
    private JLabel winValue;
    private JLabel loseValue;
    private JLabel titleLogo;
	
	public GameRecordDialog(ArrayList<GameRecord> recordList, User user) {
		bp = new BackgroundPanel("/mineGame/Screen/icon/RecordBackground.png");
		bp.setBounds(0,0,101,100);
		getContentPane().add(bp);
		
		gameRecordListModel = new DefaultListModel<>();
		bp.setLayout(null);
		gameRecordList = new JList<>(gameRecordListModel);
		gameRecordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gameRecordList.setCellRenderer(new RecordListCellRenderer());
		
		JScrollPane recordScrollPane = new JScrollPane(gameRecordList);
		recordScrollPane.setBounds(252, 10, 432, 416);
		bp.add(recordScrollPane);
		
		userInfoPanel = new JPanel();
		userInfoPanel.setBounds(12, 0, 228, 426);
		userInfoPanel.setOpaque(false);
		bp.add(userInfoPanel);
		userInfoPanel.setLayout(null);
		
		winRatingLabel = new JLabel(""+String.format("%.2f",user.getRating())+"%");
		winRatingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winRatingLabel.setForeground(new Color(255, 255, 255));
		winRatingLabel.setFont(new Font("Leelawadee UI", Font.BOLD, 51));
		winRatingLabel.setBounds(0, 112, 228, 68);
		userInfoPanel.add(winRatingLabel);
		
		lblNewLabel = new JLabel("Winning rate");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lblNewLabel.setBounds(0, 89, 228, 28);
		userInfoPanel.add(lblNewLabel);
		
		
		LineSeparator lineSeparator = new LineSeparator();
		lineSeparator.setBounds(71, 112, 90, 3);
		userInfoPanel.add(lineSeparator);
		lineSeparator.setBackground(new Color(255, 255, 255));
		
		
		
		
		JLabel nowIdLabel = new JLabel(user.getUserName());
		nowIdLabel.setBounds(17, 202, 202, 34);
		userInfoPanel.add(nowIdLabel);
		nowIdLabel.setOpaque(true);
		nowIdLabel.setBackground(SystemColor.activeCaption);
		nowIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nowIdLabel.setForeground(new Color(255, 255, 255));
		nowIdLabel.setFont(new Font("Arial", Font.BOLD, 23));
		
		JLabel userIdLabel = new JLabel("(id:"+ user.getId()+")");
		userIdLabel.setBounds(17, 245, 202, 18);
		userInfoPanel.add(userIdLabel);
		userIdLabel.setOpaque(true);
		userIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userIdLabel.setForeground(Color.WHITE);
		userIdLabel.setFont(new Font("Arial", Font.BOLD, 11));
		userIdLabel.setBackground(SystemColor.inactiveCaption);
		
		totalLabel = new JLabel("Total");
		totalLabel.setHorizontalAlignment(SwingConstants.LEFT);
		totalLabel.setForeground(new Color(255, 255, 255));
		totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
		totalLabel.setBounds(47, 282, 73, 29);
		userInfoPanel.add(totalLabel);
		
		winLabel = new JLabel("Win");
		winLabel.setHorizontalAlignment(SwingConstants.LEFT);
		winLabel.setForeground(new Color(255, 255, 255));
		winLabel.setFont(new Font("Arial", Font.BOLD, 18));
		winLabel.setBounds(47, 314, 73, 29);
		userInfoPanel.add(winLabel);
		
		loseLabel = new JLabel("Lose");
		loseLabel.setHorizontalAlignment(SwingConstants.LEFT);
		loseLabel.setForeground(new Color(255, 255, 255));
		loseLabel.setFont(new Font("Arial", Font.BOLD, 18));
		loseLabel.setBounds(47, 346, 73, 29);
		userInfoPanel.add(loseLabel);
		
		totalValue = new JLabel(user.getTotal()+"");
		totalValue.setHorizontalAlignment(SwingConstants.LEFT);
		totalValue.setForeground(Color.WHITE);
		totalValue.setFont(new Font("Arial", Font.BOLD, 18));
		totalValue.setBounds(125, 282, 73, 29);
		userInfoPanel.add(totalValue);
		
		winValue = new JLabel(user.getWin()+"");
		winValue.setHorizontalAlignment(SwingConstants.LEFT);
		winValue.setForeground(Color.WHITE);
		winValue.setFont(new Font("Arial", Font.BOLD, 18));
		winValue.setBounds(125, 314, 73, 29);
		userInfoPanel.add(winValue);
		
		loseValue = new JLabel(user.getLose()+"");
		loseValue.setHorizontalAlignment(SwingConstants.LEFT);
		loseValue.setForeground(Color.WHITE);
		loseValue.setFont(new Font("Arial", Font.BOLD, 18));
		loseValue.setBounds(125, 346, 73, 29);
		userInfoPanel.add(loseValue);
		
		
		titleLogo = new JLabel("");
		titleLogo.setIcon(new ImageIcon((new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/gameRecordLogo.png"))).getImage().getScaledInstance(219, 38, Image.SCALE_SMOOTH)));
		titleLogo.setBounds(-2, 1, 219, 44);
		userInfoPanel.add(titleLogo);
		
		
		for(GameRecord record : recordList) {
			gameRecordListModel.addElement(new GameRecordPanel(record));
		}
		
		
		setSize(712, 475);
		setVisible(true);
	}

}
