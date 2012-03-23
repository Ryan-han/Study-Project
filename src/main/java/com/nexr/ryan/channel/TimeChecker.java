package com.nexr.ryan.channel;

import org.apache.log4j.Logger;

public class TimeChecker {
	static Logger log = Logger.getLogger(TimeChecker.class);
	
	static final String inputFilePath = "/Users/ryan/input.zip";
	static final String outputFilePath = "/Users/ryan/output.zip";
	
	static long startTime;
	static long endTime;
	
	static void start(){
		startTime = System.currentTimeMillis();
	}
	
	static void end(String name) {
		endTime = System.currentTimeMillis();
		log.info("[" + name + "time : " + (endTime - startTime) + "]" ); 
	}
	
}
