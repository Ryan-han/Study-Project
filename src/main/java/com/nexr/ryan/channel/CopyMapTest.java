package com.nexr.ryan.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class CopyMapTest extends TimeChecker {

	public static void main(String[] args) {
		start();
		try {
			copyIO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		end("CopyMap");
	}

	private static void copyIO() throws Exception {
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream(inputFilePath);
		FileOutputStream fos = new FileOutputStream(outputFilePath);
		FileChannel in = fis.getChannel();
		FileChannel out = fos.getChannel();
		
		MappedByteBuffer m = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
		out.write(m);


	}
}
