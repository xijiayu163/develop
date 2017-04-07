package com.yino.drudgery.listener;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.entity.JobResult;

public interface JobStatusListener {
	void onCreate(Job job);
	
	void onStart(Job job);
	
	void onFinish(JobResult jobResult);
}	
