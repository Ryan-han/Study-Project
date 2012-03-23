package com.nexr.ryan.selector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class SimpleChatClient {
	
	static Logger log = Logger.getLogger(SimpleChatClient.class);
	
	private static final String Host = "localhost";
	private static final int port = 10001;
	
	private Selector selector = null;
	private SocketChannel sc = null;
	
	private Charset charset = null;
	private CharsetDecoder decoder = null;
	
	public SimpleChatClient() {
		charset = Charset.forName("EUC-KR");
		decoder = charset.newDecoder();
	}
	
	public void initServer() {
		try {
			selector = Selector.open();
			sc = SocketChannel.open(new InetSocketAddress(Host, port));
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startServer() {
		startWriter();
		startReader();
	}

	private void startReader() {
		// TODO Auto-generated method stub
		log.info("Reader is started");
		
		Iterator it = null;
		while (true) {
			try {
				selector.select();
				it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					if (key.isReadable()) {
						read(key);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				it.remove();
			}
		}
	}

	private void read(SelectionKey key) {
		// TODO Auto-generated method stub
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		int read = 0;
		
		try {
			read = channel.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buffer.flip();
		String data = "";
		
		try {
			data = decoder.decode(buffer).toString();
		} catch (CharacterCodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("message - " + data);
		
		clearBuffer(buffer);
	}

	private void clearBuffer(ByteBuffer buffer) {
		// TODO Auto-generated method stub
		if(buffer != null) {
			buffer.clear();
			buffer = null;
		}
	}

	private void startWriter() {
		// TODO Auto-generated method stub
		log.info("Writer is started");
		Thread t = new MyThread(sc);
		t.start();
	}

	class MyThread extends Thread {
		SocketChannel sc;
		
		public MyThread(SocketChannel sc) {
			this.sc =sc;
		}
		
		public void run() {
			ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
			
			while (!Thread.currentThread().interrupted()) {
				buffer.clear();
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				
				try {
					String message = br.readLine();
					
					if( message.equals("quit") || message.equals("shutdown")) {
						System.exit(0);
					}
					
					buffer.put(message.getBytes());
					buffer.flip();
					
					sc.write(buffer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	public static void main(String[] args) {
		SimpleChatClient scc = new SimpleChatClient();
		scc.initServer();
		scc.startServer();
	}

	
}

