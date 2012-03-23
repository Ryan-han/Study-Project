package com.nexr.ryan.channel;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

public class SSCAcceptExample {
	static Logger log = Logger.getLogger(SSCAcceptExample.class);

	public static void main(String[] args) throws Exception {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress(10001));
		ssc.configureBlocking(false);

		while (true) {
			log.info("Waiting a request ..");
			SocketChannel sc = ssc.accept();
			if (sc == null) {
				Thread.sleep(1000);
			} else {
				log.info(sc.socket().getRemoteSocketAddress() + " try connect");
			}
		}
	}
}
