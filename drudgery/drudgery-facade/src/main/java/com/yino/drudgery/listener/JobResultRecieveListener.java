package com.yino.drudgery.listener;

import com.yino.drudgery.entity.JobResult;

public abstract class JobResultRecieveListener implements ReceiveListener{
	public void onReceive(Object obj){
		if(obj instanceof JobResult){
			JobResult jobResult = (JobResult)obj;
			doInternal(jobResult);
		}
	}
	
	public abstract void doInternal(JobResult jobResult);
}
