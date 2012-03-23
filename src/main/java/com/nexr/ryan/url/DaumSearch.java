package com.nexr.ryan.url;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class DaumSearch {
	static Logger log = Logger.getLogger(DaumSearch.class);
	
	public static void main(String[] args) {
		
		String keyword = URLEncoder.encode("사");
		String query = "w=tot&t__nil_searchbox=btn&sug=&q=" + keyword;
		
		String u = "http://search.daum.net/search";
		
		log.info(u + query + keyword);
		
		try {
			URL url = new URL(u);
			URLConnection urlCon = url.openConnection();
			HttpURLConnection httpUrlCon = (HttpURLConnection) urlCon;
			
			httpUrlCon.setRequestMethod("GET");
			httpUrlCon.setDoOutput(true);
			httpUrlCon.setDoInput(true);
			httpUrlCon.setUseCaches(false);
			httpUrlCon.setDefaultUseCaches(false);
			
			PrintWriter pw = new PrintWriter(httpUrlCon.getOutputStream());
			pw.println(query);
			pw.flush();
			pw.close();
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlCon.getInputStream()));
			
			pw = new PrintWriter(new FileWriter("/Users/ryan/tmp.html"));
			String inputLine = null;
			
			while((inputLine = br.readLine()) != null) {
				pw.write(inputLine);
			}
			pw.close();
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
