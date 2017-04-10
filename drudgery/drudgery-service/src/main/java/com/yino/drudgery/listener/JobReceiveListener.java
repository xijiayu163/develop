package com.yino.drudgery.listener;

import com.yino.drudgery.entity.Job;

public abstract class JobReceiveListener implements ReceiveListener{
	
	public void onReceive(Object obj){
		if(obj instanceof Job){
			Job job = (Job)obj;
			doInternal(job);
		}
	}
	
	public abstract void doInternal(Job job);
}
