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
import java.util.*;

import com.google.gson.Gson;
import room.User;


public class SubmarineServer {
	public static int inPort = 9998;
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
	private java.util.Map<Long, ArrayList<GameRecord>> gameRecordClientsMap;
	private ArrayList<GameStart> gameStartList;
	private java.util.Map<Long, GameScreen> gameScreenList;
	private ArrayList<ChatInfo> mainChatList;
	private java.util.Map<Long, ArrayList<ChatInfo>> roomChatListMap;
	//닉네임 저장 배열
	HashSet<String> nicknameSet = new HashSet<>();


	public static void main(String[] args) throws Exception {
		new SubmarineServer().createServer();
	}


	public void createServer() throws Exception {
		System.out.println("Server start running ..");
	    ServerSocket server = new ServerSocket(inPort);

		roomList = new ArrayList<>();
		gameRoomClientsMap = new HashMap<>();
		mainScreen = new MainScreen();
		gameStartList = new ArrayList<>();
		gameScreenList = new HashMap<>();
		gameRecordClientsMap = new HashMap<>();
		mainChatList = new ArrayList<>();
		roomChatListMap = new HashMap<>();

	    numPlayer=0;

		//서버 허용 인원 넘기지 않을 때까지
        while (userCnt<maxUser) {
        	Socket socket = server.accept();
            Client c = new Client(socket);
			nicknameSet.add(c.userName);
			System.out.println("---clinet id = "+c.getId());
			c.sendCommand("User",c.ClientToUser());
			clients.add(c);
			
			// 모두에게 새로 접속한 인원 정보 넘김
			sendAllUserList();
			mainScreen.addClientList(c);
			sendRoomList(c);
			sendClientList(c);
			numPlayer++;
			userCnt++;

			//시작버튼을 눌렀을때,

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
		int index=-1;
		for(int i=0;i<clients.size();i++){
			if (clients.get(i).getId() == clientId) {
				index = i;
			}
		}
		System.out.println("제거하려는 클라이언트 인덱스 : "+index);
		if (index!=-1) clients.remove(index);

		//화면의 유저 목록 갱신 필요
		mainScreen.removeClientList(index);
		//클라이언트들에게 갱신된 유저 목록 전송
		sendAllUserList();

	}


	private void joinRoomClient(User user,GameRoom gameRoom) {
		
		//서버의 사용자가 어느 방에 들어갔는지 표시해야함
		System.out.println(" 기존 인원 = "+gameRoom.getPlayerList().size());
		for(GameRoom g : roomList){
			if (g.getId()==gameRoom.getId()){
				g.addPlayer(user);
			}
		}

		System.out.println(" 수정 인원 = "+gameRoom.getPlayerList().size());
		//화면의 유저 목록 갱신 필요

	}

	private void deleteRoomClient(User user,GameRoom gameRoom) {

		//서버의 타겟 게임방에 유저를 제거함
		for(GameRoom g : roomList){
			if (g.getId()==gameRoom.getId()){
				g.deletePlayer(user.getId());
			}
		}

		// 게임방에 참여한 유저들에게 갱신된 유저 목록 전송

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


	private void sendAllMainChat(ChatInfo chatInfo) {
		for(Client c : clients){
			c.sendCommand("addMainChat",chatInfo);
		}
	}

	private void sendAllRoomChat(ArrayList<Client> roomClientList, ChatInfo chatInfo) {
		System.out.println(" 참여자에게 추가된 메세지 보냄");
		for(Client c : roomClientList){
			System.out.println("보내는 c : "+c.getUserName());
			c.sendCommand("addRoomChat",chatInfo);
		}
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

	private void sendGameStartCommand(ArrayList<Client> gameClientList, GameStart gameStart) {
		for(Client c : gameClientList)
			c.sendCommand("startGame",gameStart);
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


	private int findRoomIndex(long roomId) {
		for(int i=0;i<roomList.size();i++){
			if (roomList.get(i).getId() == roomId){
				return i;
			}
		}
		return -1;
	}

	private boolean checkGameReady(ArrayList<Client> clients, GameRoom gameRoom) {
		System.out.println("   게임 시작하려는 방 정보:"+gameRoom);
		if (clients.size()!=gameRoom.getMaxPlayer()) return false;
		for(Client c : clients){
			if(!c.isReady) return false;
		}
		return true;
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
		private double rating;

		private long roomId;
		private boolean isReady;

		private long id;


		public Client(Socket socket) throws Exception {
			initial(socket);
            start();
		}


		public void initial(Socket socket) throws IOException {
			this.socket = socket;

			out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            userName = in.readLine();
//            System.out.println(userName+" joins from  "+socket.getInetAddress());

			setId(System.currentTimeMillis());
            System.out.println(userName+" joins from  "+getId());
			total=0;win=0;lose=0;
//            send("Wait for other player..");
		}


        @Override
        public void run() {
        	String msg;

        	try {
            	while(true) {
					System.out.println("--------------대기중------------------");
            		msg = in.readLine();
					if (msg != null){
						System.out.println("서버가 받은것:"+msg);
						processCommand(msg);
					}
					System.out.println("메세지가 null");

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
					System.out.println("User라는 클라한테 메세지 보냄");
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

				case "joinedRoomDelete":
					break;
				case "acceptCreateRoom":
					commandMap.put("createRoom",sendObject);
					break;

				case "acceptJoinRoom":
					commandMap.put("joinRoom", sendObject);
					break;

				case "startGame":
					commandMap.put("gameStart", sendObject);
					break;
				case "yourTurn":
					commandMap.put("",sendObject);
					break;
				case "updateGameInfo":
					commandMap.put("gameStart",sendObject);
					break;

				case "endGame":
					commandMap.put("winUser",sendObject);
					break;

				case "acceptNickName":
					commandMap.put("newNickName",sendObject);
					break;

				case "giveGameRecords":
					commandMap.put("gameRecordList",sendObject);
					break;

				case "addMainChat","addRoomChat":
					commandMap.put("chatInfo",sendObject);
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
					Client chairman = findClient(gameRoom.getChairmanId());
					chairman.setReady(true);
					chairman.sendCommand("acceptCreateRoom",gameRoom);
					ArrayList<Client> roomClients = gameRoomClientsMap.computeIfAbsent(gameRoom.getId(), k -> new ArrayList<>());
					roomClients.add(chairman);

					//모든 클라이언트에게 갱신된 roomList전송
					sendAllRoomList();
					//메인 화면의 방 목록 갱신
					mainScreen.updateRoomList(roomList);

					break;

				case "deleteRoom":
					long roomId = gson.fromJson(commandJson.getAsJsonObject("GameRoom"), GameRoom.class).getId();

					//ArrayList<GameRoom>에서 게임방 제거
					int index = findRoomIndex(roomId);
					if(index!=-1) {
						System.out.println(" 방제거함:"+index);
						roomList.remove(index);

						//////////////////////임시
						System.out.println(" 방삭제한 결과");
						for(GameRoom gameRoom1 : roomList){
							System.out.println(gameRoom1.getRoomName());
						}
						System.out.println("222222222222");
						//////////////////////////

					} else System.out.println("이미 없는 방");

					// 게임방의 참가자들에게 방 삭제됐다고 알림
					for(Client c : gameRoomClientsMap.get(roomId)){
						c.sendCommand("joinedRoomDelete",null);
					}
					//gameRoomMap에서 gameRoom 제거해야함
					gameRoomClientsMap.remove(roomId);

					sendAllRoomList();
					mainScreen.updateRoomList(roomList);
					break;

				case "deleteClient":
					long clientId = commandJson.get("UserId").getAsLong();
					deleteClient(clientId);
					break;

				case "joinRoom":
					User user = gson.fromJson(commandJson.getAsJsonObject("User"), User.class);
					GameRoom joinRoom = gson.fromJson(commandJson.getAsJsonObject("GameRoom"), GameRoom.class);

					long joinRoomId = joinRoom.getId();
					long userId = user.getId();
					System.out.println(" 방문하려는 방 번호:"+joinRoomId);

					Client joinClient=null;
					for(Client c : clients){
						if (c.getId() == userId){
							joinClient = c;
						}
					}

					// 방 인원 초과할 경우,
					if (joinRoom.getPlayerList().size()>=joinRoom.getMaxPlayer()){
                        assert joinClient != null;
                        joinClient.sendCommand("maxPlayer",null);
						return;
					}

					// 방 인원 초과 X
					// 방에 인원 추가
					if (joinClient != null) {
						joinRoomClient(user, joinRoom);
						ArrayList<Client> roomClientList = gameRoomClientsMap.get(joinRoomId);
						roomClientList.add(joinClient);

						// 새로운 참여자에게 승인 명령 내림
						joinClient.sendCommand("acceptJoinRoom",joinRoom);

						// 방 참가자들에게 수정된 참여자 목록 전송
						sendAllRoomUser(roomClientList, joinRoom.getId());
					}

					mainScreen.updateRoomList(roomList);

					///////////////////////// 임시 ///////////////////////////////
					for(int i=0;i<roomList.size();i++){
						GameRoom g = roomList.get(i);
						if (g.getId() == joinRoomId){
							for(User u : g.getPlayerList()){
								System.out.println("    현재 방의 참여자 : "+u.getUserName()+" | "+u.getId());
							}

						}
					}
					/////////////////////////////////////////////////////////

					break;

				case "deleteRoomClient":
					User deleteUser = gson.fromJson(commandJson.getAsJsonObject("User"), User.class);
					GameRoom targetRoom = gson.fromJson(commandJson.getAsJsonObject("GameRoom"), GameRoom.class);

					// 방의 해당 인원 제거
					deleteRoomClient(deleteUser,targetRoom);

					System.out.println(" 인원 제거하려는 방 번호:"+targetRoom.getId());
					long deleteId = deleteUser.getId();
					long targetRoomId = targetRoom.getId();


					///////////////////임시
					System.out.println("              출력");
					for (java.util.Map.Entry<Long, ArrayList<Client>> entry : gameRoomClientsMap.entrySet()) {
						Long key = entry.getKey();
						ArrayList<Client> value = entry.getValue();
						System.out.println("GameRoom ID: " + key + " -> Clients: " + value);
					}
					System.out.println("               끝");
					//////////////////////////////

					ArrayList<Client> roomClientList = gameRoomClientsMap.get(targetRoomId);
					if (roomClientList!=null) {
						Client removeClient=null;
						for(Client c : roomClientList) {
							if (c.getId() == deleteId) {
								System.out.println(" 삭제할 멤버 찾기");
								removeClient = c;
							}
						}
						if(removeClient!=null) roomClientList.remove(removeClient);
						sendAllRoomUser(roomClientList,targetRoom.getId());
						mainScreen.updateRoomList(roomList);
					} else {
						System.out.println(" 방 참여자 null임");
					}
					break;

				case "updateReadyState":
					user = gson.fromJson(commandJson.getAsJsonObject("User"), User.class);
					targetRoom = gson.fromJson(commandJson.getAsJsonObject("GameRoom"), GameRoom.class);

					userId = user.getId();

					for(Client c : clients){
						if (c.getId() == userId){
							c.setReady(user.isReady());
						}
					}

					ArrayList<Client> cs = gameRoomClientsMap.get(targetRoom.getId());
					for(Client c : cs){
						if (c.getId() == userId){
							c.setReady(user.isReady());
						}
					}

					sendAllRoomUser(cs,targetRoom.getId());
					break;

				case "startGame":
					targetRoom = gson.fromJson(commandJson.getAsJsonObject("GameRoom"), GameRoom.class);

					// 해당 게임방의 유저들 목록
					ArrayList<Client> gameClientList = gameRoomClientsMap.get(targetRoom.getId());

					// 게임 시작 조건 달성했는지 체크
					if(checkGameReady(gameClientList,targetRoom)){
						// 게임 시작에 필요한 객체 만듦 (맵,유저정보 등등 포함함)
						GameStart gameStart = new GameStart(true,gameClientList,targetRoom.getId(),targetRoom,0);
						gameStartList.add(gameStart);

						// 게임방의 유저들에게 게임 시작하는 메세지 보냄
						sendGameStartCommand(gameClientList,gameStart);
						
						// 게임방의 유저 중 첫번째 유저(방장)에게 자신의 차례라는 것을 알림
						sendCommand("yourTurn",null);

						// 서버의 게임 화면 생성
						GameScreen gameScreen = new GameScreen(gameStart);
						gameScreenList.put(targetRoom.getId(),gameScreen);
						gameScreen.setVisible(true);
					}

					break;



				case "choiceButton":
					long gameId = commandJson.get("GameId").getAsLong();
					int choice = commandJson.get("Choice").getAsInt();
					userId = commandJson.get("UserId").getAsLong();

					GameStart gameStart=null;
					for(GameStart g : gameStartList){
						if (g.getId()==gameId){
							gameStart = g;
						}
					}

					if (gameStart!=null){
						System.out.println("게임방 아이디 : "+gameStart.getId());
						ArrayList<User> gamerList = gameStart.getGameUserList();
						user = findUserById(userId,gamerList);
						if (user != null) {
							Map map = gameStart.getMap();
							//해당 게임에서 마인을 찾았는지 확인
							if (map.checkMine(choice) > 0){
								// 마인을 찾음
								System.out.println("마인 찾음");
								map.getFindMineList().add(choice);
								user.setRight(user.getRight()+1);
							}
							user.setTotalChoice(user.getTotalChoice()+1);
							user.setFindRate( ((user.getRight()*1.0)/user.getTotalChoice())*100 );

							map.getDisableButton().add(choice);
							ArrayList<Integer> enableButton = map.getEnableButton();
							for(int i=0;i<enableButton.size();i++){
								if (enableButton.get(i) == choice) enableButton.remove(i);
							}

						}

						// 다른 차례의 유저 계산함
						ArrayList<Client> clientList = gameRoomClientsMap.get(gameStart.getId());
						gameStart.setTurnIndex((gameStart.getTurnIndex()+1)%clientList.size());
						for(int i=0;i<gamerList.size();i++){
							if (i==gameStart.getTurnIndex()) gamerList.get(i).setTurn(true);
							else gamerList.get(i).setTurn(false);
						}

						// 게임방의 모든 유저에게 이런 결과를 보냄
						for(Client c : clientList)  {
							c.sendCommand("updateGameInfo",gameStart);
						}

						// 마지막 지뢰를 찾았을때,
						if (gameStart.getMap().getFindMineList().size() >= gameStart.getGameRoom().getMineNum()){
							// 승자 도출
							double max=0.0;
							User winUser = null;
							for(User u : gamerList){
								if (u.getFindRate() > max){
									winUser = u;
									max = u.getFindRate();
								}
							}

							// 참여자 통계 변경 적용
							for(Client c : clientList){
								if (c.getId() == winUser.getId()) c.setWin(c.getWin()+1);
								else c.setLose(c.getLose()+1);
								c.setTotal(c.getTotal()+1);
								c.setRating( (c.getWin()*1.0)/c.getTotal()*100 );
							}
							
							// 참여자의 경기 기록 저장
							for(User u : gamerList){
								ArrayList<GameRecord> targetClientRecord = gameRecordClientsMap.computeIfAbsent(u.getId(), k -> new ArrayList<>());
								if (u.getId() == winUser.getId()) targetClientRecord.add(new GameRecord(u,gameStart.getGameRoom().getMapSize(),gameStart.getGameRoom().getMineNum(),true));
								else targetClientRecord.add(new GameRecord(u,gameStart.getGameRoom().getMapSize(),gameStart.getGameRoom().getMineNum(),false));
							}


							// 참여자에게 게임 종료 메세지 전송
							for(Client c : clientList)  {
								c.sendCommand("endGame",winUser);
							}


							// 전체 유저에게 업데이트된 유저 정보 전달
							sendAllUserList();

							// 게임방 목록에서 제거
							long roomid = gameStart.getGameRoom().getId();
							GameRoom g = findRoomById(roomid,roomList);
							if (g != null) roomList.remove(g);
							gameRoomClientsMap.remove(roomid);
							mainScreen.updateRoomList(roomList);

							// 전체 유저에게 업데이트 된 게임방 목록 전달
							sendAllRoomList();

							// 서버의 게임 화면 닫음
							gameScreenList.get(gameStart.getGameRoom().getId()).dispose();
							mainScreen.updateClientList(clients);
							mainScreen.updateRoomList(roomList);
							break;

						}

						Client turnClient=clientList.get(gameStart.getTurnIndex());
						if (turnClient!=null) {
							turnClient.sendCommand("yourTurn",null);
							System.out.println("얘한테 차례됐다고 보냄 : "+turnClient.getId());
						}
						else {
							clientList.get(0).sendCommand("yourTurn",null);
							System.out.println("얘한테 차례됐다고 보냄 : "+clientList.get(0).getId());
						}
						gameScreenList.get(gameStart.getGameRoom().getId()).updateGameState(gameStart);

					}

					break;


				case "gameExitClient":
					gameId = commandJson.get("GameId").getAsLong();
					userId = commandJson.get("UserId").getAsLong();

					gameStart=null;
					for(GameStart g : gameStartList){
						if (g.getId()==gameId){
							gameStart = g;
						}
					}

					if (gameStart!=null) {
						System.out.println("게임방 아이디 : " + gameStart.getId());

						ArrayList<User> gamerList = gameStart.getGameUserList();
						User tmpUser=findUserById(userId,gamerList);
						if(tmpUser!=null) {
							gamerList.remove(tmpUser);
							ArrayList<Client> clientList = gameRoomClientsMap.get(gameStart.getGameRoom().getId());
							Client targetClient = findClientById(tmpUser.getId(), clientList);
							if (targetClient != null) {
								// 해당 유저에게 게임방 나가는거 승인하는 메세지 보냄
								clientList.remove(targetClient);
								targetClient.sendCommand("acceptGiveup", null);
								// 탈주자 통계 변경 적용
								targetClient.setLose(targetClient.getLose()+1);
								targetClient.setTotal(targetClient.getTotal()+1);
								targetClient.setRating( (targetClient.getWin()*1.0)/targetClient.getTotal()*100);
								ArrayList<GameRecord> targetClientRecord = gameRecordClientsMap.computeIfAbsent(targetClient.getId(), k -> new ArrayList<>());
								targetClientRecord.add(new GameRecord(tmpUser,gameStart.getGameRoom().getMapSize(),gameStart.getGameRoom().getMineNum(),false));
								System.out.println("ookokoko");
							}

							if (clientList.size()==1){
								System.out.println(" 남은 인원 1명일 때, ");
								Client c = clientList.get(0);
								System.out.println("남은 1명 : "+c.getUserName());
								c.sendCommand("endGame",c.ClientToUser());
								System.out.println("endGame 보냈음");
								// 승리자 통계 변경 적용
								c.setWin(c.getWin()+1);
								c.setTotal(c.getTotal()+1);
								c.setRating( (c.getWin()*1.0)/c.getTotal()*100 );
								
								// 승리자의 게임 기록 저장
								ArrayList<GameRecord> targetClientRecord = gameRecordClientsMap.computeIfAbsent(c.getId(), k -> new ArrayList<>());
								targetClientRecord.add(new GameRecord(findUserById(c.getId(),gameStart.getGameUserList()),gameStart.getGameRoom().getMapSize(),gameStart.getGameRoom().getMineNum(),true));


								// 게임방 제거
								long roomid = gameStart.getGameRoom().getId();
								GameRoom g = findRoomById(roomid,roomList);
								if (g != null) roomList.remove(g);
								gameRoomClientsMap.remove(roomid);
								mainScreen.updateRoomList(roomList);
								sendAllRoomList();

								gameScreenList.get(roomid).dispose();
								mainScreen.updateClientList(clients);

							} else if(clientList.isEmpty()) {
								System.out.println("남은 인원 0명");
								// 게임방 제거
								long roomid = gameStart.getGameRoom().getId();
								GameRoom g = findRoomById(roomid,roomList);
								if (g != null) roomList.remove(g);
								gameRoomClientsMap.remove(roomid);
								mainScreen.updateRoomList(roomList);
								System.out.println("여기2");
								sendAllRoomList();
								System.out.println("여기3");

								gameScreenList.get(roomid).dispose();
								System.out.println("여기4");
								mainScreen.updateClientList(clients);
								System.out.println("여기5");


							} else {
								for (Client c : clientList) {
									// 남은 유저에게 참여자 바뀌었다고 메세지 보냄
									c.sendCommand("updateGameInfo", gameStart);
								}
							}

							// 전체 유저에게 업데이트된 유저 정보 전달
							sendAllUserList();
							System.out.println("여기6");
						}
						
					}
					
					break;


				case "checkNewNickName":
					commandJson = commandJson.getAsJsonObject("User");
					User checkUser = gson.fromJson(commandJson, User.class);
					Client c = findClient(checkUser.getId());
					String newNickName = checkUser.getUserName();

					if(checkNewNick(checkUser.getUserName())){
						// 닉네임을 저장하는 hashSet에서 기존 닉네임을 제거
						nicknameSet.remove(c.getUserName());


						// 가능하면 해당 유저에게 허가된다는 메세지 보냄
						c.setUserName(checkUser.getUserName());
						c.sendCommand("acceptNickName",newNickName);

						// 모든 유저에게 유저 정보바뀌었다고 바뀜
						sendAllUserList();

						// 서버의 유저 목록도 갱신 필요
						mainScreen.updateClientList(clients);
					} else {
						c.sendCommand("rejectNickName",newNickName);
					}
					break;

				case "getGameRecord":
					clientId = commandJson.get("UserId").getAsLong();
					Client client = findClient(clientId);
					if (client != null) {
						ArrayList<GameRecord> gameRecords = gameRecordClientsMap.computeIfAbsent(clientId, k -> new ArrayList<>());
						client.sendCommand("giveGameRecords",gameRecords);
					}
					break;

				case "mainChatSend":
					ChatInfo chatInfo = gson.fromJson(commandJson.getAsJsonObject("mainChatInfo"), ChatInfo.class);
					// 해당 chat을 메인 채팅 리스트에 넣고 모든 유저에게 보내야함
					mainChatList.add(chatInfo);
					sendAllMainChat(chatInfo);
					break;

				case "roomChatSend":
					chatInfo = gson.fromJson(commandJson.getAsJsonObject("mainChatInfo"), ChatInfo.class);
					// 해당 chat을 메인 채팅 리스트에 넣고 모든 유저에게 보내야함
					ArrayList<ChatInfo> chatList = roomChatListMap.computeIfAbsent(chatInfo.getRoomId(), k -> new ArrayList<>());
					chatList.add(chatInfo);
					roomClientList = gameRoomClientsMap.get(chatInfo.getRoomId());
					System.out.println("보낼때, chatInfo의 색:"+chatInfo.getColor());
					sendAllRoomChat(roomClientList,chatInfo);


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
			if (isReady) user.setReady(true);
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

		public double getRating() {
			return rating;
		}

		public void setRating(double rating) {
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

		public boolean isReady() {
			return isReady;
		}

		public void setReady(boolean ready) {
			isReady = ready;
		}

		@Override
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}

	}



	private boolean checkNewNick(String tmpUserName) {
		return nicknameSet.add(tmpUserName);
	}


	private GameRoom findRoomById(long roomid, ArrayList<GameRoom> roomList) {
		for(GameRoom g : roomList){
			if (roomid == g.getId()){
				return g;
			}
		}
		return null;
	}

	private User findUserById(long userId, ArrayList<User> gamerList) {
		for(User user : gamerList){
			if (user.getId() == userId){
				return user;
			}
		}
		return null;
	}

	private Client findClientById(long id, ArrayList<Client> clientList) {
		for(Client c : clientList){
			if (c.getId() == id){
				return c;
			}
		}
		return null;
	}

	private Client findClient(long userId) {
		for( Client c : clients){
			if (c.getId() == userId){
				return c;
			}
		}
		return null;
	}


}
