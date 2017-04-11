package com.yino.drudgery.dependency;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.factory.JobFactory;
import com.yino.drudgery.listener.JobStatusListener;
import com.yino.drudgery.mq.service.MessageService;
import com.yino.drudgery.service.JobConfigService;

public class dependencyJobStatusLinstener implements JobStatusListener{
	@Autowired
	private JobConfigService  jobConfigService ;
	@Autowired
	private MessageService msgService;
	
	@Override
	public void onCreate(Job job) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(Job job) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(Job job) {
		// TODO Auto-generated method stub
		if(job==null || job.getJobcfg()==null)
		{
			return ;
		}
		
		List<JobConfig> list = jobConfigService.getDependencyJobConfigs(job.getJobcfg().getJobName());
		for(JobConfig jobConfig :list)
		{
			Job newJob=JobFactory.createJob(jobConfig);
			newJob.setInputJobData(job.getOutputJobData());
			msgService.sendMsg(newJob);
		}
		
	}

}
