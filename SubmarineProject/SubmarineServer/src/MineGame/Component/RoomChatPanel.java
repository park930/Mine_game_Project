package MineGame.Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import room.User;

import javax.swing.SwingConstants;

public class RoomChatPanel extends JPanel {

    private final JLabel roomIdLabel;
    private final JLabel roomIdResult;
    private final JPanel bp;
    private long roomId;

    public RoomChatPanel(long roomId) {
        this.roomId = roomId;

        bp = new JPanel();
        bp.setBackground(new Color(255, 255, 255));
        bp.setBounds(0, 0, 280, 23);
        add(bp);
        bp.setLayout(null);
        //    	mainPanel.setBorder(new RoundedBorder(7, 2, Color.white, 2));

        roomIdLabel = new JLabel("Room ID:");
        roomIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roomIdLabel.setBounds(0, 1, 58, 23);
        bp.add(roomIdLabel);
        roomIdLabel.setForeground(new Color(0, 0, 0));
        roomIdLabel.setFont(new Font("Arial", Font.BOLD, 12));

        roomIdResult = new JLabel(roomId + "");
        roomIdResult.setBounds(58, 1, 217, 23);
        bp.add(roomIdResult);
        roomIdResult.setForeground(new Color(0, 0, 0));
        roomIdResult.setFont(new Font("Arial", Font.BOLD, 11));
        roomIdResult.setIcon(null);

        setBackground(new Color(23, 30, 43));
        setLayout(null);

        setOpaque(false);
        setPreferredSize(new Dimension(280, 23));

    }

    public long getRoomId(){
        return roomId;
    }
}