
import room.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen extends JFrame {

    private ArrayList<JPanel> userInfoTableList;

    private JPanel mineMapPanel;
    private GameStart gameStart;

    private ArrayList<JButton> mineButtonList;

    private JLabel timerLabel;
    private JLabel remainMineLabel;
    private JLabel turnLabel;
    private HashMap<Long, JTable> userGameTableMap;
    private HashMap<Long, JLabel> turnUserMap;

    public GameScreen(GameStart gameStart) {
        this.gameStart = gameStart;
        userGameTableMap=new HashMap<>();
        turnUserMap=new HashMap<>();

        Map map = gameStart.getMap();

        setTitle("Game Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        // 메인 패널 설정
        // 3x3으로 나눈다.
        JPanel mainPanel = new JPanel(new GridLayout(3, 3, 0, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 빈 패널
        JPanel emptyPanel1 = new JPanel();
        JPanel emptyPanel2 = new JPanel();
        JPanel emptyPanel3 = new JPanel();
        JPanel emptyPanel4 = new JPanel();


        // 타이머 라벨 초기화 및 emptyPanel1에 추가
        timerLabel = new JLabel("Time remaining: 10", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        remainMineLabel = new JLabel(gameStart.getGameRoom().getMineNum()+"", SwingConstants.CENTER);
        remainMineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emptyPanel1.setLayout(new BorderLayout());
        emptyPanel1.add(timerLabel, BorderLayout.CENTER);
        emptyPanel1.add(remainMineLabel, BorderLayout.SOUTH);


        // JTable이 들어갈 패널
        userInfoTableList = new ArrayList<>();

        createTablePanel(gameStart.getGameUserList());

        // W x W 버튼인 마인 맵이 들어갈 패널
        mineMapPanel = createButtonGridPanel(map);


        // 패널 추가
        mainPanel.add(userInfoTableList.get(0)); // 1
        mainPanel.add(emptyPanel1); // 2
        mainPanel.add(userInfoTableList.get(1)); // 3
        mainPanel.add(emptyPanel2); // 4
        mainPanel.add(mineMapPanel); // 5
        mainPanel.add(emptyPanel3); // 6
        mainPanel.add(userInfoTableList.get(2)); // 7
        mainPanel.add(emptyPanel4); // 8
        mainPanel.add(userInfoTableList.get(3)); // 9

        add(mainPanel);
        setVisible(true);
    }

    // JTable이 들어갈 패널을 생성하는 메소드
    private void createTablePanel(ArrayList<User> users) {

        for (User user : users){
            JPanel panel = new JPanel(new BorderLayout());

            JLabel nameLabel = new JLabel(user.getUserName(), SwingConstants.CENTER);
            JLabel userIdLabel = new JLabel("id:"+user.getId(), SwingConstants.CENTER);
            JLabel userRatingLabel = new JLabel(user.getTotal()+"전 "+user.getWin()+"승 "+ user.getLose()+"패 ("+String.format("%.2f", user.getFindRate()) + "%)", SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
            userRatingLabel.setFont(new Font("Arial", Font.BOLD, 14));
            userIdLabel.setFont(new Font("Arial", Font.BOLD, 10));

            JPanel userNamePanel = new JPanel();
            userNamePanel.setLayout(new BorderLayout());
            userNamePanel.add(nameLabel, BorderLayout.NORTH);
            userNamePanel.add(userRatingLabel, BorderLayout.CENTER);
            userNamePanel.add(userIdLabel, BorderLayout.SOUTH);
            panel.add(userNamePanel, BorderLayout.NORTH);


            System.out.println(" 테이블 생성 1");
            String[] columnNames = {"선택 횟수", "찾은 지뢰","성공률"};
            Object[][] data = {
                    {user.getTotalChoice(), user.getRight(),String.format("%.2f",user.getFindRate())+"%"}
            };

            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            JTable table = new JTable(model);

//            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBorder(null);
            panel.add(scrollPane, BorderLayout.CENTER);


            turnLabel = new JLabel("", SwingConstants.CENTER);
            if (user.isTurn()) turnLabel.setText("Turn");
            else turnLabel.setText("");
            turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
            panel.add(turnLabel, BorderLayout.SOUTH);


            userInfoTableList.add(panel);
            userGameTableMap.put(user.getId(),table);
            turnUserMap.put(user.getId(),turnLabel);

        }

        if (users.size()!=4){
            int now = users.size();
            while (now!=4){
                JPanel emptypanel = new JPanel(new BorderLayout());
                String[] columnNames = {"   ", "   ","   ","   "};
                Object[][] data = {
                        {"  ","  ","  ","  "}
                };
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                emptypanel.add(scrollPane, BorderLayout.CENTER);
                userInfoTableList.add(emptypanel);
                now++;
            }
        }

    }

    // 10x10 버튼 그리드를 생성하는 메소드
    private JPanel createButtonGridPanel(Map map) {
        int rows = map.width,cols=map.width;
        mineButtonList = new ArrayList<>();

        JPanel panel = new JPanel(new GridLayout(rows, cols, 1, 1)); // 간격을 거의 없도록 설정
        for(int i=0;i<rows;i++){
            for(int j=0;j<rows;j++){
                JButton button = new JButton();

                if(map.mineMap[i][j]==1) button.setText("O");
                else button.setText("X");

                button.setMargin(new Insets(0, 0, 0, 0)); // 버튼 여백 제거
                button.setFont(new Font("Arial", Font.BOLD, 9)); // 폰트 크기 줄이고 Bold로 설정
                button.setPreferredSize(new Dimension(30, 30)); // 버튼 크기 조정
                button.putClientProperty("id", (i*rows+j)); // 버튼에 고유 ID 설정
                panel.add(button);
                mineButtonList.add(button);

            }
        }
        return panel;
    }

    public void updateGameState(GameStart gameStart) {
        System.out.println("------게임 스크린 처리 과정 ------");
        this.gameStart = gameStart;

        // 선택한 버튼들에 대해서 결과를 화면에 표시
        ArrayList<Integer> disableButton = gameStart.getMap().getDisableButton();
        for(JButton button : mineButtonList){
            int id = (Integer) button.getClientProperty("id"); // 설정된 ID 가져오기
            if (disableButton.contains(id)){
                button.setBackground(Color.gray);
            }
        }

        // 유저들의 정보도 업데이트 ( 총 몇번  눌렀고, 각 유저는 몇개의 지뢰를 찾았는지, 누구의 차례인지)
        for (User u : gameStart.getGameUserList()) {
            JTable table = userGameTableMap.get(u.getId());
            JLabel turnLabel = turnUserMap.get(u.getId());
            if (table != null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setValueAt(u.getTotalChoice(), 0, 0);
                model.setValueAt(u.getRight(), 0, 1);
                model.setValueAt(String.format("%.2f", u.getFindRate()) + "%", 0, 2);
            }
            if (turnLabel!= null) {
                if (u.isTurn()) turnLabel.setText("Turn");
                else turnLabel.setText("");
            }
        }


    }

}