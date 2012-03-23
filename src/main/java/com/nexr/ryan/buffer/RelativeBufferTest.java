package com.nexr.ryan.buffer;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

public class RelativeBufferTest {
	static Logger log = Logger.getLogger(RelativeBufferTest.class);
	
	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(10);
		log.info("Init Position " + buf.position());
		log.info("Init Limit    " + buf.limit());
		log.info("Init Capacity " + buf.capacity());
		log.info("=====================================");
		
		buf.mark();
		
		buf.put((byte)10).put((byte)11).put((byte)12);
		log.info("Init Position " + buf.position());
		log.info("Init Limit    " + buf.limit());
		log.info("Init Capacity " + buf.capacity());
		log.info("=====================================");
		
		buf.reset();
		
		log.info("Init Position " + buf.position());
		log.info("Init Limit    " + buf.limit());
		log.info("Init Capacity " + buf.capacity());
		log.info("=====================================");
		
		log.info("Value " + buf.get() + ", Position : " + buf.position());
		log.info("Value " + buf.get() + ", Position : " + buf.position());
		log.info("Value " + buf.get() + ", Position : " + buf.position());
		log.info("Value " + buf.get() + ", Position : " + buf.position());
	}
}
