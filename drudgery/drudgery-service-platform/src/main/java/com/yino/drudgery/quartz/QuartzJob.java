package com.yino.drudgery.quartz;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.factory.JobFactory;
import com.yino.drudgery.mq.service.MessageService;
import com.yino.drudgery.service.JobService;
import com.yino.drudgery.service.impl.ServiceImpls;

/**
 * @Description:定时生产Job，并调用消息服务发送
 * 
 * 
 * @Title: QuartzJob.java
 * @Copyright:
 * 
 * @author: wxb
 * @date:2017/04/11
 * @version:V1.0
 */
public class QuartzJob implements Job {
	
	private final Log log = LogFactory.getLog(this.getClass());	
	/**
	 * 
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobConfig jobConfig = null;
		ServiceImpls serviceImpls = null;
		Collection<Object> objects = context.getJobDetail().getJobDataMap().values();
		for (Object o : objects) {
			if (o instanceof JobConfig) {
				jobConfig = (JobConfig) o;
			}

			if (o instanceof ServiceImpls) {
				serviceImpls = (ServiceImpls) o;
			}
		}

		if (jobConfig == null || serviceImpls == null)
			return;

		com.yino.drudgery.entity.Job job = JobFactory.createJob(jobConfig);
		serviceImpls.getMsgService().sendMsg(job);
		serviceImpls.getJobService().addJob(job);
		
	}

}
