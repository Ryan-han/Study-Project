package com.nexr.ryan.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

public class UDPEchoServer {
	static Logger log = Logger.getLogger(UDPEchoServer.class);

	public static void main(String args[]) {

		int port = 10001;

		DatagramSocket dsock = null;

		try {
			dsock = new DatagramSocket(port);
			String line = null;

			while (true) {
				byte[] buffer = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
				try {
					dsock.receive(receivePacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String msg = new String(receivePacket.getData(), 0,
						receivePacket.getLength());
				log.info("Receive Message " + msg);

				if (msg.equals("quit")) {
					break;
				}

				DatagramPacket sendPacket = new DatagramPacket(receivePacket.getData(),
						receivePacket.getData().length, receivePacket.getAddress(),
						receivePacket.getPort());
				
				try {
					dsock.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			log.info("Shutdown UDP echo server");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (dsock != null) {
				dsock.close();
			}
		}
	}
}
