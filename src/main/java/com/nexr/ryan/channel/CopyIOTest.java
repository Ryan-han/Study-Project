package com.nexr.ryan.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CopyIOTest extends TimeChecker {

	public static void main(String[] args) {
		start();
		try {
			copyIO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		end("CopyIO");
	}

	private static void copyIO() throws Exception {
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream(inputFilePath);
		FileOutputStream fos = new FileOutputStream(outputFilePath);

		byte[] buf = new byte[fis.available()];
		fis.read(buf);
		fos.write(buf);

	}
}
