package com.nexr.ryan.file;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {

	public static void main(String... args) {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		
		map.put("1", "aaa");
		map.put("2", "aaa");
		
		
		if (map.containsValue("aaa")) {
			System.out.println(" Contains value");
		
		}
	}
		
	
}
