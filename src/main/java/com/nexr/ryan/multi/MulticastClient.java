package com.nexr.ryan.multi;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.log4j.Logger;

public class MulticastClient {
	static Logger log = Logger.getLogger(MulticastClient.class);
	
	MulticastSocket receiver = null;
	DatagramPacket packet = null;
	InetAddress channel = null;
	int port = 10001;
	String address = "127.0.0.1";
	byte[] b = new byte[1024];
	
	public MulticastClient() {
		try {
			receiver = new MulticastSocket(port);
			channel = InetAddress.getByName(address);
			packet = new DatagramPacket(b, b.length);
			receiver.joinGroup(channel);
			
			for (int i=0; i<3; i++) {
				receiver.receive(packet);
				String notice = new String(packet.getData());
				log.info("Message : " + notice);
			}
			receiver.leaveGroup(channel);
			receiver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MulticastClient();
	}
}
