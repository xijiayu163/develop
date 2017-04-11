package com.yino.drudgery.service;

import java.util.List;

import com.yino.drudgery.entity.JobConfig;
import com.yino.drudgery.listener.JobConfigListener;

public interface JobConfigService {
	JobConfig getJobConfig(String jobName);
	
	void saveJobConfig(JobConfig jobConfig);
	
	void removeJobConfig(JobConfig jobConfig);
	
	List<JobConfig> getAllJobConfig();
	
	List<JobConfig> getDependencyJobConfigs(String dependencyName);
	
	void addJobConfigListener(JobConfigListener listener);
	
	void removeJobConfigListener(JobConfigListener listener);
}
