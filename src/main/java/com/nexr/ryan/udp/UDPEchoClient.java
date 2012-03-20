package com.nexr.ryan.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.log4j.Logger;

public class UDPEchoClient {
	static Logger log = Logger.getLogger(UDPEchoClient.class);
	
	public static void main(String[] args) {
		DatagramSocket dsock = null;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			dsock = new DatagramSocket();
			String line = null;
			while((line = br.readLine()) !=null ) {
				
				DatagramPacket sendPacket = new DatagramPacket(line.getBytes(), line.getBytes().length, InetAddress.getByName("127.0.0.1"), 10001);
				dsock.send(sendPacket);
				
				if(line.equals("quit")) {
					break;
				}
				
				byte[] buffer = new byte[line.getBytes().length];
				DatagramPacket receivePackat = new DatagramPacket(buffer, buffer.length);
				dsock.receive(receivePackat);
				
				String msg = new String(receivePackat.getData(), 0, receivePackat.getData().length);
				log.info("Client receive message " + msg);
				
			}
			
			log.info("shutdown client");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(dsock != null) {
				dsock.close();
			}
		}
	}
}
