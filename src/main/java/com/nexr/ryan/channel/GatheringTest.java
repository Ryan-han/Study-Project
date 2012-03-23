package com.nexr.ryan.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;

import org.apache.log4j.Logger;

public class GatheringTest {
	
	static Logger log = Logger.getLogger(GatheringTest.class);

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FileOutputStream fos = new FileOutputStream("/Users/ryan/tmp.txt");
		GatheringByteChannel channel = fos.getChannel();
		
		ByteBuffer header = ByteBuffer.allocateDirect(20);
		ByteBuffer body = ByteBuffer.allocateDirect(40);
		ByteBuffer[] buffers = {header,body};
		
		header.put("Hello ".getBytes());
		body.put("World!".getBytes());
		 
		header.flip();
		body.flip();
		
		channel.write(buffers);
		channel.close();
		
		
	}

}
