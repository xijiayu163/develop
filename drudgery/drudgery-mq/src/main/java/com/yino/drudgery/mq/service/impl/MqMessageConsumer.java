package com.yino.drudgery.mq.service.impl;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yino.drudgery.entity.Job;

@Service("mqMessageConsumer")
public class MqMessageConsumer implements MessageListener{

	private ConnectionFactory connectionFactory;
	private Destination destination;
	private String selector=null;
	
	@Autowired
	private MqMessageService service ;

	
	public MqMessageConsumer(){}
	
	public void start() throws JMSException
	{
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer messageConsumer = session.createConsumer(destination, selector);
		messageConsumer.setMessageListener(this);
	}
	
	@Override
	public void onMessage(Message message) {
		if(message instanceof ObjectMessage)
		{
			Object o=null;
			try {
				o = ((ObjectMessage)message).getObject();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(o!=null &&   o instanceof Job)
			{
				Job job = (Job)o;
				if(service!=null)
				{
					service.receiveMsg(job);
				}
			}
		}
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public MqMessageService getService() {
		return service;
	}

	public void setService(MqMessageService service) {
		this.service = service;
	}

	
}
