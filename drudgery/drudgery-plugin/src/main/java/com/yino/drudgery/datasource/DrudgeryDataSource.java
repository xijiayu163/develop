package com.yino.drudgery.datasource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DrudgeryDataSource {
	private String name;
	private BasicDataSource basicDataSource;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}
	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
	}
}
