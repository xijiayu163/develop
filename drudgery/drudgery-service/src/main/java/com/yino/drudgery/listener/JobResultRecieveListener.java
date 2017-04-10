package com.yino.drudgery.listener;

import com.yino.drudgery.entity.Job;

public abstract class JobResultRecieveListener implements ReceiveListener{
	public void onReceive(Object obj){
		if(obj instanceof Job){
			Job jobResult = (Job)obj;
			doInternal(jobResult);
		}
	}
	
	public abstract void doInternal(Job jobResult);
}
