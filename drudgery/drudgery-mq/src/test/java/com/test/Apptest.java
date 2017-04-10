package com.test;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.ConsumerSessionAwareMessageListener;
import com.test.MQProducer;
import com.test.User;
import com.yino.drudgery.mq.service.impl.MqMessageConsumer;
import com.yino.drudgery.mq.service.impl.MqMessageProducer;




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
//			log.error("==>MQ context start error:context");
//			MqMessageConsumer consumer = (MqMessageConsumer)context.getBean("mqMessageConsumer");
//			consumer.start();
//			log.error("==>MQ context start error:consumer"+consumer.getSelector());
//			MqMessageProducer producer = (MqMessageProducer)context.getBean("mqMessageProducer");
//			producer.start();
//			log.error("==>MQ context start error:producer");
			
			ConsumerSessionAwareMessageListener bean = (ConsumerSessionAwareMessageListener)context.getBean("consumerSessionAwareMessageListener");
			bean.setIndenty(1);
			
			MQProducer mqProducer = (MQProducer) context.getBean("mqProducer");
			for(int i=0;i<100;i++){
				User user = new User();
				user.setUserId(i);
				user.setUserNameString("xijia");
				mqProducer.sendMessage(user);
			}
			
		} catch (Exception e) {
			log.error("==>MQ context start error:", e);
			System.exit(0);
		}
	}

}
