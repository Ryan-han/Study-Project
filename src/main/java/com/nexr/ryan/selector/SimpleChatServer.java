package com.nexr.ryan.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.FileHandler;

import org.apache.log4j.Logger;

public class SimpleChatServer {
	static Logger log = Logger.getLogger(SimpleChatServer.class);

	static final String Host = "localhost";
	static final int port = 10001;

	static final FileHandler fileHandler = null;

	Selector selector = null;
	ServerSocketChannel serverSocketChannel = null;
	ServerSocket serverSocket = null;

	Vector room = new Vector();

	public void initServer() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocket = serverSocketChannel.socket();
			serverSocket.bind(new InetSocketAddress(Host, port));
			
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void startServer() {
		log.info("Server is Started...");
		
		while (true) {
			log.info("Request Wait...");
			
			try {
				selector.select();
				Iterator it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					if (key.isAcceptable()) {
						accept(key);
					} else if (key.isReadable()) {
						read(key);
					}
					
					it.remove();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void accept(SelectionKey key) {
		// TODO Auto-generated method stub
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		try {
			SocketChannel sc = server.accept();
			registerChannel(selector, sc, SelectionKey.OP_READ);
			log.info(sc.toString() + " Access client");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void read(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		
		int read;
		try {
			read = sc.read(buffer);
			log.info("read " + read + "  byte.. ");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			try {
				sc.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			removeUser(sc);
			log.info("Removed " + sc);
		}
		
		try {
			broadcast(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clearBuffer(buffer);
	}

	

	private void clearBuffer(ByteBuffer buffer) {
		// TODO Auto-generated method stub
		if (buffer != null) {
			buffer.clear();
			buffer = null;
		}
	}


	private void broadcast(ByteBuffer buffer) throws IOException {
		// TODO Auto-generated method stub
		buffer.flip();
		Iterator iter = room.iterator();
		while(iter.hasNext()) {
			SocketChannel sc = (SocketChannel) iter.next();
			if (sc != null) {
				sc.write(buffer);
				buffer.rewind();
			}
		}
	}


	private void removeUser(SocketChannel sc) {
		// TODO Auto-generated method stub
		room.remove(sc);
	}


	private void registerChannel(Selector selector, SocketChannel sc, int ops) throws IOException {
		// TODO Auto-generated method stub
		if (sc == null) {
			log.info("Invalid Connection");
			return;
		}
		
		sc.configureBlocking(false);
		sc.register(selector, ops);
		addUser(sc);
	}


	private void addUser(SocketChannel sc) {
		// TODO Auto-generated method stub
		room.add(sc);
	}
	
	public static void main(String[] args) {
		SimpleChatServer scs = new SimpleChatServer();
		
		scs.initServer();
		scs.startServer();
	}




}
