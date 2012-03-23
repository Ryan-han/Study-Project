package com.nexr.ryan.buffer;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

public class BulkReadTest {
	static Logger log = Logger.getLogger(BulkReadTest.class);
	
	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(10);
		buf.put((byte)0).put((byte)1).put((byte)2).put((byte)3).put((byte)4);
		buf.mark();
		buf.put((byte)5).put((byte)6).put((byte)7).put((byte)8).put((byte)9);
		buf.reset();
		
		
		byte[] b = new byte[15];
		int size = buf.remaining();
		
		if (b.length < size)
			size = b.length;
		
		buf.get(b, 0, size);
		
		log.info("Position " + buf.position() + ", Limit " + buf.limit());
		
		doSomething(b, size);
		
	}

	private static void doSomething(byte[] b, int size) {
		// TODO Auto-generated method stub
		for (int i=0; i<size; i++) {
			log.info("byte " + b[i]);
		}
	}
}
