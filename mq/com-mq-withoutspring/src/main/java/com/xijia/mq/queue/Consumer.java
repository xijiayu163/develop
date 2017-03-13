package com.xijia.mq.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Consumer {
	private static String userName = "xijia";
	private static String password = "xijia";
	private static String brokerURL = "tcp://192.168.8.100:61616?jms.prefetchPolicy.all=1000";

	private Session session;
	private Connection connection;
	private ConnectionFactory factory;
	
	private static final Log log = LogFactory.getLog(Consumer.class);

	public Consumer() throws JMSException {
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public Session getSession() {
		return session;
	}

	public class Listener implements MessageListener {

		private String job;

		public Listener(String job) {
			this.job = job;
		}

		public void onMessage(Message message) {
			try {
				// do something here
				Thread.sleep(1000);
				log.error(job + " id:" + ((ObjectMessage) message).getObject()+"priority:"+ message.getJMSPriority());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws JMSException {
		Consumer consumer = new Consumer();
		Queue createQueue = consumer.getSession().createQueue("JOBS");
		MessageConsumer messageConsumer = consumer.getSession().createConsumer(createQueue);
		messageConsumer.setMessageListener(consumer.new Listener(Integer.toString(1)));
	}
}
