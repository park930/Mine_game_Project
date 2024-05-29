package MineGame.Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import MineGame.GameRecord;

public class UserGameRecordPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private GameRecord gameRecord;
	
	public UserGameRecordPanel(GameRecord gr) {
		this.gameRecord = gr;
		setLayout(null);

		JLabel userIdResult = new JLabel(gameRecord.getRoomId()+"");
		userIdResult.setFont(new Font("Arial", Font.PLAIN, 10));
		userIdResult.setBounds(56, 1, 107, 19);
		add(userIdResult);
		setPreferredSize(new Dimension(385, 21));
		
		JLabel roomIdLabel = new JLabel("Room ID");
		roomIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roomIdLabel.setFont(new Font("Arial", Font.BOLD, 10));
		roomIdLabel.setBounds(2, 1, 50, 19);
		add(roomIdLabel);
		
		JLabel inGameInfoLabel = new JLabel("Trial");
		inGameInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inGameInfoLabel.setFont(new Font("Arial", Font.BOLD, 10));
		inGameInfoLabel.setBounds(165, 1, 40, 19);
		add(inGameInfoLabel);
		
		JLabel userTrialResult = new JLabel(gameRecord.getChoiceTotal()+"");
		userTrialResult.setFont(new Font("Arial", Font.PLAIN, 10));
		userTrialResult.setBounds(205, 1, 36, 19);
		add(userTrialResult);
		
		JLabel stateLabel = new JLabel();
		if (gameRecord.isWin()) stateLabel.setText("Win");
		else stateLabel.setText("Lose");
		stateLabel.setForeground(new Color(0, 128, 192));
		stateLabel.setFont(new Font("Arial", Font.BOLD, 10));
		stateLabel.setBounds(329, 1, 50, 19);
		add(stateLabel);
		
		JLabel lblFind = new JLabel("Find");
		lblFind.setHorizontalAlignment(SwingConstants.CENTER);
		lblFind.setFont(new Font("Arial", Font.BOLD, 10));
		lblFind.setBounds(245, 1, 41, 19);
		add(lblFind);
		
		JLabel userFindResult = new JLabel(gameRecord.getFindMine()+"");
		userFindResult.setFont(new Font("Arial", Font.PLAIN, 10));
		userFindResult.setBounds(281, 1, 36, 19);
		add(userFindResult);
	}

}
