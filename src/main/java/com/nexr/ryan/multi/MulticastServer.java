package com.nexr.ryan.multi;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.log4j.Logger;

public class MulticastServer extends Thread {

	static Logger log = Logger.getLogger(MulticastServer.class);

	DatagramSocket socket = null;
	DatagramPacket packet = null;
	InetAddress channel = null;
	int port = 10001;
	String address = "127.0.0.1";
	boolean onAir = true;

	public MulticastServer() throws SocketException {
		socket = new DatagramSocket(port);
	}

	public void run() {
		String msg = "Do you hear??";
		byte[] b = new byte[1024];

		while (onAir) {
			b = msg.getBytes();
			try {
				channel = InetAddress.getByName(address);
				packet = new DatagramPacket(b,b.length, channel, port);
				socket.send(packet);
				
				Thread.sleep(1000);
				
				log.info("on Air");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			socket.close();
		}
	}
	
	public static void main(String args[]) {
		try {
			new MulticastServer().start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
