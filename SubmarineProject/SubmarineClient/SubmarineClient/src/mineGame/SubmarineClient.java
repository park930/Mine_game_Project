package mineGame;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

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

			mainScreen = new MainScreen();
			roomList = new ArrayList<>();
			
			//초기 방 목록 받았을 때의 설정
			processCommand(msg);

			while (true) {
				System.out.println("메세지 받길 대기 중");
				msg = in.readLine();        // start message
				processCommand(msg);
//				mainScreen.setRoomList(roomList);
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
		System.out.println("msg : "+msg);
		JsonObject jsonObject = JsonParser.parseString(msg).getAsJsonObject();
		String command = jsonObject.get("command").getAsString();

		switch (command) {
			case "updateRoom":
				JsonArray roomListJsonArray = jsonObject.getAsJsonArray("roomList");

				// roomListJsonArray를 ArrayList<GameRoom>으로 변환
				roomList.clear();
				Gson gson = new Gson();
				for (int i = 0; i < roomListJsonArray.size(); i++) {
					JsonObject roomObject = roomListJsonArray.get(i).getAsJsonObject();
					GameRoom room = gson.fromJson(roomObject, GameRoom.class);
					roomList.add(room);
				}
				System.out.println("방 목록 받은거 파싱 완료");
				mainScreen.setRoomList(roomList);
				break;

		}
	}


	public static void sendCommand(String command, GameRoom gameRoom) {
		java.util.Map<String, Object> commandMap = new HashMap<>();
		commandMap.put("command", command);
		commandMap.put("GameRoom", gameRoom);

		Gson gson = new Gson();
		String json = gson.toJson(commandMap);
		out.println(json);
	}

}