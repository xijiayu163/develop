package com.yu.jmx.demo1;

public class User implements UserMBean {
	 
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	public int add(int x, int y) {
		return x+y;
	}
}
