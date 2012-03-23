package com.nexr.ryan.url;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class WebSpiderWithURLConnection {

	static Logger log = Logger.getLogger(WebSpiderWithURLConnection.class);
	
	public static void main(String[] args) {
		
		try {
			URL url = new URL("http://10.1.7.12:15871/flumemaster.jsp");
			
			FileOutputStream fos = new FileOutputStream("/Users/ryan/tmp.html");
			URLConnection urlCon = url.openConnection();
			String contentType = urlCon.getContentType();
			long d1 = urlCon.getDate();
			Date d = new Date(d1);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
			String sdate = format.format(d);
			
			log.info("Content-type : " + contentType);
			log.info("Readed date  : " + sdate);
			
			InputStream is = urlCon.getInputStream();
			
			byte[] buffer = new byte[1024];
			
			int readCount = 0;
			while((readCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
