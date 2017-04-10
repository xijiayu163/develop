package com.yu.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yu.service.MyService;

import junit.framework.TestCase;

public class MyServiceTest extends TestCase{
	
	private MyService myService;
	ClassPathXmlApplicationContext context;
	
	@Override
	protected void setUp(){
		System.out.println("setup");
		context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		context.start();
	}
	
	@Override
	protected void tearDown(){
		System.out.println("tear down");
	}
	
	@Test
	public void testSayHello(){
		myService = (MyService)context.getBean("myServiceImpl");
		myService.sayHello();
	}
}
