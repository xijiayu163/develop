package com.yu.jmx.demo1;

public interface UserMBean {
	 
	String getUsername();
 
	void setUsername(String username);
 
	String getPassword();
 
	void setPassword(String password);
 
	int add(int x,int y);
}