package com.nexr.ryan.url;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

public class URLInfo {
	static Logger log = Logger.getLogger(URLInfo.class);
	
	public static void main(String args[]) {
		URL url = null;
		try {
			url = new URL("hdfs://www.daum.net:80");
			log.info("Protocol : " + url.getProtocol() );
			log.info("Host     : " + url.getHost());
			log.info("Port     : " + url.getPort());
			log.info("File     : " + url.getPath());
			log.info("Query    : " + url.getQuery());
			log.info("Authrioty: " + url.getAuthority());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
