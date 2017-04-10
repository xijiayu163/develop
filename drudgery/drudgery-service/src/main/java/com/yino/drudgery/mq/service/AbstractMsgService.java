package com.yino.drudgery.mq.service;

import java.util.ArrayList;
import java.util.List;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.listener.ReceiveListener;

public class AbstractMsgService implements MessageService{
	
	protected List<ReceiveListener> receiveListeners;

	public AbstractMsgService(){
		receiveListeners = new ArrayList<ReceiveListener>();
	}
	
	public void sendMsg(Job job) {
		// TODO Auto-generated method stub
		
	}

	public void receiveMsg(Job job) {
		// TODO Auto-generated method stub
		fireReceiveEvents(job);
		
	}

	public void addListener(ReceiveListener listener) {
		receiveListeners.add(listener);
	}

	public void removeListener(ReceiveListener listener) {
		receiveListeners.remove(listener);
	}
	
	public void fireReceiveEvents(Object obj){
		for (ReceiveListener receiveListener : receiveListeners) {
			receiveListener.onReceive(obj);
		}
	}
}
