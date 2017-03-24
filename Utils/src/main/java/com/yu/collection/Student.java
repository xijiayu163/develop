package com.yu.collection;

import java.util.Date;

public class Student{
	private String name;
	private Date birthday;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
//	public int compareTo(Student o) {
//		return this.birthday.compareTo(o.birthday);
//	}
}
