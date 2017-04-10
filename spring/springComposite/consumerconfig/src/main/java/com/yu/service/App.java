package com.yu.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		args = new String[] { "classpath:spring-context.xml"
				// ,"classpath:spring/ApplicationContext.xml"
				// ,"classpath:spring/mybatis-config.xml"
		};
		ApplicationContext actx = new ClassPathXmlApplicationContext(args);

		MyService myService = (MyService) actx.getBean("myServiceImpl");
		myService.sayHello();

	}
}
