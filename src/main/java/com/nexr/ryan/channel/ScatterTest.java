package com.nexr.ryan.channel;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

import org.apache.log4j.Logger;

public class ScatterTest {
	static Logger log = Logger.getLogger(ScatterTest.class);
	
	public static void main(String[] args) throws Exception {
		FileInputStream fi = new FileInputStream("/Users/ryan/check.py");
		ScatteringByteChannel channel = fi.getChannel();
		
		ByteBuffer header = ByteBuffer.allocateDirect(100);
		ByteBuffer body = ByteBuffer.allocateDirect(200);
		ByteBuffer[] buffers = {header, body};
		
		int readCount = (int) channel.read(buffers);
		channel.close();
		
		log.info("Read Count : " + readCount);
		log.info("=================================================\n");
		
		header.flip();
		body.flip();
		
		byte[] b = new byte[100];
		header.get(b);
		log.info("Header " + new String(b));
		log.info("=================================================\n");
		
		byte[] bb = new byte[200];
		body.get(bb);
		log.info("Body " + new String(bb));
		log.info("=================================================\n");
	}
}
