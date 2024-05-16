// enjoy submarine detection game
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import room.GameRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import com.google.gson.Gson;


public class SubmarineServer {
	public static int inPort = 9999;
	public static Vector<Client> clients = new Vector<Client>();
	public MainScreen mainScreen;
	public static int maxPlayer=4;
	public static int maxUser=100;
	public static int numPlayer=0;
	public static int width=10;
	public static int num_mine=10;
	public static Map map;
	static ArrayList<GameRoom> roomList;
	static int userCnt=0;


	public static void main(String[] args) throws Exception {
		new SubmarineServer().createServer();
	}


	public void createServer() throws Exception {
		System.out.println("Server start running ..");
	    ServerSocket server = new ServerSocket(inPort);
		roomList = new ArrayList<>();

		mainScreen = new MainScreen();

	    numPlayer=0;

		//서버 허용 인원 넘기지 않을 때까지
        while (userCnt<maxUser) {
        	Socket socket = server.accept();
            Client c = new Client(socket);
			System.out.println("---clinet id = "+c.getId());
			clients.add(c);
			mainScreen.addClientList(c);
			sendRoomList(c);
			numPlayer++;
			userCnt++;

			//시작버튼을 눌렀을때,
			System.out.println("   대기중");

        }

        System.out.println("\n"+numPlayer+" players join");
        for(Client c:clients) {
        	c.turn = true;
        	System.out.println("  - "+c.userName);
        }

        map = new Map(width, num_mine);
        sendtoall("Start Game");

        while(true) {
        	if (allTurn()) {
        		System.out.println();

        		for(Client c : clients) {
            		int check=map.checkMine(c.x, c.y);
        			if (check>=0) {
        				System.out.println(c.userName + " hit at (" + c.x+" , "+c.y+")");
        				map.updateMap(c.x, c.y);
        			}
        			else
        				System.out.println(c.userName + " miss at (" + c.x+" , "+c.y+")");

        			c.send(""+check);
        			c.turn=true;
        		}

        	}
        }

	}

	private void deleteClient(long clientId) {
		int index=0;
		for(Client c : clients){
			if (c.getId() == clientId) break;
			index++;
		}
		clients.remove(index);

		//1. 화면의 유저 목록 갱신 필요
		//2. 클라이언트들에게 갱신된 유저 목록 전송

	}

	public void sendtoall(String msg) {
		for(Client c : clients)
			c.send(msg);
	}


	public void sendAllRoomList() {
		for(Client c : clients)
			c.sendRoomList(roomList);
	}


	public void sendRoomList(Client c) {
		c.sendRoomList(roomList);
	}

	public boolean allTurn() {
		int i=0;
		for(Client c:clients)
			if (c.turn == false)
				i++;

		if (i==clients.size()) return true;
		else return false;
	}


	private int findRoomId(long roomId) {
		for(int i=0;i<roomList.size();i++){
			if (roomList.get(i).getId() == roomId){
				return i;
			}
		}
		return -1;
	}


	class Client extends Thread {
		Socket socket;
		PrintWriter out = null;
		BufferedReader in = null;
		Map map;
		String userName = null;
		int x, y;
		public boolean turn=false;
		boolean inGame=false;


		public Client(Socket socket) throws Exception {
			initial(socket);
            start();
			sendUserId(getId());
		}


		public void initial(Socket socket) throws IOException {
			this.socket = socket;

			out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            userName = in.readLine();
            System.out.println(userName+" joins from  "+socket.getInetAddress());
//            send("Wait for other player..");
		}


        @Override
        public void run() {
        	String msg;

        	try {
            	while(true) {
            		msg = in.readLine();
					if (msg != null){
						System.out.println("서버가 받은것:"+msg);
						processCommand(msg);
					}

            		if (turn) {
            			String[] arr = msg.split(",");
            			x = Integer.parseInt(arr[0]);
            			y = Integer.parseInt(arr[1]);
            			send("ok");
            			turn=false;
            		}

            	}
            }
        	catch (IOException e) { }
        }


		public void send(String msg) {
			out.println(msg);
		}

		private void sendUserId(long userId) {
			java.util.Map<String, Object> commandMap = new HashMap<>();
			commandMap.put("command", "userId");
			commandMap.put("userId", userId);
			Gson gson = new Gson();
			String json = gson.toJson(commandMap);
			out.println(json); // 클라이언트에 전송
		}

		public void sendRoomList(ArrayList<GameRoom> roomList){

			java.util.Map<String, Object> commandMap = new HashMap<>();
			commandMap.put("command", "updateRoom");
			commandMap.put("roomList", roomList);

			Gson gson = new Gson();
			String json = gson.toJson(commandMap);
			out.println(json); // 클라이언트에 전송
		}

		private void processCommand(String msg) {
			Gson gson = new Gson();
			JsonObject commandJson = JsonParser.parseString(msg).getAsJsonObject();
			String command = commandJson.get("command").getAsString();

			switch (command) {
				case "createRoom":
					JsonObject gameRoomJson = commandJson.getAsJsonObject("GameRoom");
					GameRoom gameRoom = gson.fromJson(gameRoomJson, GameRoom.class);
					roomList.add(gameRoom);

					//모든 클라이언트에게 갱신된 roomList전송
					sendAllRoomList();
					mainScreen.updateRoomList(roomList);

					break;

				case "deleteRoom":
					long roomId = gson.fromJson(commandJson.getAsJsonObject("GameRoom"), GameRoom.class).getId();
					int index = findRoomId(roomId);
					if(index!=-1) roomList.remove(index);
					else System.out.println("없는 방 입니다.");

					sendAllRoomList();
					mainScreen.updateRoomList(roomList);
					break;

				case "deleteClient":
					long clientId = commandJson.get("userId").getAsInt();
					deleteClient(clientId);
					break;

			}
		}



		@Override
		public String toString() {
			String res="";
			res+= userName+"\t";
			if (inGame) res+="inGame";
			else res+="notinGame";
			return res;
		}

	}


}
