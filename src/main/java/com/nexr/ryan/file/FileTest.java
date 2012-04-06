package com.nexr.ryan.file;

import java.io.File;

import org.apache.log4j.Logger;

public class FileTest {

	static Logger log = Logger.getLogger(FileTest.class);
	
	public static void main(String[] args) {
		File f = new File("/Users/ryan/tmp/test-428.DAT");
		File r = new File("/Users/ryan/tmp/test-428.TMP");
		String path = f.getAbsolutePath();
		log.info(path);
		f.renameTo(r);
		log.info(f.getAbsolutePath());
	}
}
