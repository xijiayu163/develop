package com.yino.drudgery.listener;

import com.yino.drudgery.entity.Job;

public interface JobStatusListener {
	void onCreate(Job job);
	
	void onStart(Job job);
	
	void onFinish(Job job);
}	
