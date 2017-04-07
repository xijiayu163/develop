package com.yino.drudgery.mq.service;

import com.yino.drudgery.listener.ReceiveListener;

public interface MessageService {
	/**
	 * 发送消息
	 * @param obj
	 */
	void sendMsg(Object obj);
	
	/**
	 * 接收消息
	 *接收完成之后触发监听器
	 * @param obj
	 */
	void receiveMsg(Object obj);
	
	void addListener(ReceiveListener listener);
	
	void removeListener(ReceiveListener listener);
}
