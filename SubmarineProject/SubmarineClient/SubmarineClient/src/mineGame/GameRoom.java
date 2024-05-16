package mineGame;

import java.util.ArrayList;

public class GameRoom {
    private long id;
    private String roomName;
    private int maxPlayer;
    private boolean visible=true;
    private ArrayList<User> playerList;
    private int mapSize=10;
    private int mineNum = 10;
    private long chairmanId;

    public GameRoom(String roomName, int maxPlayer, boolean visible,long roomId, User user){
        this.roomName = roomName;
        this.maxPlayer = maxPlayer;
        this.visible = visible;
        this.id = roomId;
        this.chairmanId = user.getId();
        System.out.println("chairmanId 설정 = "+chairmanId);
        playerList = new ArrayList<>();
        playerList.add(user);
        System.out.println("방 인원 중 방장 추가 : "+user);
    }


    @Override
    public String toString() {
        String res= id+" | "+roomName+" | "+"/"+maxPlayer+" | 맵:"+mapSize+" | 마인:"+mineNum;
        return res;
    }

    public long getChairmanId() {
        return chairmanId;
    }

    public void setChairmanId(long chairmanId) {
        this.chairmanId = chairmanId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ArrayList<User> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<User> playerList) {
        this.playerList = playerList;
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
}
