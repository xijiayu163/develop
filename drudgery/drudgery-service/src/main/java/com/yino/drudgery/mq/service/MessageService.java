package com.yino.drudgery.mq.service;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.listener.ReceiveListener;

public interface MessageService {
	/**
	 * ������Ϣ
	 * @param obj
	 */
	void sendMsg(Job job);
	
	/**
	 * ������Ϣ
	 *�������֮�󴥷�������
	 * @param obj
	 */
	void receiveMsg(Job job);
	
	void addListener(ReceiveListener listener);
	
	void removeListener(ReceiveListener listener);
}
