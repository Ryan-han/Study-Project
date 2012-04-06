package com.nexr.ryan.file;

import java.awt.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class SortMapExample{
	 
  public static void main(String[] args) {

	System.out.println("Unsort Map......");
	Map<String,String> unsortMap = new HashMap<String,String>();
	unsortMap.put("1-1", "1");
	unsortMap.put("1-2", "A");
	unsortMap.put("2-1", "2");
	unsortMap.put("2-3", "B");
	unsortMap.put("3-1", "C");
	unsortMap.put("3-2", "c");
	unsortMap.put("4-1", "b");
	unsortMap.put("4-2", "a");

	Iterator iterator=unsortMap.entrySet().iterator();

       for (Map.Entry entry : unsortMap.entrySet()) {
       	System.out.println("Key : " + entry.getKey() 
      			+ " Value : " + entry.getValue());
       }

       System.out.println("Sorted Map......");
       Map<String,String> sortedMap =  sortByComparator(unsortMap);

       for (Map.Entry entry : sortedMap.entrySet()) {
       	System.out.println("Key : " + entry.getKey() 
      			+ " Value : " + entry.getValue());
       }
  }

  private static Map sortByComparator(Map unsortMap) {

       LinkedList list = new LinkedList(unsortMap.entrySet());

       //sort list based on comparator
       Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
	           return ((Comparable) ((Map.Entry) (o2)).getKey())
	           .compareTo(((Map.Entry) (o1)).getKey());
            }
	});

       //put sorted list into map again
	Map sortedMap = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
	     Map.Entry entry = (Map.Entry)it.next();
	     sortedMap.put(entry.getKey(), entry.getValue());
	}
	return sortedMap;
  }	
}