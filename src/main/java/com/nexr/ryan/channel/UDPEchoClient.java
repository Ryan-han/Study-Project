package com.nexr.ryan.channel;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class UDPEchoClient {
	static Logger log = Logger.getLogger(UDPEchoClient.class);
	
	static Timer timer = null;
	
	public UDPEchoClient(int seconds) {
		timer = new Timer();
		timer.schedule(new EchoClientTask(), seconds * 1000);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.cancel();
	}
	
	class EchoClientTask extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				DatagramChannel channel = DatagramChannel.open();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
