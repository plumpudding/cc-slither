package at.computercamp.slither.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Networking extends Thread {
	
	private DatagramSocket sock;
	private GameServer gameServer;
	private Map<SocketAddress, String> messageBuffer;
	
	public Networking(int port, GameServer gameServer) {
		try {
			sock = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		messageBuffer = new HashMap<>();
		
		this.gameServer = gameServer;
	}

	@Override
	public void run() {
		
		byte[] buffer = new byte[1280];
		DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
		
		while (true) {
			try {
				sock.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String message = new String(receivePacket.getData());
			if (messageBuffer.containsKey(receivePacket.getSocketAddress())){
				message = messageBuffer.get(receivePacket.getSocketAddress()) + message;
			}
			
			//TODO: find last package in message, process it and remove it from the buffer
			
			int startIndex = message.lastIndexOf("|||") + 3;
			int endIndex = message.lastIndexOf("$$$") - 1;
			
			System.out.println(message.substring(startIndex, endIndex));
			
			//Check indeices
			//bla,  blubb);
			//messageBuffer.put(receivePacket.getSocketAddress(), message.substring(endIndex + 3));
			
			//gameServer.handleClientAction(receivePacket.getData().toString(), receivePacket.getAddress(), receivePacket.getPort());
		}
		
	}
	
	public void sendData(InetAddress address, int port, String data) {

		byte[] sendData = data.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
		
		try {
			sock.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
