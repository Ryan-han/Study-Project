package com.nexr.ryan.channel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

public class SSCConnectionTest {
	static Logger log = Logger.getLogger(SSCConnectionTest.class);
	
	static int port = 10001;
	
	public static void main(String[] args) throws Exception {
		InetAddress ia = InetAddress.getLocalHost();
		InetSocketAddress isa = new InetSocketAddress(ia, port);
		
		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		log.info("Is ConnectionPending 1 : " + sc.isConnectionPending());
		sc.connect(isa);
		log.info("Is ConnectionPending 2 : " + sc.isConnectionPending());
		sc.finishConnect();
		log.info("Is ConnectionPending 3 : " + sc.isConnectionPending());
		
		log.info("Is Connected : " + sc.isConnected());
		log.info("Is Blocking Mode : " + sc.isBlocking());
	}
}
