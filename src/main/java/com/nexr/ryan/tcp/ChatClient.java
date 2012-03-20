package com.nexr.ryan.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

public class ChatClient {

	static Logger log = Logger.getLogger(ChatClient.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length != 2) {
			log.info("Usage id server");
			System.exit(1);
		}
		
		Socket sock = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		boolean endFlag = false;
		
		try {
			sock = new Socket(args[1], 10001);
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			pw.println(args[0]);
			pw.flush();
			
			InputThread inputThread = new InputThread(sock, br);
			inputThread.start();
			
			String line = null;
			while ((line = keyboard.readLine()) != null) {
				pw.println(line);
				pw.flush();
				if(line.equals("/quit")) {
					endFlag = true;
					break;
				}
			}
			
			log.info("Terminate client " + args[0]);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (!endFlag) {
				log.info(e);
			}
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (br != null ){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
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

}


class InputThread extends Thread {
	static Logger log = Logger.getLogger(InputThread.class);
	
	Socket sock;
	BufferedReader br;
	public InputThread(Socket sock, BufferedReader br) {
		this.sock = sock;
		this.br = br;
	}
	
	public void run() {
		String line = null;
		
		try {
			while ((line= br.readLine()) != null) {
				log.info(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if (sock != null ) {
			try {
				sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}