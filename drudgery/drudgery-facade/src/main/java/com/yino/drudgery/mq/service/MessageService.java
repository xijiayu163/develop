package com.yino.drudgery.mq.service;

import com.yino.drudgery.listener.ReceiveListener;

public interface MessageService {
	/**
	 * ������Ϣ
	 * @param obj
	 */
	void sendMsg(Object obj);
	
	/**
	 * ������Ϣ
	 *�������֮�󴥷�������
	 * @param obj
	 */
	void receiveMsg(Object obj);
	
	void addListener(ReceiveListener listener);
	
	void removeListener(ReceiveListener listener);
}
