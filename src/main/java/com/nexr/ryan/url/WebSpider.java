package com.nexr.ryan.url;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

public class WebSpider {
	static Logger log = Logger.getLogger(WebSpider.class);
	
	public static void main(String[] args) {
		try {
			URL url = new URL("http://10.1.7.12:15871/flumemaster.jsp");
	
			FileOutputStream fos = new FileOutputStream("/Users/ryan/tmp.html");
			InputStream in = url.openStream();
	
			byte[] buffer = new byte[1024];
			int readcount = 0;
			
			log.info("Start reading ...");
			
			while ((readcount=in.read(buffer)) != -1) {
				fos.write(buffer, 0, readcount);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
