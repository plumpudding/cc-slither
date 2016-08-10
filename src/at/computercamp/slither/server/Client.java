package at.computercamp.slither.server;

import java.net.InetAddress;

public class Client {

	private InetAddress address;
	private int port;
	private Snake snake;
	
	public Client(InetAddress address, int port, Snake snake) {
		this.address = address;
		this.port = port;
		this.snake = snake;
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
}
