package com.yino.drudgery.mq.service.impl;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yino.drudgery.entity.Job;

@Service("mqMessageProducer")
public class MqMessageProducer {

	private ConnectionFactory connectionFactory;
	private Destination destination;
	private Session session;
	private MessageProducer producer;
	
	public MqMessageProducer(){}

	public void start() throws JMSException
	{
		Connection connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(destination);
		
	}
	
	
	public void sendMessage(Job job)
	{
		/*
		Message message = session.createObjectMessage(job);
		if(job.getJobcfg().getJobGroupName()=null && !groupName.isEmpty())
		message.setStringProperty("JMSXGroupID", groupName);
		producer.send(message, priority, 0, 0);
		*/
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
	

}
