package com.yu.service.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yu.service.MyService;

public class ChildSpringTest extends BaseSpringTestCase{
	@Autowired
	private MyService myService;
	
	@Test
	public void testSayHello(){
		log.info("test case start");
		myService.sayHello();
	}
}
