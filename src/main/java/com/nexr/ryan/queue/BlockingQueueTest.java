package com.nexr.ryan.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class BlockingQueueTest {

	static Logger log = Logger.getLogger(BlockingQueueTest.class);

	public static void main(String... args) {
		BlockingQueue<String> a = new ArrayBlockingQueue<String>(3);
		Pthread pthread = new Pthread(a);
		Cthread cthread = new Cthread(a);

		pthread.start();
		cthread.start();
	}

}

class Pthread extends Thread {
	static Logger log = Logger.getLogger(Pthread.class);
	BlockingQueue<String> a;

	public Pthread(BlockingQueue<String> a) {
		this.a = a;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				log.info("put to Queue " + "Test " + i);
				a.put("Test " + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Cthread extends Thread {
	static Logger log = Logger.getLogger(Cthread.class);
	BlockingQueue<String> a;

	public Cthread(BlockingQueue<String> a) {
		this.a = a;
	}

	public void run() {
		while (true) {
			try {

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("Get from queue " + a.poll());
		}
	}
}