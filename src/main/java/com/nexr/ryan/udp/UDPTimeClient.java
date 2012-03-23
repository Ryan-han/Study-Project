package com.nexr.ryan.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

public class UDPTimeClient {
	static Logger log = Logger.getLogger(UDPTimeClient.class);
	
	public static void main(String args[]) {
		try {
			InetAddress inetaddr = InetAddress.getByName("127.0.0.1");
			DatagramSocket dsock = new DatagramSocket();
			
			DatagramPacket sendPacket = new DatagramPacket("".getBytes(), "".getBytes().length, inetaddr, 10001);
			dsock.send(sendPacket);
			
			byte[] buffer = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
			dsock.receive(receivePacket);
			
			String msg = new String(receivePacket.getData(), 0, receivePacket.getData().length);
			log.info("Received Time " + msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
