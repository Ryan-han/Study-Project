package com.nexr.ryan.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class UDPTimeServer {
	static Logger log = Logger.getLogger(UDPTimeServer.class);
	
	public static void main(String[] args) {
		int port = 10001;
		
		DatagramSocket dsock = null;
		
		try {
			dsock = new DatagramSocket(port);
			String line = null;
			while (true) {
				byte[] buffer = new byte[1024];
				DatagramPacket receivepacket = new DatagramPacket(buffer, buffer.length);
				dsock.receive(receivepacket);
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String sdate = format.format(new Date());
				
				log.info("Current time " + sdate);
				
				DatagramPacket sendPacket = new  DatagramPacket(sdate.getBytes(), sdate.getBytes().length, receivepacket.getAddress(), receivepacket.getPort());
				dsock.send(sendPacket);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
