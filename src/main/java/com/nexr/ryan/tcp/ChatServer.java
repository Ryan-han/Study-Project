package com.nexr.ryan.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class ChatServer {
	static Logger log = Logger.getLogger(ChatServer.class);
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(10001);
			log.info("Wait client ");
			
			HashMap map = new HashMap();
			while(true) {
				Socket sock = server.accept();
				ChatThread chatThread = new ChatThread(sock, map);
				chatThread.start();
				log.info(map.size());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


class ChatThread extends Thread {
	static Logger log = Logger.getLogger(ChatThread.class);
	
	Socket sock;
	String id;
	BufferedReader br;
	HashMap hm;
	boolean initFlag = false;
	
	public ChatThread(Socket sock, HashMap map) {
		log.info("new thread create");
		this.sock = sock;
		this.hm = map;
		
		try {
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			id = br.readLine();
			
			broadcast(id + " entered !");
			log.info("Entered User id is " + id);
			
			synchronized (hm) {
				hm.put(this.id, pw);
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.equals("/quit")) {
					break;
				} 
				
				if (line.indexOf("/to") == 0 ) {
					sendmsg(line);
				} else {
					broadcast(id + " : " + line);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			synchronized (hm) {
				hm.remove(id);
			}
			broadcast("Log out " + id);
			
			if(sock != null) {
				try {
					sock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void sendmsg(String msg) {
		// TODO Auto-generated method stub
		int start = msg.indexOf(" ") + 1;
		int end = msg.indexOf(" ", start);
		
		if (end != -1) {
			String to = msg.substring(start, end);
			String msg2 = msg.substring(end+1);
			
			Object obj = hm.get(to);
			if (obj != null) {
				PrintWriter pw = (PrintWriter)obj;
				pw.println(id + "send message : " + msg2);
				pw.flush();
			}
				
		}
	}

	private void broadcast(String string) {
		// TODO Auto-generated method stub
		synchronized (hm) {
			Collection collection = hm.values();
			Iterator iterator = collection.iterator();
			while (iterator.hasNext()) {
				PrintWriter pw = (PrintWriter) iterator.next();
				pw.println(string);
				pw.flush();
			}
		}
	}
}