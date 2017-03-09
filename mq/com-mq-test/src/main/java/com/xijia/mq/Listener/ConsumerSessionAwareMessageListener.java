
package com.xijia.mq.Listener;

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
import org.springframework.stereotype.Component;


import com.alibaba.fastjson.JSONObject;
import com.xijia.mq.consumer.User;


public class ConsumerSessionAwareMessageListener implements SessionAwareMessageListener<Message> {

	private static final Log log = LogFactory.getLog(ConsumerSessionAwareMessageListener.class);

	@Autowired
	private JmsTemplate activeMqJmsTemplate;
	@Autowired
	private Destination sessionAwareQueue;
	
	private int identity=-1;

	public void setIndenty(int indenty) {
		this.identity = indenty;
	}

	public synchronized void onMessage(Message message, Session session) {
		try {
			ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
			final String ms = msg.getText();
			
			User user = JSONObject.parseObject(ms, User.class);// 转换成相应的对象
			Thread.sleep(1000);
			if (user == null) {
				return;
			}
			log.error("listen:"+identity+"==>receive message:" + user.getUserId());
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



}
