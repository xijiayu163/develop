package com.yino.drudgery.mq.service.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.test.ConsumerSessionAwareMessageListener;
import com.test.User;

@Service
public class MqMessageConsumer implements SessionAwareMessageListener<Message> {

	private static final Log log = LogFactory.getLog(ConsumerSessionAwareMessageListener.class);

	@Autowired
	private JmsTemplate activeMqJmsTemplate;
	@Autowired
	private Destination sessionAwareQueue;

	private MqMessageService service;

	public synchronized void onMessage(Message message, Session session) {
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
			final String ms = msg.getText();
			
			User user = JSONObject.parseObject(ms, User.class);// 转换成相应的对象
			//Thread.sleep(1000);
			if (user == null) {
				return;
			}
			log.error("listen:"+1+"==>receive message:" + user.getUserId());
			try {
			} catch (Exception e) {
				
				
				
				// 发送异常，重新放回队列
				activeMqJmsTemplate.send(sessionAwareQueue, new MessageCreator() {
					public Message createMessage(Session session) throws JMSException {
						return session.createTextMessage(ms);
					}
				});
				log.error("==>MailException:", e);
			}
		} catch (Exception e) {
			log.error("==>", e);
		}
	}

	public MqMessageService getService() {
		return service;
	}

	public void setService(MqMessageService service) {
		this.service = service;
	}

	
}
