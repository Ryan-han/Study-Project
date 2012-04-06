package com.nexr.ryan.file;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class FileTreeToQueue {

	static Logger log = Logger.getLogger(FileTreeToQueue.class);
	static Pattern regPattern;

	public static void main(String... args) {

		File root = new File("/Users/ryan/source");
		if (!root.isDirectory()) {
			System.exit(1);
		}
		String regex = ".*";
		regPattern = Pattern.compile(regex);

		BlockingQueue<File> queue;
		List<File> result = new ArrayList<File>();
		List<File> dirList = new ArrayList<File>();

		FileTreeToQueue dtt = new FileTreeToQueue();

		dirList.add(root);
		dirList = dtt.getDirs(root.getPath(), dirList, 0, 4);
		for (File dir : dirList) {
			result = dtt.getFiles(dir.getPath(), regex, result);
		}

		if (result.size() > 0) {
			queue = new ArrayBlockingQueue<File>(result.size());
			for (File f : result) {
				queue.add(f);
			}

			for (File f : queue) {
				log.info(f.getPath());
			}
		}
	}

	public List<File> getDirs(String path, List<File> dirList, int depth,
			int recurseDepth) {
		depth = depth + 1;
		File[] dirs = null;
		if (recurseDepth >= depth) {
			File root = new File(path);
			dirs = root.listFiles(new FileFilter() {

				public boolean accept(File pathname) {
					// TODO Auto-generated method stub
					if (pathname.isDirectory()) {
						return true;
					} else {
						return false;
					}
				}
			});
		}

		if (dirs != null) {
			for (File file : dirs) {
				dirList.add(file);
				getDirs(file.getPath(), dirList, depth, recurseDepth);
			}
		}

		return dirList;
	}

	public List<File> getFiles(String path, String regex, List<File> files) {
		File root = new File(path);
		File[] fs = root.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if (pathname.isDirectory()) {
					return false;
				} else if (pathname.isFile()) {
					Matcher mc = regPattern.matcher(pathname.getName());
					if (mc.matches()) {
						return true;
					} else {
						return false;
					}
				} else
					return true;

			}
		});

		for (File file : fs) {
			files.add(file);
		}
		return files;
	}

}
