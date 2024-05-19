package room;

public class User {
    private String userName;
    private int total,win,lose;
    private int totalChoice,right,wrong;
    private double findRate;
    private long id;
    private double rating;
    private long roomId;
    private boolean isReady=false;
    
    // 게임 중 자신의 차례인지 나타냄
    private boolean turn=false;


    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public void setStartTotalChoice() {
        totalChoice=0; right=0;wrong=0;findRate=0.0;
    }

    public int getTotalChoice() {
        return totalChoice;
    }

    public void setTotalChoice(int totalChoice) {
        this.totalChoice = totalChoice;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public void setTurn(boolean b) {
        turn = b;
    }

    public boolean isTurn(){
        return turn;
    }

    public double getFindRate() {
        return findRate;
    }

    public void setFindRate(double findRate) {
        this.findRate = findRate;
    }
}
