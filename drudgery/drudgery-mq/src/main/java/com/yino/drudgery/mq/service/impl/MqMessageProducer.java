package com.yino.drudgery.mq.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import com.alibaba.fastjson.JSONObject;
import com.yino.drudgery.entity.Job;

public class MqMessageProducer {

	@Autowired
	private JmsTemplate activeMqJmsTemplate;

	/**
	 * 发送消息.
	 * 
	 * @param user
	 */
	public synchronized void sendMessage(final Job job) {
		activeMqJmsTemplate.setExplicitQosEnabled(true);
		int priority = 4;
		if(job.getPriority()!=null)
		{
			priority = job.getPriority().getNum();
		}
		activeMqJmsTemplate.setPriority(priority);
		activeMqJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage createTextMessage = session.createTextMessage(JSONObject.toJSONString(job));
				if (job.getJobcfg() == null || job.getJobcfg().getJobGroupName() == null
						|| job.getJobcfg().getJobGroupName().isEmpty()) {
					createTextMessage.setStringProperty("groupName", job.getJobcfg().getJobGroupName());
				}
				return createTextMessage;
			}
		});
	}
}
