package com.nexr.ryan.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SimpleWebServerBasic {

	static Logger log = Logger.getLogger(SimpleWebServerBasic.class);
	
	public static void main(String[] args) {
		Socket sock = null;
		BufferedReader br = null;
		try{
			ServerSocket server = new ServerSocket(10001);
			sock = server.accept();
			
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String line = null;
			
			while ((line = br.readLine()) != null) {
				log.info(line);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (sock != null) {
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
