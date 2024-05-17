import room.GameRoom;
import room.User;

import java.util.ArrayList;

public class GameStart {
    private boolean doing=true;
    private long id;
    private GameRoom gameRoom;
    private int total;
    private Map map;
    private ArrayList<User> gameUserList;

    public GameStart(boolean doing, ArrayList<SubmarineServer.Client> gamePlayerList, long id, GameRoom gameRoom, int total) {
        this.doing = doing;
        this.id = id;
        this.gameRoom = gameRoom;
        this.total = total;
        map = new Map(gameRoom.getMapSize(), gameRoom.getMineNum());

        gameUserList = new ArrayList<>();
        for(SubmarineServer.Client c :  gamePlayerList){
            gameUserList.add(c.ClientToUser());
        }
    }
}
