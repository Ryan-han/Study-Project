package com.nexr.ryan.channel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

public class StreamTest {
	static Logger log = Logger.getLogger(StreamTest.class);
	
	static FileInputStream in = null;
	
	public static void main(String[] args) {
		try {
			in = new FileInputStream("/Users/ryan/check.py");
			TestThread tt = new TestThread(in);
			tt.start();
			
			Thread.sleep(2000);
			log.info("main " + in.available());
			
			tt.interrupt();
			
			Thread.sleep(2000);
			log.info("main " + in.available());
			
			Thread.sleep(2000);
			log.info("main " + in.available());
			
			int v = 0;
			while((v=in.read()) != -1) {
				System.out.println("Main ...");
				System.out.println(v);
				Thread.sleep(1000);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	static class TestThread extends Thread {
		
		FileInputStream in;
		public TestThread(FileInputStream in) {
			this.in = in;
			
		}
		
		public void run() {
			int v =0;
			
			try {
				while((v=in.read()) != -1) {
					System.out.println("Thread start ...");
					System.out.println(v);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Thread End ...");
//				e.printStackTrace();
			}
		}
	}
}

