package com.yino.drudgery.service;

import com.yino.drudgery.entity.JobConfig;

public interface JobConfigService {
	JobConfig getJobConfig(String jobName);
	
	void saveJobConfig(JobConfig jobConfig);
	
	void removeJobConfig(JobConfig jobConfig);
	
}
