package com.yino.drudgery.mq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.yino.drudgery.entity.Job;
import com.yino.drudgery.mq.service.AbstractMsgService;


public class MqMessageServiceImpl extends AbstractMsgService{

	@Autowired
	private MqMessageProducer producer;
	
	public MqMessageServiceImpl()
	{
	}
	
	@Override
	public void sendMsg(Job job) {
		producer.sendMessage(job);
	}
}
