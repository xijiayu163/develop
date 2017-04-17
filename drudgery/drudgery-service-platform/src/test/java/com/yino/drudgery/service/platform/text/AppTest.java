package com.yino.drudgery.service.platform.text;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yino.drudgery.mq.service.MessageService;
import com.yino.drudgery.service.impl.JobConfigServiceImpl;
import com.yino.drudgery.service.impl.JobServiceImpl;

public class AppTest {

	private static final Log log = LogFactory.getLog(AppTest.class);
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-service-platform.xml");
			context.start();
			JobConfigServiceImpl jobConfigService = (JobConfigServiceImpl)context.getBean("jobConfigService");
			JobServiceImpl jobServiceImpl = (JobServiceImpl)context.getBean("jobService");
			MessageService msgService = (MessageService)context.getBean("mqMessageService");
			
			//System.exit(0);
		}
		catch(Exception e)
		{
			log.error("==>MQ context start error:", e);
			System.exit(0);
		}
	}
}
