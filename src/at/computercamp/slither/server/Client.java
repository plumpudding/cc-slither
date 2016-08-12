package at.computercamp.slither.server;

import java.net.SocketAddress;

public class Client {

	private Snake snake;
	private long lastSeen;
	private SocketAddress socketAddress;

	public Client(SocketAddress socketAddress, Snake snake) {
		this.socketAddress = socketAddress;
		this.snake = snake;
		this.lastSeen = System.currentTimeMillis();
	}

	public SocketAddress getSocketAddress() {
		return socketAddress;
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
