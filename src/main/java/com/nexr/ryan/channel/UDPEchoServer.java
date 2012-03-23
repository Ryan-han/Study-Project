package com.nexr.ryan.channel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.apache.log4j.Logger;

public class UDPEchoServer {
	static Logger log = Logger.getLogger(UDPEchoServer.class);
	
	int port = 10001;
	
	public void execute() throws Exception {
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress("localhost", port));
		channel.configureBlocking(false);
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		while(true) {
			buffer.clear();
			SocketAddress addr = channel.receive(buffer);
			
			if(addr != null) {
				log.info("Incomming packet");
				buffer.flip();
				channel.send(buffer, addr);
			} else {
				log.info("There isn't incomming packet");
				Thread.sleep(5000);
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new UDPEchoServer().execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
