package com.nexr.ryan.file;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class CopyOfDirToTree {
	
	static Logger log = Logger.getLogger(CopyOfDirToTree.class);
	
	public static void main(String... args) {
		
		File root = new File("/Users/ryan/source");
		if(!root.isDirectory()) {
			System.exit(1);
		}
		
		CopyOfDirToTree dtt = new CopyOfDirToTree();
		DirTree2 dirTree = dtt.createTree(root.getPath());
		dtt.collectRootChildren(dirTree);
	}
	
	public DirTree2 createTree(String rootPath) {
		DirTree2 dTree = new DirTree2(rootPath);
		return dTree;
	}
	
	public DirTree2 collectRootChildren(DirTree2 dirTree) {
		File root = new File(dirTree.getRootPath());
		File[] subdirs = root.listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if(pathname.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}
		});
		
		for (File subdir : subdirs) {
			DirNode2 node = new DirNode2(subdir.getPath());
			collectChildren(node);
			dirTree.addChild(node);
		}
		
		return dirTree;
	}
	
	public DirNode2 collectChildren(DirNode2 node) {
		File root = new File(node.getPath());
		File[] subdirs = root.listFiles(new FileFilter() {
			
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if(pathname.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}
		});
		
		for (File subdir : subdirs) {
			DirNode2 subNode = new DirNode2(subdir.getPath());
			collectChildren(subNode);		
		}
		node.addChild(node);
		return node;
	}

}

class DirTree2 {
	Logger log = Logger.getLogger(DirTree2.class);
	
	String rootPath;
	int maxDepth;
	List<DirNode2> children;
	
	
	public DirTree2(String rootPath) {
		// TODO Auto-generated constructor stub
		this.rootPath = rootPath;
		this.children = new ArrayList<DirNode2>();
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public List<DirNode2> getChilden() {
		return children;
	}

	public void setChilden(List<DirNode2> childen) {
		this.children = childen;
	}
	
	public void addChild(DirNode2 child) {
		this.children.add(child);
	}
	
	public void toStrings() {
		for (DirNode2 node : children) {
			log.info(node.getPath());
		}
	}

}

class DirNode2 {
	Logger log = Logger.getLogger(DirNode2.class);
	String path;
	int depth;
	List<DirNode2> children;

	public DirNode2(String path2) {
		// TODO Auto-generated constructor stub
		this.path = path2;
		this.children = new ArrayList<DirNode2>();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public List<DirNode2> getChilden() {
		return children;
	}

	public void setChilden(List<DirNode2> childen) {
		this.children = childen;
	}
	
	public void addChild(DirNode2 child) {
		log.info(child.getPath());
		this.children.add(child);
	}
	
	public boolean hasChildren() {
		if (children.size() > 0) {
			return true;
		} else
			return false;
	}

}