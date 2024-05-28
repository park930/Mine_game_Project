package MineGame;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import room.GameRoom;
import java.awt.Color;

public class RecordInUserPanel extends JPanel {

	private GameRecord gameRecord;
	
	
	public RecordInUserPanel(GameRecord gr) {

		this.gameRecord = gr;

		setLayout(null);

		JLabel userIdResult = new JLabel(gameRecord.getUser().getId()+"");
		userIdResult.setFont(new Font("Arial", Font.PLAIN, 10));
		userIdResult.setBounds(23, 1, 69, 19);
		add(userIdResult);

		JLabel userNameResult = new JLabel(gameRecord.getUser().getUserName());
		userNameResult.setFont(new Font("Arial", Font.PLAIN, 10));
		userNameResult.setBounds(138, 1, 63, 19);
		add(userNameResult);
		setPreferredSize(new Dimension(424, 21));
		
		JLabel userIdLabel = new JLabel("ID");
		userIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userIdLabel.setFont(new Font("Arial", Font.BOLD, 10));
		userIdLabel.setBounds(2, 1, 14, 19);
		add(userIdLabel);
		
		JLabel userNameLabel = new JLabel("Name");
		userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userNameLabel.setFont(new Font("Arial", Font.BOLD, 10));
		userNameLabel.setBounds(97, 1, 33, 19);
		add(userNameLabel);
		
		JLabel inGameInfoLabel = new JLabel("Trial");
		inGameInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inGameInfoLabel.setFont(new Font("Arial", Font.BOLD, 10));
		inGameInfoLabel.setBounds(212, 1, 40, 19);
		add(inGameInfoLabel);
		
		JLabel userTrialResult = new JLabel(gameRecord.getChoiceTotal()+"");
		userTrialResult.setFont(new Font("Arial", Font.PLAIN, 10));
		userTrialResult.setBounds(252, 1, 36, 19);
		add(userTrialResult);
		
		JLabel stateLabel = new JLabel();
		if (gameRecord.isWin()) stateLabel.setText("Win");
		else stateLabel.setText("Lose");
		stateLabel.setForeground(new Color(0, 128, 192));
		stateLabel.setFont(new Font("Arial", Font.BOLD, 10));
		stateLabel.setBounds(372, 1, 50, 19);
		add(stateLabel);
		
		JLabel lblFind = new JLabel("Find");
		lblFind.setHorizontalAlignment(SwingConstants.CENTER);
		lblFind.setFont(new Font("Arial", Font.BOLD, 10));
		lblFind.setBounds(292, 1, 41, 19);
		add(lblFind);
		
		JLabel userFindResult = new JLabel(gameRecord.getFindMine()+"");
		userFindResult.setFont(new Font("Arial", Font.PLAIN, 10));
		userFindResult.setBounds(328, 1, 36, 19);
		add(userFindResult);
	}

	public GameRecord getGameRecord(){
		return gameRecord;
	}



}

