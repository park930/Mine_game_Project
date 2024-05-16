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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import com.google.gson.Gson;
import room.User;


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

	private java.util.Map<Long, ArrayList<Client>> gameRoomClientsMap;


	public static void main(String[] args) throws Exception {
		new SubmarineServer().createServer();
	}


	public void createServer() throws Exception {
		System.out.println("Server start running ..");
	    ServerSocket server = new ServerSocket(inPort);

		roomList = new ArrayList<>();
		gameRoomClientsMap = new HashMap<>();
		mainScreen = new MainScreen();

	    numPlayer=0;

		//서버 허용 인원 넘기지 않을 때까지
        while (userCnt<maxUser) {
        	Socket socket = server.accept();
            Client c = new Client(socket);
			System.out.println("---clinet id = "+c.getId());
			c.sendCommand("User",c.ClientToUser());
			clients.add(c);
			mainScreen.addClientList(c);
			sendRoomList(c);
			sendClientList(c);
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

		//화면의 유저 목록 갱신 필요
		mainScreen.removeClientList(index);
		//클라이언트들에게 갱신된 유저 목록 전송
		sendAllUserList();

	}


	private void joinRoomClient(User user,GameRoom gameRoom) {
		
		//서버의 사용자가 어느 방에 들어갔는지 표시해야함


		for(GameRoom g : roomList){
			if (g.getId()==gameRoom.getId()){
				g.addPlayer(user);
			}
		}

		//화면의 유저 목록 갱신 필요

		//클라이언트들에게 갱신된 유저 목록 전송
		sendAllUserList();

	}

	public void sendtoall(String msg) {
		for(Client c : clients)
			c.send(msg);
	}



	public void sendRoomList(Client c) {
		c.sendCommand("updateRoom",roomList);
	}

	public void sendClientList(Client c) {
		c.sendCommand("updateClient",clients);
	}

	public void sendAllRoomList() {
		for(Client c : clients)
			sendRoomList(c);
	}

	public void sendAllRoomUser(ArrayList<Client> clients,long roomId) {
		ArrayList<User> userList = new ArrayList<>();
		for(Client c : clients) {
			userList.add(c.ClientToUser());
			c.setRoomId(roomId);
		}

		for(Client c : clients) {
			c.sendRoomUserUpdate(roomId, userList);
		}
	}

	public void sendAllUserList() {
		for(Client c : clients)
			c.sendCommand("updateClient",clients);
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

		private int total,win,lose;
		private int rating;

		private long roomId;


		public Client(Socket socket) throws Exception {
			initial(socket);
            start();
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


		public <T> void sendCommand(String command, T sendObject) {
			java.util.Map<String, Object> commandMap = new HashMap<>();
			commandMap.put("command", command);

			switch (command){
				case "updateRoom":
					commandMap.put("roomList", sendObject);
					break;

				case "User":
					commandMap.put("User", sendObject);
					break;

				case "updateClient":
					ArrayList<User> userList = new ArrayList<>();
					for(Client c : clients){
						userList.add(c.ClientToUser());
					}
					commandMap.put("userList", userList);
					break;

				case "updateRoomUser":
					commandMap.put("roomUserList", sendObject);
					break;
            }

			Gson gson = new Gson();
			String json = gson.toJson(commandMap);
			out.println(json);
		}

		public void sendRoomUserUpdate(Long roomId, ArrayList<User> userList){
			java.util.Map<String, Object> commandMap = new HashMap<>();
			commandMap.put("command", "updateRoomUser");
			commandMap.put("roomId", roomId);
			commandMap.put("userList", userList);

			Gson gson = new Gson();
			String json = gson.toJson(commandMap);
			out.println(json);

		}


		private void processCommand(String msg) {
			Gson gson = new Gson();
			JsonObject commandJson = JsonParser.parseString(msg).getAsJsonObject();
			String command = commandJson.get("command").getAsString();

			switch (command) {
				case "createRoom":
					System.out.println("------------방 생성");
					JsonObject gameRoomJson = commandJson.getAsJsonObject("GameRoom");
					GameRoom gameRoom = gson.fromJson(gameRoomJson, GameRoom.class);
					roomList.add(gameRoom);
					
					//서버의 게임방 목록 관리 위해 추가함
					System.out.println("방장 = "+gameRoom.getChairmanId());
					for(Client c : clients) {
						System.out.println(" client = "+c.getId());
						if (c.getId() == gameRoom.getChairmanId()){
							ArrayList<Client> roomClients = gameRoomClientsMap.get(gameRoom.getId());
							if (roomClients == null) {
								System.out.println("   방에 대한 멤버 리스트 새로 생성");
								// 해당 룸 ID에 대한 ArrayList가 없으면 새로 생성하여 맵에 추가
								roomClients = new ArrayList<>();
								gameRoomClientsMap.put(gameRoom.getId(), roomClients);
							}
							// 클라이언트를 해당 룸 ID의 ArrayList에 추가
							roomClients.add(c);
							System.out.println("   멤버 추가"+c.getId());
						}
					}

					///////////////////임시
					System.out.println("              출력");
					for (java.util.Map.Entry<Long, ArrayList<Client>> entry : gameRoomClientsMap.entrySet()) {
						Long key = entry.getKey();
						ArrayList<Client> value = entry.getValue();
						System.out.println("GameRoom ID: " + key + " -> Clients: " + value);
					}
					System.out.println("               끝");
					//////////////////////////////



					//모든 클라이언트에게 갱신된 roomList전송
					sendAllRoomList();
					//메인 화면의 방 목록 갱신
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
					long clientId = commandJson.get("UserId").getAsInt();
					deleteClient(clientId);
					break;

				case "joinRoom":
					User user = gson.fromJson(commandJson.getAsJsonObject("User"), User.class);
					GameRoom joinRoom = gson.fromJson(commandJson.getAsJsonObject("GameRoom"), GameRoom.class);
					joinRoomClient(user,joinRoom);

					System.out.println(" 방문하려는 방 번호:"+joinRoom.getId());
					long userId = user.getId();
					for(Client c : clients ) {
						if (c.getId() == userId){
							ArrayList<Client> roomClientList = gameRoomClientsMap.get(joinRoom.getId());
							roomClientList.add(c);

							sendAllRoomUser(roomClientList,joinRoom.getId());
						}
					}
					break;

			}
		}


		public User ClientToUser(){
			User user = new User();
			user.setUserName(getUserName());
			user.setId(getId());
			user.setTotal(getTotal());
			user.setWin(getWin());
			user.setLose(getLose());
			user.setRating(getRating());
			return user;
		}



		@Override
		public String toString() {
			String res =  userName+" | "+total+"전 "+ win+"승 "+lose+"패 ("+rating+"%)";
			if (inGame) res += " | 게임 중";
			else res += " | 대기 중";
			return res;
		}


		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public int getWin() {
			return win;
		}

		public void setWin(int win) {
			this.win = win;
		}

		public int getLose() {
			return lose;
		}

		public void setLose(int lose) {
			this.lose = lose;
		}

		public int getRating() {
			return rating;
		}

		public void setRating(int rating) {
			this.rating = rating;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public long getRoomId() {
			return roomId;
		}

		public void setRoomId(long roomId) {
			this.roomId = roomId;
		}
	}


}
