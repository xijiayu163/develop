package com.yu.service.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.yu.service.MyService;

@ContextConfiguration({
    "classpath:spring-context.xml"
})
public class MyServiceTest2 extends AbstractJUnit4SpringContextTests{
	@Autowired
	private MyService myService;
	
	private static final Log log = LogFactory.getLog(MyServiceTest.class);
	
	@Test
	public void testSayHello(){
		log.info("test case start");
		myService.sayHello();
	}
}
