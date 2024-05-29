package MineGame;

import room.User;

import java.util.LinkedHashMap;

public class GameRecord {

    private User user;
    private int choiceTotal;
    private int findMine;
    private double findRate;
    private int mapSize;
    private int mineNum;
    private boolean isWin;
    private long roomId;

    public GameRecord(User user, int mapSize, int mineNum, boolean isWin,long roomId) {
        if (user!=null) {
            this.user = user;
            this.choiceTotal = user.getTotalChoice();
            this.findMine = user.getRight();
            this.findRate = user.getFindRate();
        }
        this.mapSize = mapSize;
        this.mineNum = mineNum;
        this.isWin = isWin;
        this.roomId = roomId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getChoiceTotal() {
        return choiceTotal;
    }

    public void setChoiceTotal(int choiceTotal) {
        this.choiceTotal = choiceTotal;
    }

    public int getFindMine() {
        return findMine;
    }

    public void setFindMine(int findMine) {
        this.findMine = findMine;
    }

    public double getFindRate() {
        return findRate;
    }

    public void setFindRate(double findRate) {
        this.findRate = findRate;
    }

    public int getMapSize() {
        return mapSize;
    }

    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }

    public int getMineNum() {
        return mineNum;
    }

    public void setMineNum(int mineNum) {
        this.mineNum = mineNum;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public long getRoomId() {
        return roomId;
    }
}