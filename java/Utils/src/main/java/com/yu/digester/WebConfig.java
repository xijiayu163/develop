package com.yu.digester;

import java.util.HashMap;

public class WebConfig {
	private HashMap rootsMap = new HashMap();

	public void addRoot(Root root) {
		rootsMap.put(root.getName(), root);
	}

	public Root findRoot(String name) {
		return (Root) rootsMap.get(name);
	}

	public Root[] getRoots() {
		Object objs[] = rootsMap.values().toArray();
		Root[] results = new Root[objs.length];
		for (int i = 0; i < objs.length; i++) {
			results[i] = (Root) objs[i];
		}
		return results;
	}

	public int getSize() {
		return rootsMap.size();
	}
}