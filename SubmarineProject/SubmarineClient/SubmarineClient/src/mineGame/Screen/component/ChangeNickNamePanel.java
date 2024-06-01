package mineGame.Screen.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import mineGame.SubmarineClient;
import mineGame.User;

public class ChangeNickNamePanel extends JDialog {

	private BackgroundPanel bp;
	private JTextField textField;

	/**
	 * Create the dialog.
	 */
	public ChangeNickNamePanel(User user) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		bp = new BackgroundPanel("/mineGame/Screen/icon/nicknameBackground.png");
		getContentPane().add(bp);
		bp.setLayout(null);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(255, 255, 255));
		bottomPanel.setBounds(1, 72, 343, 129);
		bottomPanel.setOpaque(false);
		bp.add(bottomPanel);
		bottomPanel.setLayout(null);
		
		textField = new JTextField(user.getUserName());
		textField.setFont(new Font("Arial", Font.BOLD, 13));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(74, 34, 187, 30);
		bottomPanel.add(textField);
		textField.setColumns(10);
		
		JLabel infoIconLabel = new JLabel("");
		infoIconLabel.setIcon(new ImageIcon(new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/changeNickName.png")).getImage().getScaledInstance(205, 59, Image.SCALE_SMOOTH)));
		infoIconLabel.setBounds(68, 6, 205, 59);
		bp.add(infoIconLabel);
		
		

        JLabel okButton = new JLabel("");
        okButton.setBounds(74, 78, 187, 49);
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	//어떤거 할지
				String newNickName = textField.getText();
				SubmarineClient.sendCommand("checkNewNickName",new User(newNickName,user.getId()));
            }
        });
        okButton.setIcon(new ImageIcon(new ImageIcon(UserPanel.class.getResource("/mineGame/Screen/icon/nicknameUpdateButton.png")).getImage().getScaledInstance(187, 49, Image.SCALE_SMOOTH)));
		bottomPanel.add(okButton);


		
		setSize(360, 239);

	}

	public void showInfo(String string) {
		JOptionPane.showMessageDialog(this, string, "알림", JOptionPane.INFORMATION_MESSAGE);
	}

	public void clearTextField(){
		textField.setText("");
	}

}
