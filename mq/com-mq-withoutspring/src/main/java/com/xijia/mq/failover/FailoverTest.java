package com.xijia.mq.failover;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;

import junit.framework.TestSuite;

public class FailoverTest extends TestSuite {
	private static String userName = "xijia";
	private static String password = "xijia";

	// 默认情况下randomize=true，故障切换传输选择随机的URI。这有效地在多个代理上平衡客户端，达到负载均衡的效果
	// randomize=false使客户端首先连接到主节点，并在主节点不可用时只连接到辅助备份代理
	//
	//private static String brokerURL = "failover:(tcp://192.168.8.88:61619,tcp://192.168.8.88:61617)?randomize=false";
	private static String brokerURL = "tcp://192.168.8.88:61617";

	private ActiveMQConnectionFactory factory;
	private Session session;
	private Queue queue;
	private Connection connection;

	/**
	 * 故障转移 1 启动Broker2,Broker1不启动，默认连接Broker1，结果会自动故障转移到Broker2
	 * 
	 * @throws Exception
	 */
	@Test
	public void TestFailOver() throws Exception {
		brokerURL = "failover:(tcp://192.168.8.88:61619,tcp://192.168.8.88:61617?)randomize=false";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();
		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 100; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			producer.send(message);
		}

		// 消息的接收者
		MessageConsumer comsumer = session.createConsumer(queue);
		comsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					Thread.sleep(1000);
					String text = ((TextMessage) m).getText();
					System.out.println(text);
				} catch (JMSException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread.sleep(100000);
	}

	/**
	 * 负载均衡 让同一个session创建
	 * 设置步骤
	 * 1 jms.prefetchPolicy.all=1 表示MQ服务器只给服务端推一个消息
	 * 2 failover模式这里不起作用
	 * 3  在activemq.xml静态链接配置处将 conduitSubscriptions="false"
	 * 4 randomize设为false
	 * 
	 * 发送的负载均衡，注释掉消费的代码，运行用例多次，观察结果，只运行一次，在一次里发送10000条消息，再观察结果
	 * 问题：测试发现，消费者1不仅消费了Broker1的消息，也消费了Broker2的消息,按理，消费者1只消费Broker1上的Pending消息，
	 * 消费者2只消费Broker2上的Pending消息
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void TestLoadBalance() throws JMSException, InterruptedException {
		// 消息接收者
		brokerURL = "failover:(tcp://192.168.8.88:61619,tcp://192.168.8.88:61617)?randomize=true&jms.prefetchPolicy.all=1";
		//brokerURL = "tcp://192.168.8.88:61619?jms.prefetchPolicy.all=1";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();
		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		for(int i=0;i<1;i++){
			MessageConsumer comsumer1 = session.createConsumer(queue);
			comsumer1.setMessageListener(new MessageListener() {
				public void onMessage(Message m) {
					try {
						Thread.sleep(1000);
						String text = ((TextMessage) m).getText();
						System.out.println("消费者1:"+text);
					} catch (JMSException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		

		// 消息接收者2
		brokerURL = "failover:(tcp://192.168.8.88:61619,tcp://192.168.8.88:61617)?randomize=true&jms.prefetchPolicy.all=1";
		//brokerURL = "tcp://192.168.8.88:61617?jms.prefetchPolicy.all=1";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();
		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		for(int i=0;i<1;i++){
			Queue queue2 = new ActiveMQQueue("testQueue");
			MessageConsumer comsumer2 = session.createConsumer(queue2);
			comsumer2.setMessageListener(new MessageListener() {
				public void onMessage(Message m) {
					try {
						Thread.sleep(1000);
						String text = ((TextMessage) m).getText();
						System.out.println("消费者2:"+text);
					} catch (JMSException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
//		
//		Thread.sleep(1000);
		// 消息发送者
//		brokerURL = "failover:(tcp://192.168.8.88:61619,tcp://192.168.8.88:61617)?randomize=true";
//		//brokerURL = "tcp://192.168.8.88:61619?jms.prefetchPolicy.all=1";
//		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
//		connection = factory.createConnection();
//		connection.start();
//		queue = new ActiveMQQueue("testQueue");
//		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		MessageProducer producer = session.createProducer(queue);
//		for (int i = 0; i < 10000; i++) {
//			TextMessage message = session.createTextMessage(String.valueOf(i));
//			producer.send(message);
//		}
		Thread.sleep(100000);
	}

}
