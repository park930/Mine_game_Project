package MineGame;

import javax.swing.JPanel;

import MineGame.SubmarineServer.Client;
import room.User;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;

public class UserInfoPanel extends JPanel {
	private JLabel userNameLabel;
	private Client user;

	public UserInfoPanel(Client client) {
		this.user = client;
		setLayout(null);
		
		JLabel uesrIdLabel = new JLabel("ID:"+user.getId());
		uesrIdLabel.setFont(new Font("Arial", Font.PLAIN, 10));
		uesrIdLabel.setBounds(1, 2, 83, 23);
		add(uesrIdLabel);
		
		userNameLabel = new JLabel("Name:"+user.getUserName());
		userNameLabel.setFont(new Font("Arial", Font.PLAIN, 10));
		userNameLabel.setBounds(90, 2, 88, 23);
		add(userNameLabel);
		
		JLabel gameStateLabel = new JLabel("");
		if(user.inGame) gameStateLabel.setIcon(new ImageIcon((new ImageIcon(UserDetailDialog.class.getResource("/MineGame/icon/inGameButton.png"))).getImage().getScaledInstance(19,19, Image.SCALE_SMOOTH)));
		else gameStateLabel.setIcon(new ImageIcon((new ImageIcon(UserDetailDialog.class.getResource("/MineGame/icon/onlineButton.png"))).getImage().getScaledInstance(19, 19, Image.SCALE_SMOOTH)));
		gameStateLabel.setBounds(180, 2, 23, 23);
		add(gameStateLabel);
        setPreferredSize(new Dimension(208, 28));
	}

	public Client getClient(){
		return user;
	}
}
