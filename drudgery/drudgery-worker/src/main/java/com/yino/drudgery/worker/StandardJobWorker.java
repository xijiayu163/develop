package com.yino.drudgery.worker;

import java.util.Date;

import com.yino.drudgery.Factory.JobRunnerFactory;
import com.yino.drudgery.entity.Job;
import com.yino.drudgery.enums.JobRunErrorEnum;
import com.yino.drudgery.enums.JobRunStatusEnum;
import com.yino.drudgery.jobrunner.JobRunner;
import com.yino.drudgery.listener.JobReceiveListener;
import com.yino.drudgery.mq.service.MessageService;

public class StandardJobWorker extends JobReceiveListener{
	
	private MessageService service ;
	
	
	public StandardJobWorker()
	{
		//TODO: 获取发送消息实例
		service = null;
		service.addListener(this);
	}
	
	@Override
	public void doInternal(Job job) {
		JobRunner runner;
		try {
			runner = JobRunnerFactory.createJobRunner(job,service);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			job.setErrorMessage(e.getMessage());
			job.setJobRunStatus(JobRunStatusEnum.finish);
			job.setJobRunError(JobRunErrorEnum.error);
			job.setStartTime(new Date());
			job.setEndTime(new Date());
			return ;
		}
		runner.run();
	}

	/**
	 * @param service the service to set
	 */
	public void setService(MessageService service) {
		this.service = service;
	}
}
