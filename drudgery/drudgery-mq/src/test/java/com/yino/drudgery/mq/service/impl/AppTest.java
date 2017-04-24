package com.yino.drudgery.mq.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.enums.JobPriorityEnum;
import com.yino.drudgery.factory.JobFactory;

public class AppTest {

	private static final Log log = LogFactory.getLog(AppTest.class);
	
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
			context.start();
			MqMessageServiceImpl mqProducer = (MqMessageServiceImpl) context.getBean("mqMessageService");
			mqProducer.addListener(new Receiver());
			
			JobConfig jobConfig = new JobConfig();
			jobConfig.setJobName("jobName");
			jobConfig.setJobGroupName("jobGroupName");
			
			for(int i=0;i<11;i++)
			{
				Job job = JobFactory.createJob(jobConfig);
				job.setPriority(JobPriorityEnum.Hight);
				mqProducer.sendMsg(job);
				log.error("==>MQ context start error:"+ job.getJobId());
			}
			
		}
		catch(Exception e)
		{
			log.error("==>MQ context start error:", e);
			System.exit(0);
		}
	}
	
}
