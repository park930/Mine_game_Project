package mineGame;

public class User {
    private String userName;
    private int total,win,lose;
    private int id;

    public User(String userName, int total, int win, int lose, int id) {
        this.userName = userName;
        this.total = total;
        this.win = win;
        this.lose = lose;
        this.id = id;
    }

}
