package com.yino.drudgery.mq.service.impl;

import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import com.alibaba.fastjson.JSONObject;
import com.yino.drudgery.entity.Job;

public class MqMessageConsumer implements SessionAwareMessageListener<Message> {

	private static final Log log = LogFactory.getLog(MqMessageConsumer.class);
	@Autowired
	private MqMessageServiceImpl service;

	public synchronized void onMessage(Message message, Session session) {
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
			final String ms = msg.getText();
			
			Job job = JSONObject.parseObject(ms, Job.class);// 转换成相应的对象
			//Thread.sleep(1000);
			if (job == null) {
				return;
			}
			
			service.receiveMsg(job);
			
		} catch (Exception e) {
			log.error("==>", e);
		}
	}

	public MqMessageServiceImpl getService() {
		return service;
	}

	public void setService(MqMessageServiceImpl service) {
		this.service = service;
	}

	
}
