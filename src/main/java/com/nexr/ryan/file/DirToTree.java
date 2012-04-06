package com.nexr.ryan.file;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class DirToTree {

	static Logger log = Logger.getLogger(DirToTree.class);

	public static void main(String... args) {

		File root = new File("/Users/ryan/source");
		if (!root.isDirectory()) {
			System.exit(1);
		}
		Map<String, String> result = new ConcurrentHashMap<String, String>();

		DirToTree dtt = new DirToTree();

		result = dtt.collectChildren(root.getPath(), result, 0, 0, 3);

		Map<String, String> sortedMap = sortByComparator(result);
		for (Map.Entry entry : sortedMap.entrySet()) {
			log.info("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}

	private static Map<String, String> sortByComparator(Map unsortMap) {

		LinkedList<String> list = new LinkedList<String>(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getKey())
						.compareTo(((Map.Entry) (o1)).getKey());
			}
		});

		// put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public Map<String, String> collectChildren(String path,
			Map<String, String> result, int depth, int count, int recurseDepth) {
		depth = depth + 1;
		if (recurseDepth >= depth) {
			File root = new File(path);
			File[] subdirs = root.listFiles(new FileFilter() {

				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					if (pathname.isDirectory()) {
						return true;
					} else {
						return false;
					}
				}
			});

			for (int i = 0; i < subdirs.length; i++) {
				count = count + 1;
				String key = depth + "-" + count;
				result.put(key, subdirs[i].getPath());
				collectChildren(subdirs[i].getPath(), result, depth, count,
						recurseDepth);
			}
		}
		return result;
	}

}
