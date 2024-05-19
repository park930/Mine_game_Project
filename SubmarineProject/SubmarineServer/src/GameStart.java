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
    private int turnIndex=0;


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

        for(User u : gameUserList){
            // 선택 횟수, 지뢰 찾은 수 등.. 초기화
            u.setStartTotalChoice();
            
            // 방장 먼저 시작 차례로 설정
            if (gameRoom.getChairmanId()==u.getId()) u.setTurn(true);
            else u.setTurn(false);
        }

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

    public int getTurnIndex() {
        return turnIndex;
    }

    public void setTurnIndex(int turnIndex) {
        this.turnIndex = turnIndex;
    }

}
