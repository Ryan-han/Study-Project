package com.nexr.ryan.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ScheduledThread {
	static Logger log = Logger.getLogger(ScheduledThread.class);
	
	static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) {
		service.scheduleAtFixedRate(new TestThread(), 1, 1, TimeUnit.MINUTES);
	
	}
	

}

class TestThread extends Thread {
	Logger log = Logger.getLogger(TestThread.class);
	
	public void run() {
		log.info("test!!");
	}
}