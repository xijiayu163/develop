package com.yino.drudgery.mq.service;

import java.util.ArrayList;
import java.util.List;

import com.yino.drudgery.listener.ReceiveListener;

public class AbstractMsgService implements MessageService{
	
	protected List<ReceiveListener> receiveListeners;

	public AbstractMsgService(){
		receiveListeners = new ArrayList<ReceiveListener>();
	}
	
	public void sendMsg(Object obj) {
		// TODO Auto-generated method stub
		
	}

	public void receiveMsg(Object obj) {
		// TODO Auto-generated method stub
		fireReceiveEvents(obj);
		
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
