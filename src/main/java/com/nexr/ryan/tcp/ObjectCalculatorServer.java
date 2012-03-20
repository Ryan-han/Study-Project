package com.nexr.ryan.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

public class ObjectCalculatorServer {

	static Logger log = Logger.getLogger(ObjectCalculatorServer.class);
	
	public static void main(String arg[]) {
		Socket sock = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		
		try {
			ServerSocket server = new ServerSocket(10001);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
