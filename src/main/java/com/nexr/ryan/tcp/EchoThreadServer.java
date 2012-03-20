package com.nexr.ryan.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class EchoThreadServer {

	static Logger log = Logger.getLogger(EchoThreadServer.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(10001);
			log.info("Waiting client ....");

			while (true) {
				Socket sock = server.accept();
				EchoThread echoThread = new EchoThread(sock);
				echoThread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class EchoThread extends Thread {
	static Logger log = Logger.getLogger(EchoThread.class);

	Socket sock;

	public EchoThread(Socket sock) {
		this.sock = sock;
	}

	public void run() {
		try {
			InetAddress inetAddr = sock.getInetAddress();
			log.info("Client access from " + inetAddr.getHostAddress());

			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
			while ((line = br.readLine()) != null) {
				log.info("Recieve message from Client " + line);
				pw.println(line);
				pw.flush();
			}
			
			pw.close();
			br.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}