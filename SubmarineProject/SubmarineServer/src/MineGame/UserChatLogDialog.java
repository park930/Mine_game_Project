package MineGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import MineGame.SubmarineServer.Client;

import javax.swing.JScrollPane;

public class UserChatLogDialog extends JDialog {


	private JList<ChatInfo> chatList;
	private DefaultListModel<ChatInfo> chatListModel;
	
	
	public UserChatLogDialog(Client user,ArrayList<ChatInfo> chatInfoList) {
		JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);


        setBounds(100, 100, 626, 337);
        setVisible(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        JLabel idLabel = new JLabel("ID");
        idLabel.setBounds(19, 16, 61, 32);
        mainPanel.add(idLabel);
        idLabel.setFont(new Font("Arial", Font.BOLD, 13));
        idLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel userIdResult = new JLabel(user.getId()+"");
        userIdResult.setBounds(89, 16, 104, 32);
        mainPanel.add(userIdResult);
        userIdResult.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel userNameResult = new JLabel(user.getUserName());
        userNameResult.setBounds(275, 16, 104, 32);
        mainPanel.add(userNameResult);
        userNameResult.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(205, 16, 61, 32);
        mainPanel.add(nameLabel);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setBounds(391, 16, 61, 32);
        mainPanel.add(ipLabel);
        ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ipLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        JLabel userIPResult = new JLabel(user.socket.getInetAddress()+"");
        userIPResult.setBounds(461, 16, 131, 32);
        mainPanel.add(userIPResult);
        userIPResult.setFont(new Font("Arial", Font.PLAIN, 12));
        
        

        
        chatListModel = new DefaultListModel<>();
        updateChatList(chatInfoList);
        
        chatList = new JList<>(chatListModel);

        JScrollPane scrollPane = new JScrollPane(chatList);
        scrollPane.setBounds(12, 55, 580, 226);
        mainPanel.add(scrollPane);
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
