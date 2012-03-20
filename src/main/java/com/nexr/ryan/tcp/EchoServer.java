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

public class EchoServer {
	static Logger log = Logger.getLogger(EchoServer.class);
	
	public static void main(String[] args) {
		
		try {
			ServerSocket server = new ServerSocket(10001);
			log.info("Waint client ...");
			Socket sock = server.accept();
			InetAddress inetAddr = sock.getInetAddress();
			
			log.info("Access from " + inetAddr.getHostAddress());
			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
			while((line = br.readLine()) != null) {
				log.info("Recieve String " + line);
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
