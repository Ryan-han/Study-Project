package com.nexr.ryan.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.log4j.Logger;

public class FileLockTest {
	static Logger log = Logger.getLogger(FileLockTest.class);

	public static void main(String[] args) {
		FileChannel channel = null;

		File file = new File("/Users/ryan/tmp.txt");
		FileLock lock = null;
		try {
			channel = new RandomAccessFile(file, "rw").getChannel();
			lock = channel.lock(0, Long.MAX_VALUE, true);

			boolean isShared = lock.isShared();
			log.info("isShard " + isShared);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				lock.release();
				channel.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
