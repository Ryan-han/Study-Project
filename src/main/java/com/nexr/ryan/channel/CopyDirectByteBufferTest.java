package com.nexr.ryan.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CopyDirectByteBufferTest extends TimeChecker {

	public static void main(String[] args) {
		start();
		try {
			copyIO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		end("CopyByteBuffer");
	}

	private static void copyIO() throws Exception {
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream(inputFilePath);
		FileOutputStream fos = new FileOutputStream(outputFilePath);
		FileChannel in = fis.getChannel();
		FileChannel out = fos.getChannel();
		
		ByteBuffer m = ByteBuffer.allocateDirect((int)in.size());
		in.read(m);
		m.flip();
		out.write(m);

	}
}
