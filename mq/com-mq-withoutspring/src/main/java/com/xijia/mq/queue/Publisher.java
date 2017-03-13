package com.xijia.mq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {
	private static String userName = "xijia";
	private static String password = "xijia";
	private static String brokerURL = "tcp://192.168.8.100:61616";

	private Session session;
	private MessageProducer producer;
	private Connection connection;
	private ConnectionFactory factory;

	public Publisher() throws JMSException {
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);
	}

	public void sendMessage() throws JMSException {
		for (int i = 0; i < 30; i++) {
			Destination destination = session.createQueue("JOBS");
			Message message = session.createObjectMessage(i);
			if(i%2==0){
				message.setStringProperty("JMSXGroupID", "A");
			}else{
				message.setStringProperty("JMSXGroupID", "B");
			}
			//设置优先级
			//在服务器增加配置:<policyEntry queue=">" prioritizedMessages="true" useCache="false" expireMessagesPeriod="0" queuePrefetch="1" />
			producer.setPriority(i%10);
			producer.send(destination, message);
		}
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

	public static void main(String[] args) throws JMSException {
		Publisher publisher = new Publisher();
		publisher.sendMessage();
		System.out.println("Published job messages");
		publisher.close();
	}
}
