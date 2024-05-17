package mineGame;

import java.util.ArrayList;

public class GameStart {
    private boolean doing=true;
    private long id;
    private GameRoom gameRoom;
    private int total;
    private Map map;
    private ArrayList<User> gameUserList;

    public GameStart(boolean doing,long id, GameRoom gameRoom, int total, Map map, ArrayList<User> gameUserList) {
        this.doing = doing;
        this.id = id;
        this.gameRoom = gameRoom;
        this.total = total;
        this.map = map;
        this.gameUserList = gameUserList;
    }

    @Override
    public String toString() {
        String res = doing+"|"+id+"|"+gameRoom+"|"+total+"|";
        for(User u : gameUserList){
            res += u+" | ";
        }
        return res;
    }


    public boolean isDoing() {
        return doing;
    }

    public void setDoing(boolean doing) {
        this.doing = doing;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GameRoom getGameRoom() {
        return gameRoom;
    }

    public void setGameRoom(GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<User> getGameUserList() {
        return gameUserList;
    }

    public void setGameUserList(ArrayList<User> gameUserList) {
        this.gameUserList = gameUserList;
    }
}
