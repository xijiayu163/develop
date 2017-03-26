package com.yu.digester;

import java.util.HashMap;

public class Root {
	private String name;
	private HashMap barsMap = new HashMap();

	public String getName() {
		return name;																							
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addBar(Bar bar) {
		barsMap.put(new Integer(bar.getId()), bar);
	}

	public Bar findBar(int id) {
		return (Bar) barsMap.get(new Integer(id));
	}

	public Bar[] getBars() {
		Object objs[] = barsMap.values().toArray();
		Bar[] results = new Bar[objs.length];
		for (int i = 0; i < objs.length - 1; i++) {
			for (int j = 0; j < objs.length - 1 - i; j++) {
				int x1 = ((Bar) objs[j]).getId();
				int x2 = ((Bar) objs[j + 1]).getId();
				if (x1 > x2) {
					Object temp = objs[j];
					objs[j] = objs[j + 1];
					objs[j + 1] = temp;
				}
			}
		}
		for (int i = 0; i < objs.length; i++) {
			results[i] = (Bar) objs[i];
		}
		return results;

	}

	public int getSize() {
		return barsMap.size();
	}
}