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
}
