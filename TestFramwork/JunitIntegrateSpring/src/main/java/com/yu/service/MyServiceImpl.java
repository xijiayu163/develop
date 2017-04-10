package com.yu.service;

import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService{

	public void sayHello() {
		System.out.println("hello");
	}
}
