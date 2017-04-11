package com.yino.drudgery.mq.service.impl;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.test.User;
import com.yino.drudgery.entity.Job;

@Service
public class MqMessageProducer {
	
	@Autowired
	private JmsTemplate activeMqJmsTemplate;

	/**
	 * 发送消息.
	 * @param user 
	 */
	public void sendMessage(final Job job) {
		activeMqJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage createTextMessage = session.createTextMessage(JSONObject.toJSONString(job));
				createTextMessage.setStringProperty("groupName", job.getJobcfg().getJobGroupName());
				return createTextMessage;
			}
		});
	}


}
