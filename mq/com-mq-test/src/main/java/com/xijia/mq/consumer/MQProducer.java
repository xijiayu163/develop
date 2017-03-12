package com.xijia.mq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;



/**
 * 参考资料http://docs.spring.io/spring/docs/3.0.6.RELEASE/spring-framework-reference/html/jms.html
 * @author xijia
 *
 */
@Service("mqProducer")
public class MQProducer {
	
	@Autowired
	private JmsTemplate activeMqJmsTemplate;

	/**
	 * 发送消息.
	 * @param user 
	 */
	public void sendMessage(final User user) {
		activeMqJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage createTextMessage = session.createTextMessage(JSONObject.toJSONString(user));
				createTextMessage.setStringProperty("JMSXGroupID", "IBM_NASDAQ_20/4/05");
				return createTextMessage;
			}
		});
	}

}
