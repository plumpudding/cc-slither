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
	private Map<SocketAddress, String> messageBuffer;

	public Networking(int port) {
		try {
			sock = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		messageBuffer = new HashMap<>();
	}

	@Override
	public void run() {

		

		while (true) {
			byte[] buffer = new byte[1280];
			DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
			try {
				sock.receive(receivePacket);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String message = new String(receivePacket.getData());
			message = message.substring(0, message.indexOf(0x00));
			
			if (messageBuffer.containsKey(receivePacket.getSocketAddress())) {
				message = messageBuffer.get(receivePacket.getSocketAddress()) + message;
			}

			int startIndex = message.lastIndexOf("|||") + 3;
			int endIndex = message.lastIndexOf("$$$");

			// if we received a part of a packet, wait for the next part to
			// arrive
			if (startIndex < 0 || endIndex < 0 || startIndex >= endIndex) {
				messageBuffer.put(receivePacket.getSocketAddress(), message);
				continue;
			}

			// now we know we can process the complete message, so we can remove
			// is from the buffer
			//messageBuffer.put(receivePacket.getSocketAddress(), message.substring(0, startIndex - 3));
			messageBuffer.put(receivePacket.getSocketAddress(), "");

			message = message.substring(startIndex, endIndex);
			GameServer.getInstance().handleClientAction(message, receivePacket.getSocketAddress());
			// receivePacket.getAddress(), receivePacket.getPort());
		}

	}

	public void sendData(InetAddress address, int port, String data) {

		//System.out.println(data);
		byte[] sendData = ("|||" + data + "$$$").getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);

		try {
			sock.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
