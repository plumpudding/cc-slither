package at.computercamp.slither.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Networking  implements Runnable{
	
	private DatagramSocket sock;
	
	
	public Networking(int port) {
		try {
			sock = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
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
			
			GameServer.getInstance().handleClientAction(receivePacket.getData().toString(), receivePacket.getAddress(), receivePacket.getPort());
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
