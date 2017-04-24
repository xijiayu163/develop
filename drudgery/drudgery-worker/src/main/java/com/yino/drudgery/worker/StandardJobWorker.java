package com.yino.drudgery.worker;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yino.drudgery.Factory.JobRunnerFactory;
import com.yino.drudgery.entity.Job;
import com.yino.drudgery.enums.JobRunErrorEnum;
import com.yino.drudgery.enums.JobRunStatusEnum;
import com.yino.drudgery.jobrunner.JobRunner;
import com.yino.drudgery.listener.JobReceiveListener;
import com.yino.drudgery.mq.service.MessageService;

public class StandardJobWorker extends JobReceiveListener{
	
	private static final Log log = LogFactory.getLog(StandardJobWorker.class);
	
	private MessageService msgService ;
	
	
	public StandardJobWorker()
	{
	}
	
	@Override
	public void doInternal(Job job) {
		JobRunner runner;
		try {
			runner = JobRunnerFactory.createJobRunner(job,msgService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			job.setErrorMessage(e.getMessage());
			job.setJobRunStatus(JobRunStatusEnum.finish);
			job.setJobRunError(JobRunErrorEnum.error);
			job.setStartTime(new Date());
			job.setEndTime(new Date());
			msgService.sendMsg(job);
			return ;
		}
		runner.run();
	}

	/**
	 * @param service the service to set
	 */
	public void setMsgService(MessageService service) {
		this.msgService = service;
		service.addListener(this);
	}

	public MessageService getMsgService() {
		return msgService;
	}
	
	
	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-worker.xml");
			context.start();
			StandardJobWorker jobWorker = (StandardJobWorker)context.getBean("jobWorker");
			
		}
		catch(Exception e)
		{
			log.error("==>MQ context start error:", e);
			System.exit(0);
		}
	}
	
}
