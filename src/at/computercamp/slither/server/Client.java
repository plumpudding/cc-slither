package at.computercamp.slither.server;

import java.net.InetAddress;

public class Client {

	private InetAddress address;
	private int port;
	private Snake snake;
	private long lastSeen;

	public Client(InetAddress address, int port, Snake snake) {
		this.address = address;
		this.port = port;
		this.snake = snake;
		this.lastSeen = System.currentTimeMillis();
	}

	public InetAddress getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setLastSeen() {
		lastSeen = System.currentTimeMillis();
	}

	public boolean isOnline() {
		if (lastSeen + 20000 < System.currentTimeMillis()) {
			return false;
		} else {
			return true;
		}
	}
}
