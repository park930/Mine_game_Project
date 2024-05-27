package MineGame;

import javax.swing.*;

import room.GameRoom;

import java.awt.*;

public class GameRecordPanel extends JPanel {


	private GameRoom gameRoom;
	
	public GameRecordPanel(GameRoom gr) {

		this.gameRoom = gr;

		setLayout(null);

		JLabel roomId = new JLabel(gameRoom.getId()+"");
		roomId.setFont(new Font("Arial", Font.PLAIN, 10));
		roomId.setBounds(23, 1, 76, 19);
		add(roomId);

		JLabel roomName = new JLabel(gameRoom.getRoomName());
		roomName.setFont(new Font("Arial", Font.PLAIN, 10));
		roomName.setBounds(138, 1, 63, 19);
		add(roomName);
		setPreferredSize(new Dimension(343, 21));
		
		JLabel roomIdLabel = new JLabel("ID");
		roomIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roomIdLabel.setFont(new Font("Arial", Font.BOLD, 10));
		roomIdLabel.setBounds(2, 1, 14, 19);
		add(roomIdLabel);
		
		JLabel roomNameLabel = new JLabel("Name");
		roomNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		roomNameLabel.setFont(new Font("Arial", Font.BOLD, 10));
		roomNameLabel.setBounds(104, 1, 33, 19);
		add(roomNameLabel);
		
		JLabel createrLabel = new JLabel("Creater");
		createrLabel.setHorizontalAlignment(SwingConstants.CENTER);
		createrLabel.setFont(new Font("Arial", Font.BOLD, 10));
		createrLabel.setBounds(212, 1, 50, 19);
		add(createrLabel);
		
		JLabel roomCreater = new JLabel(gameRoom.getChairmanId()+"");
		roomCreater.setFont(new Font("Arial", Font.PLAIN, 10));
		roomCreater.setBounds(266, 1, 75, 19);
		add(roomCreater);
	}

	public GameRoom getGameRoom(){
		return gameRoom;
	}



}
