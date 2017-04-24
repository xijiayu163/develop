package com.yino.drudgery.jobstatuslistener;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.factory.JobFactory;
import com.yino.drudgery.listener.JobStatusListener;
import com.yino.drudgery.service.impl.ServiceImpls;

public class DependencyJobStatusLinstener implements JobStatusListener{

	private final Log log = LogFactory.getLog(this.getClass());	
	private ServiceImpls serviceImpls;
	
	public DependencyJobStatusLinstener(ServiceImpls serviceImpls)
	{
		this.serviceImpls=serviceImpls;
		serviceImpls.getJobService().addJobStatusListener(this);
	}
	
	@Override
	public void onCreate(Job job) {
		// Do nothing
	}

	@Override
	public void onStart(Job job) {
		// Do nothing
	}

	@Override
	public void onFinish(Job job) {
		// TODO Auto-generated method stub
		if(job==null || job.getJobcfg()==null)
		{
			return ;
		}
		
		List<JobConfig> list = serviceImpls.getJobConfigService().getDependencyJobConfigs(job.getJobcfg().getJobName());
		for(JobConfig jobConfig :list)
		{
			if(!jobConfig.isUsed())
			{
				continue;
			}
			
			Job newJob=JobFactory.createJob(jobConfig);
			newJob.setInputJobData(job.getOutputJobData());
			serviceImpls.getMsgService().sendMsg(newJob);
			serviceImpls.getJobService().addJob(newJob);
		}
		
	}

}
