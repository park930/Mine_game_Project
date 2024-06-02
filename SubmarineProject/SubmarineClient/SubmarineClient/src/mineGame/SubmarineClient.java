package mineGame;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mineGame.Screen.GameScreen;
import mineGame.Screen.MainScreen;
import mineGame.Screen.RoomScreen;
import mineGame.Screen.component.GameRecordDialog;
import mineGame.tmp.RoomScreen2;

import java.io.*;
import java.net.*; 
import java.util.*; 
  
 
public class SubmarineClient {
	static int inPort = 9998;
	public MainScreen mainScreen;

	static String address ="192.168.35.173";
//	static String address ="192.168.56.1";


	static public PrintWriter out;
    static public BufferedReader in;
    static String userName = "Alice";
	static Map map;
	static int num_mine=10;
	static int width=9;
	static ArrayList<GameRoom> roomList;
	static ArrayList<User> userList;
	private User myUser;
	private GameScreen gameScreen=null;
	private ArrayList<ChatInfo> mainChatList;

	public static void main(String[] args) throws Exception {
		new SubmarineClient().createClient();
	}

	public void createClient() throws Exception {
		int score=0;
    	String msg;
    	boolean turn = true;

        try (Socket socket = new Socket(address, inPort)) {
        	out = new PrintWriter(socket.getOutputStream(), true);
           	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out.println(userName);		//자신의 이름을 보냄
			msg= in.readLine();        // roomList message

			// 해당 클라이언트의 id 받음
			processCommand(msg);

			// 접속 중인 유저 목록 받음
			processCommand(msg);

			mainScreen = new MainScreen(myUser);
			roomList = new ArrayList<>();
			userList = new ArrayList<>();
			mainChatList = new ArrayList<>();

			while (true) {
				msg = in.readLine();
				processCommand(msg);
			}

        }
        catch (Exception e) {}
    }



	private void processCommand(String msg) {
		Gson gson = new Gson();
		System.out.println("msg : "+msg);
		JsonObject jsonObject = JsonParser.parseString(msg).getAsJsonObject();
		String command = jsonObject.get("command").getAsString();

		switch (command) {
			case "updateRoom":
				JsonArray roomListJsonArray = jsonObject.getAsJsonArray("roomList");

				// roomListJsonArray를 ArrayList<GameRoom>으로 변환
				roomList.clear();
				for (int i = 0; i < roomListJsonArray.size(); i++) {
					JsonObject roomObject = roomListJsonArray.get(i).getAsJsonObject();
					GameRoom room = gson.fromJson(roomObject, GameRoom.class);
					roomList.add(room);
				}
				mainScreen.setRoomList(roomList);
				break;

			case "User":
				JsonObject userJson = jsonObject.getAsJsonObject("User");
				myUser = gson.fromJson(userJson, User.class);
				break;

			case "updateRoomUser":
				JsonArray roomUserJsonArray = jsonObject.getAsJsonArray("userList");
				ArrayList<User> users = new ArrayList<>();
				for (int i = 0; i < roomUserJsonArray.size(); i++) {
					JsonObject roomObject = roomUserJsonArray.get(i).getAsJsonObject();
					User user = gson.fromJson(roomObject, User.class);
					users.add(user);
				}
				// 새로운 방 멤버 users를 화면에 반영해야함
				mainScreen.roomScreen.setRoomUserList(users);

				break;

			case "updateClient":
				JsonArray userListJsonArray = jsonObject.getAsJsonArray("userList");
				userList.clear();
				for (int i = 0; i < userListJsonArray.size(); i++) {
					JsonObject userObject = userListJsonArray.get(i).getAsJsonObject();
					User user = gson.fromJson(userObject, User.class);
					if (user.getId() == myUser.getId()) myUser = user;
					userList.add(user);
				}
				mainScreen.setUserList(userList);
				break;

			case "joinedRoomDelete":
				mainScreen.roomScreen.dispose();
				mainScreen.roomScreen = null;
				mainScreen.setVisible(true);
				mainScreen.showInfo("방장이 방을 닫았습니다.");
				break;

			case "acceptJoinRoom":
				JsonObject roomJson = jsonObject.getAsJsonObject("joinRoom");
				GameRoom gameRoom = gson.fromJson(roomJson, GameRoom.class);
				mainScreen.createJoinRoomScreen(gameRoom);
				break;

			case "startGame":
				jsonObject = jsonObject.getAsJsonObject("gameStart");
				GameStart gameStart = gson.fromJson(jsonObject, GameStart.class);

				//게임 화면 생성
				gameScreen = new GameScreen(gameStart, mainScreen.roomScreen,myUser.getId());
				gameScreen.setVisible(true);
				break;

			case "yourTurn":
				if(gameScreen!=null){
					gameScreen.start();
				}
				break;

			case "updateGameInfo":
				jsonObject = jsonObject.getAsJsonObject("gameStart");
				GameStart updateGame = gson.fromJson(jsonObject, GameStart.class);
				gameScreen.updateGameState(updateGame);
				break;

			case "endGame":
				jsonObject = jsonObject.getAsJsonObject("winUser");
				User winUser = gson.fromJson(jsonObject, User.class);

				// gameScreen의 타이머 종료함
				gameScreen.timerStop();

				// 승자 알림 및 게임 화면 종료
				gameScreen.showInfo("Winner : "+winUser.getUserName()+"\n(id:"+winUser.getId()+")");
				gameScreen.dispose();
				mainScreen.setVisible(true);
				mainScreen.roomScreen=null;
				break;

			case "acceptGiveup":
				gameScreen.dispose();
				mainScreen.setVisible(true);
				mainScreen.roomScreen=null;
				break;

			case "acceptNickName":
				String newName = jsonObject.get("newNickName").getAsString();
				myUser.setUserName(newName);
				mainScreen.myInfoUpdate(myUser);

				if (mainScreen.changeNickNamePanel != null){
					mainScreen.changeNickNamePanel.showInfo("변경이 완료되었습니다.");
					mainScreen.changeNickNamePanel.dispose();
					mainScreen.changeNickNamePanel = null;

				} else {
					// userinfoDialog에 알림창 띄우고, 해당 창 닫기
					mainScreen.userInfoDialog.showInfo("변경이 완료되었습니다.");
					mainScreen.userInfoDialog.dispose();
					mainScreen.userInfoDialog = null;

				}



				break;

			case "rejectNickName":
				// userinfoDialog에 거부 알림창 띄우기
				if (mainScreen.changeNickNamePanel != null){
					mainScreen.changeNickNamePanel.showInfo("중복된 닉네임입니다.");
					mainScreen.changeNickNamePanel.clearTextField();

				} else {
					mainScreen.userInfoDialog.showInfo("중복된 닉네임입니다.");
					mainScreen.userInfoDialog.clearTextField();
				}
				break;

			case "acceptCreateRoom":
				jsonObject = jsonObject.getAsJsonObject("createRoom");
				GameRoom createRoom = gson.fromJson(jsonObject, GameRoom.class);

				mainScreen.createRoomDialog.dispose();
				mainScreen.roomScreen = new RoomScreen(mainScreen,createRoom,myUser);// 새로운 창 생성
				mainScreen.roomScreen.setVisible(true);

				// 방 생성자 준비상태 완료로 전환
				myUser.setReady(true);
				mainScreen.roomScreen.addUser(myUser);



				break;

			case "giveGameRecords":
				JsonArray gameRecordJsonArray = jsonObject.getAsJsonArray("gameRecordList");
				ArrayList<GameRecord> gameRecordList = new ArrayList<>();
				for (int i = 0; i < gameRecordJsonArray.size(); i++) {
					JsonObject gameRecordObject = gameRecordJsonArray.get(i).getAsJsonObject();
					gameRecordList.add(gson.fromJson(gameRecordObject, GameRecord.class));
				}
				
				// 얻은 전적 리스트로 화면 띄우기
				mainScreen.gameRecordDialog = new GameRecordDialog(gameRecordList,myUser);
				break;

			case "addMainChat":
				// 전달 받은 채팅 내용을 리스트에 저장하고 메인 화면에 띄우기
				ChatInfo chatInfo = gson.fromJson(jsonObject.getAsJsonObject("chatInfo"), ChatInfo.class);
				mainChatList.add(chatInfo);
				mainScreen.addMainChat(chatInfo,"userChat");
				break;

			case "addRoomChat":
				// 전달 받은 채팅 내용을 리스트에 저장하고 메인 화면에 띄우기
				chatInfo = gson.fromJson(jsonObject.getAsJsonObject("chatInfo"), ChatInfo.class);
				mainScreen.roomScreen.addChatInfo(chatInfo);
				break;

			case "forceQuitInGameUser":
				gameScreen.showInfo("관리자에 의해 강제 종료되었습니다.");
				gameScreen.timerStop();
				SubmarineClient.sendGameCommand("gameExitClient", gameScreen.getGameStart().getId(), -1,myUser.getId());
				SubmarineClient.sendCommand("deleteClient",myUser.getId());
				System.exit(0);
				break;

			case "forceQuitWaitingUser":
				if (mainScreen.roomScreen==null){
					mainScreen.showInfo("관리자에 의해 강제 종료되었습니다.");
				} else {
					mainScreen.roomScreen.showInfo("관리자에 의해 강제 종료되었습니다.");
					SubmarineClient.sendCommand("deleteRoom",mainScreen.roomScreen.getGameRoom());
				}
				SubmarineClient.sendCommand("deleteClient",myUser.getId());
				System.exit(0);
				break;

			case "maxPlayer":
				mainScreen.showInfo("해당 방은 가득 찼습니다.");
				break;

			case "GameIsStarted":
				mainScreen.showInfo("해당 방은 이미 게임 중입니다.");
				break;
		}


	}



	public static <T> void sendCommand(String command, T sendObject) {
		java.util.Map<String, Object> commandMap = new HashMap<>();
		commandMap.put("command", command);

		switch (command){
			case "createRoom", "deleteRoom", "startGame":
				commandMap.put("GameRoom", sendObject);
				break;

            case "deleteClient","getGameRecord":
				commandMap.put("UserId", sendObject);
				break;

			case "checkNewNickName":
				commandMap.put("User", sendObject);
				break;

			case "mainChatSend","roomChatSend":
				commandMap.put("mainChatInfo",sendObject);
				break;
        }

		Gson gson = new Gson();
		String json = gson.toJson(commandMap);
		out.println(json);
	}

	public static void sendRoomCommand(String command,GameRoom gameRoom, User user){
		java.util.Map<String, Object> commandMap = new HashMap<>();
		commandMap.put("command", command);

		switch (command){
			case "joinRoom", "deleteRoomClient","updateReadyState":
				commandMap.put("GameRoom", gameRoom);
				commandMap.put("User", user);
				break;

//			case "deleteRoomUser":
//				commandMap.put("GameRoom", gameRoom);
//				commandMap.put("User", user);
//				break;

		}

		Gson gson = new Gson();
		String json = gson.toJson(commandMap);
		out.println(json);

	}

	public static void sendGameCommand(String command, long gameId, int choice, long userId) {
		java.util.Map<String, Object> commandMap = new HashMap<>();
		commandMap.put("command", command);

		switch (command){
			case "choiceButton":
				commandMap.put("GameId", gameId);
				commandMap.put("Choice", choice);
				commandMap.put("UserId", userId);
				break;

			case "gameExitClient":
				commandMap.put("GameId", gameId);
				commandMap.put("UserId", userId);
				break;

		}

		Gson gson = new Gson();
		String json = gson.toJson(commandMap);
		out.println(json);

	}



}