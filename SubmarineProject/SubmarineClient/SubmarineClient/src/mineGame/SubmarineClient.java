package mineGame;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import mineGame.Screen.MainScreen;

import java.io.*;
import java.net.*; 
import java.util.*; 
  
 
public class SubmarineClient {
	static int inPort = 9999;
	public MainScreen mainScreen;
	static String address ="192.168.35.173";
	static public PrintWriter out;
    static public BufferedReader in;
    static String userName = "Alice";
	static MineMap map;
	static int num_mine=10;
	static int width=9;
	static ArrayList<GameRoom> roomList;
	static long userId;
	static ArrayList<User> userList;
	private User myUser;

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

			System.out.println("시작");
			out.println(userName);		//자신의 이름을 보냄
			msg= in.readLine();        // roomList message
			System.out.println("여기서 " +msg);
			System.out.println("dddd");

			// 해당 클라이언트의 id 받음
			processCommand(msg);

			System.out.println("1구역");
			// 접속 중인 유저 목록 받음
			processCommand(msg);

			mainScreen = new MainScreen(myUser);
			roomList = new ArrayList<>();
			userList = new ArrayList<>();

			while (true) {
				System.out.println("메세지 받길 대기 중");
				msg = in.readLine();
				processCommand(msg);
				System.out.println("여기까지");
			}


//           	while(score<=num_mine) {
//    			msg = guess(in);
//
//    			if(msg.equalsIgnoreCase("ok")) {
//    				msg = in.readLine();
//    				int result = Integer.parseInt(msg);
//    				if (result>=0) {
//    					score++;
//    					System.out.println("hit , score = "+score);
//    				}
//    				else
//    					System.out.println("miss , score = "+score);
//
//    			}
//
//    		}
           	
//        	in.close();
//            out.close();
//            socket.close();
            
        }
        catch (Exception e) {}
    }


	public static String guess(BufferedReader in) throws IOException {
    	Scanner scan = new Scanner (System.in);
		   	
    	System.out.print("\n Enter x coordinate:");
		int x = scan.nextInt();
		while ((x < 0) || (x >= width)) {
			System.out.println(" Invalid x, enter a new x coordinate");
			x = scan.nextInt();
		}
		System.out.print(" Enter y coordinate:");
		int y = scan.nextInt();
		while ((y < 0) || (y >= width)) {
			System.out.println(" Invalid y, enter a new y coordinate");
			y = scan.nextInt();
		}

		System.out.println("wait for turn");
		out.println(x+","+y);
		String msg = in.readLine();
		
    	return msg;
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
				System.out.println("방 목록 받은거 파싱 완료");
				mainScreen.setRoomList(roomList);
				break;

			case "User":
				System.out.println("00000000000000000");
				JsonObject userJson = jsonObject.getAsJsonObject("User");
				myUser = gson.fromJson(userJson, User.class);
				System.out.println("받은 내 정보 = " + myUser);
				break;

			case "updateRoomUser":
				System.out.println("-------현재 방 참가한 상태");
				JsonArray roomUserJsonArray = jsonObject.getAsJsonArray("userList");
				ArrayList<User> users = new ArrayList<>();
				for (int i = 0; i < roomUserJsonArray.size(); i++) {
					JsonObject roomObject = roomUserJsonArray.get(i).getAsJsonObject();
					User user = gson.fromJson(roomObject, User.class);
					users.add(user);
				}
				System.out.println("   멤버 재설정 완료");
				// 새로운 방 멤버 users를 화면에 반영해야함
				mainScreen.roomScreen.setRoomUserList(users);
				System.out.println("   아마 막힘");

				break;

			case "updateClient":
				JsonArray userListJsonArray = jsonObject.getAsJsonArray("userList");
				System.out.println("서버한테 유저 목록 받음");
				userList.clear();
				for (int i = 0; i < userListJsonArray.size(); i++) {
					JsonObject userObject = userListJsonArray.get(i).getAsJsonObject();
					User user = gson.fromJson(userObject, User.class);
					userList.add(user);

					//본인이 누군지 찾는 과정
					if (user.getId()==userId) myUser = user;

					System.out.println("유저 : "+user.getUserName());
				}
				mainScreen.setUserList(userList);
				break;

			case "joinedRoomDelete":
				//todo. 화면 전환하는 과정 필요
				mainScreen.roomScreen.dispose();
				mainScreen.setVisible(true);
				mainScreen.showInfo("방장이 방을 닫았습니다.");
				break;

			case "acceptJoinRoom":
				JsonObject roomJson = jsonObject.getAsJsonObject("joinRoom");
				GameRoom gameRoom = gson.fromJson(roomJson, GameRoom.class);
				mainScreen.createJoinRoomScreen(gameRoom);
				break;
		}


	}


	public static <T> void sendCommand(String command, T sendObject) {
		java.util.Map<String, Object> commandMap = new HashMap<>();
		commandMap.put("command", command);

		switch (command){
			case "createRoom", "deleteRoom":
				commandMap.put("GameRoom", sendObject);
				break;

            case "deleteClient":
				commandMap.put("UserId", sendObject);
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
			case "joinRoom", "deleteRoomClient":
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



}