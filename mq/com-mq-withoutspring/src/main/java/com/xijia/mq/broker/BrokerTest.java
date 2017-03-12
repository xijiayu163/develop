package com.xijia.mq.broker;

import java.net.URI;
import java.net.URISyntaxException;

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
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;

import junit.framework.TestCase;

public class BrokerTest extends TestCase {
	private static String userName = "xijia";
	private static String password = "xijia";
	private static String brokerURL = "tcp://localhost:61616";

	private ActiveMQConnectionFactory factory;
	private Session session;
	private Queue queue;
	private Connection connection;
	private BrokerService broker;

	@Override
	public void tearDown() throws Exception {
		// sendAndReceive();
	}

	public void sendAndReceive() throws Exception {

		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		TextMessage message = session.createTextMessage(String.valueOf(111));
		producer.send(message);

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
	 * 测试用本机的Broker
	 * 
	 * @throws Exception
	 */
	@Test
	public void testStartBroker() throws Exception {
		BrokerService broker = new BrokerService();
		broker.setUseJmx(true);

		broker.addConnector(brokerURL);
		broker.start();
		factory = new ActiveMQConnectionFactory(brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	@Test
	public void testReadProperties() throws URISyntaxException, Exception {
		String uri = "properties:mq.properties";
		broker = BrokerFactory.createBroker(new URI(uri));
		broker.addConnector(brokerURL);
		broker.start();
		factory = new ActiveMQConnectionFactory(brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		System.out.println("fdsfdsfd");
	}

	@Test
	public void testSSL() throws JMSException {
		brokerURL = "ssl://192.168.8.88:61617";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	@Test
	public void testNIO() throws JMSException {
		brokerURL = "nio://192.168.8.88:61618";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	@Test
	public void testMultiBroker() throws JMSException {
		brokerURL = "tcp://192.168.8.88:61619";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		brokerURL = "tcp://192.168.8.88:61616";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	/**
	 * 多个Broker静态网络桥接（默认单向）
	 * 环境配置步骤
	 * 1 在conf/activemq.xml中新增配置如下( 根据上节增加了一个节点的前提下配置)：如果有密码需要设置密码
	 * <networkConnectors>
        	<networkConnector name="local network" userName="xijia" password="xijia" uri="static://(tcp://192.168.8.88:61616,tcp://192.168.8.88:61617)"/>
    	</networkConnectors>
       2 分别启动activemq2和activemq
       	实现原理：
       networkConnector的实现原理是基于ActiveMQ的公告消息（AdvisoryMessage）机制的（参见此处）。当broker2通过networkConnector、duplex方式指向broker1时，发生了什么呢？
		假定broker1已经启动，这时候broker2开始启动。
		1.         broker2先启动自己的connector
		2.         然后使用一个vm的connector，创建一个connection，把自己作为一个client，连接到broker1。
		3.         通过订阅Advisory Message，拿到相互的Consumer与相应的Queue列表。
		至此，双方建立关系。
		将duplex设为true,可测试双向链接
		在不设置duplex为true的情况下，消息基本都被消费者2消费掉了
		
		消息回流测试
		在两个Broker的activemq.xml中增加如下配置
		<policyEntry queue=">" enableAudit="false">
                        <networkBridgeFilterFactory>
                            <conditionalNetworkBridgeFilterFactory replayWhenNoConsumers="true" />
                         </networkBridgeFilterFactory>
            </policyEntry> 
        测试步骤:
        1 启动生产者,向Broker1发送消息
        2 启动消费者2，Broker2会将消息全部接过去并让消费者2消费
        3 停掉Broker2，停掉消费者2
        4 启动Broker2,启动消费者1，Broker2会将消息回流到Broker1让消费者1消费
        
   	测试 
   	先启动两个消费者，再启动发送者发送消息，观察消费情况，两个消费者达到了负载均衡。待解释。
   	直接启动发送者再启动两个消费者，结果发现基本让消费者2消费了。(一般连接到BrokerB的消费者会优先消费，
   	可以将BrokerB看做BrokerA的一个特殊的消费者，BrokerA一有消息就会转到BrokerB中去了,
   	默认的配置对负载均衡不利,即使设置为双向也不行)
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void testStaticNetworkConnector() throws JMSException, InterruptedException {
		brokerURL = "tcp://192.168.8.88:61619?jms.prefetchPolicy.all=1";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for(int i=0;i<100;i++){
			TextMessage message = session.createTextMessage(String.valueOf(i));
			producer.send(message);
		}
		
		//主Broker的消费者1
		new Thread(new Runnable() {
			public void run() {
				try {
					queue = new ActiveMQQueue("testQueue");
					session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					MessageConsumer comsumer = session.createConsumer(queue);
					comsumer.setMessageListener(new MessageListener() {
						public void onMessage(Message m) {
							try {
								Thread.sleep(1000);
								String text = ((TextMessage) m).getText();
								System.out.println("主Broker:"+text);
							} catch (JMSException e1) {
								e1.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (JMSException e2) {
					e2.printStackTrace();
				}
			}
		}).start();
		

		// 另一个Broker消息消费者2
		brokerURL = "tcp://192.168.8.88:61617?jms.prefetchPolicy.all=1";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		new Thread(new Runnable() {
			public void run() {
				try {
					ActiveMQQueue queue2 = new ActiveMQQueue("testQueue");
					Session session2 = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					MessageConsumer comsumer = session2.createConsumer(queue2);
					comsumer.setMessageListener(new MessageListener() {
						public void onMessage(Message m) {
							try {
								Thread.sleep(1000);
								String text = ((TextMessage) m).getText();
								System.out.println("从Broker:"+text);
							} catch (JMSException e1) {
								e1.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (JMSException e2) {
					e2.printStackTrace();
				}
			}
		}).start();

		Thread.sleep(100000);
	}
	
	@Test
	public void testSend() throws JMSException{
		brokerURL = "tcp://192.168.8.88:61617?jms.prefetchPolicy.all=1";
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for(int i=0;i<100;i++){
			TextMessage message = session.createTextMessage(String.valueOf(i));
			producer.send(message);
		}
	}
}
