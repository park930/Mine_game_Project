package MineGame.Component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import MineGame.ChatInfo;

public class RoomChatDetailDialog extends JDialog {

	private JList<ChatInfo> chatList;
	private DefaultListModel<ChatInfo> chatListModel;
	private JLabel idLabel;
	
	
	public RoomChatDetailDialog(long roomId,ArrayList<ChatInfo> chatInfoList) {
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);


        setBounds(100, 100, 626, 385);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        idLabel = new JLabel("Room ID");
        idLabel.setBounds(15, 55, 82, 32);
        mainPanel.add(idLabel);
        idLabel.setFont(new Font("Arial", Font.BOLD, 13));
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel userIdResult = new JLabel(roomId+"");
        userIdResult.setBounds(109, 55, 231, 32);
        mainPanel.add(userIdResult);
        userIdResult.setFont(new Font("Arial", Font.PLAIN, 12));
        
        

        
        chatListModel = new DefaultListModel<>();
        updateChatList(chatInfoList);
        
        chatList = new JList<>(chatListModel);

        JScrollPane scrollPane = new JScrollPane(chatList);
        scrollPane.setBounds(12, 84, 580, 256);
        mainPanel.add(scrollPane);
        
        JLabel lblRoomChatLog = new JLabel("Room Chat Log");
        lblRoomChatLog.setHorizontalAlignment(SwingConstants.LEFT);
        lblRoomChatLog.setFont(new Font("Arial", Font.BOLD, 20));
        lblRoomChatLog.setBounds(15, 10, 215, 32);
        mainPanel.add(lblRoomChatLog);
        mainPanel.revalidate();
        mainPanel.repaint();
	}


	private void updateChatList(ArrayList<ChatInfo> chatInfoList) {
		chatListModel.clear();
		for(ChatInfo chat : chatInfoList) {
			chatListModel.addElement(chat);
		}
		
	}
}
