package com.yino.drudgery.mq.service.impl;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.mq.service.AbstractMsgService;


@Service
public class MqMessageService extends AbstractMsgService{

	@Autowired
	private MqMessageConsumer consumer;
	@Autowired
	private MqMessageProducer producer;
	
	public MqMessageService()
	{
	}
	
	@Override
	public void sendMsg(Job job) {
		// TODO Auto-generated method stub
		producer.sendMessage(job);
	}
}
