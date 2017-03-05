package com.xijia.mq.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xijia.mq.consumer.MQProducer;
import com.xijia.mq.consumer.User;




//@ContextConfiguration(locations = { "classpath:spring-context.xml" })
////使用标准的JUnit @RunWith注释来告诉JUnit使用Spring TestRunner
//@RunWith(SpringJUnit4ClassRunner.class)
public class Apptest {
	
	private static final Log log = LogFactory.getLog(Apptest.class);
	
//	@Test
//	public void testMQProducer(){
//		System.out.println("kaishi");
//		System.out.println("kaishi");
//		final User user = new User();
//		user.setUserId(1);
//		user.setUserNameString("xijia");
//		mqProducer.sendMessage(user);
//	}
	
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
			context.start();
			
			
			MQProducer mqProducer = (MQProducer) context.getBean("mqProducer");
			final User user = new User();
			user.setUserId(1);
			user.setUserNameString("xijia");
			mqProducer.sendMessage(user);
			
		} catch (Exception e) {
			log.error("==>MQ context start error:", e);
			System.exit(0);
		}
	}

}
