package at.computercamp.slither.server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class GameServer {

	private int listenPort = 51265;
	private Kevin controller = new Kevin();
	private Networking networking = new Networking(listenPort);
	private List<Client> clients = new ArrayList<Client>();
	private String gameStateJson;
	private Gson gson = new Gson();

	private GameServer() {
		new Thread(networking).start(); 
		while (true) {
			long startTime = System.currentTimeMillis();
			loop();
			// sleep for 100 - the duration of loop()
			try {
				Thread.sleep(1000 - (System.currentTimeMillis() - startTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// GameServer Singleton
	
	private static GameServer instance;

	public static GameServer getInstance() {
		if (instance == null)
			instance = new GameServer();
		return instance;
	}

	// main loop
	
	public void loop() {
		controller.tick();

		for (Client client : clients) {
 			sendDataToClient(client);
			if (!client.isOnline()) {
				clients.remove(client);
				controller.removeSnake(client.getSnake());
			}
		}

		// wipe gameStateJson
		// it will be regenerated by sendDataToClient
		gameStateJson = null;
	}

	// network stuff

	//serialize the gs and send it to the client
	public void sendDataToClient(Client client) {
		if (gameStateJson == null)
			gameStateJson = gson.toJson(controller.getGameState());

		networking.sendData(client.getAddress(), client.getPort(), gameStateJson);
	}
	
	public Kevin getController() {
		return controller;
	}
	
	public void handleClientAction(String clientActionJson, InetAddress sourceAddress, int sourcePort) {
		ClientAction action = gson.fromJson(clientActionJson, ClientAction.class);
		
		Snake snake = null;
		
		for (Client client:clients) {
			if (client.getSnake().getName() == action.getName()) {
				snake = client.getSnake();
				break;
			}
		}
		
		if (snake == null) {
			snake = controller.addSnake(action.getName());
			clients.add(new Client(sourceAddress, sourcePort, snake));
		}
		
		controller.handleClientAction(action, snake);
	}
}
