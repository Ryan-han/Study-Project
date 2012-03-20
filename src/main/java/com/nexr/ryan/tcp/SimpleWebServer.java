package com.nexr.ryan.tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class SimpleWebServer {

	static Logger log = Logger.getLogger(SimpleWebServer.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			ServerSocket server = new ServerSocket(10001);

			while (true) {
				log.info("Wait client request ");
				Socket sock = server.accept();
				log.info("Start processing for new request");

				HttpThread ht = new HttpThread(sock);
				ht.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class HttpThread extends Thread {
	static Logger log = Logger.getLogger(HttpThread.class);

	Socket sock;
	BufferedReader br;
	PrintWriter pw;

	public HttpThread(Socket sock) {
		this.sock = sock;
		try {
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		BufferedReader fbr = null;

		try {
			String line = br.readLine();
			int start = line.indexOf(" ") + 2;
			int end = line.lastIndexOf("HTTP") - 1;

			String fileName = line.substring(start, end);

			if (fileName.endsWith("")) {
				fileName = "index.html";
				log.info("User request " + fileName);

				fbr = new BufferedReader(new FileReader(fileName));
				String fline = null;

				while ((fline = fbr.readLine()) != null) {
					pw.println(fline);
					pw.flush();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (fbr != null) {
				try {
					fbr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (pw != null) {
				pw.close();
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
