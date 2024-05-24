package mineGame.Screen.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import mineGame.GameRoom;
import mineGame.Screen.MainScreen;
import mineGame.Screen.RoomScreen;
import mineGame.SubmarineClient;
import mineGame.User;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInfoDialog extends JDialog {

	private BackgroundPanel bp;
	private JTextField textField;

	
	public UserInfoDialog(User user) {
		bp = new BackgroundPanel("/mineGame/Screen/icon/infoBackground.png");
		getContentPane().add(bp);
		bp.setLayout(null);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(255, 255, 255));
		bottomPanel.setBounds(0, 170, 266, 91);
		bp.add(bottomPanel);
		bottomPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(88, 16, 133, 28);
		bottomPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("이름");
		lblNewLabel.setForeground(new Color(95, 95, 95));
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblNewLabel.setBounds(44, 16, 43, 28);
		bottomPanel.add(lblNewLabel);
		
		JLabel infoIconLabel = new JLabel("");
		infoIconLabel.setIcon(new ImageIcon(new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/roundUserImg.png")).getImage().getScaledInstance(83, 83, Image.SCALE_SMOOTH)));
		infoIconLabel.setBounds(92, 15, 83, 83);
		bp.add(infoIconLabel);
		
		JLabel nowIdLabel = new JLabel(user.getUserName());
		nowIdLabel.setOpaque(true);
		nowIdLabel.setBackground(new Color(78, 87, 92));
		nowIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nowIdLabel.setForeground(new Color(255, 255, 255));
		nowIdLabel.setFont(new Font("Arial", Font.BOLD, 19));
		nowIdLabel.setBounds(58, 106, 154, 26);
		bp.add(nowIdLabel);
		
		JLabel userIdLabel = new JLabel("(id:"+ user.getId()+")");
		userIdLabel.setOpaque(true);
		userIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userIdLabel.setForeground(Color.WHITE);
		userIdLabel.setFont(new Font("Arial", Font.BOLD, 11));
		userIdLabel.setBackground(new Color(104, 117, 123));
		userIdLabel.setBounds(58, 138, 154, 18);
		bp.add(userIdLabel);
		
		

        JLabel okButton = new JLabel("");
        okButton.setBounds(46, 54, 179, 30);
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	//어떤거 할지
				String newNickName = textField.getText();
				SubmarineClient.sendCommand("checkNewNickName",new User(newNickName,user.getId()));
            }
        });
        okButton.setIcon(new ImageIcon(new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/updateButton.png")).getImage().getScaledInstance(179, 25, Image.SCALE_SMOOTH)));
		bottomPanel.add(okButton);


		setSize(280, 296);

	}

	public void showInfo(String string) {
		JOptionPane.showMessageDialog(this, string, "알림", JOptionPane.INFORMATION_MESSAGE);
	}

	public void clearTextField(){
		textField.setText("");
	}
}
