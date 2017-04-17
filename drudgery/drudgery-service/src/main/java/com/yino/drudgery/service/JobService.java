package com.yino.drudgery.service;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.listener.JobStatusListener;

/**
 * @Description:实现Job缓存或持久化，针对Job状态监听
 * 
 * 
 * @Title: JobService.java
 * @Copyright:
 * 
 * @author: wxb
 * @date:2017/04/11
 * @version:V1.0
 */
public interface JobService {

	void addJob(Job job);
	
	void removeJob(Job job);
	
	void updateJob(Job job);
	
	void waitJob(Job job);
	 
	void notifyJob(Job job);
	
	Job getJob(String jobId);
	
	void addJobStatusListener(JobStatusListener listener);
	
	void removeJobStatusListener(JobStatusListener listener);
	
}
