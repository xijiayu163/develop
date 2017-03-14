package com.xijia.mq.features;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;

import junit.framework.TestCase;


public class FeatureTest extends TestCase {
	private static String userName = "xijia";
	private static String password = "xijia";

	private static String brokerURL = "failover:(tcp://192.168.8.88:61619,tcp://192.168.8.88:61617)?randomize=false";

	private ActiveMQConnectionFactory factory;
	private Session session;
	private Queue queue;
	private Connection connection;
	
	@Override
	protected void setUp() throws JMSException {
//		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
//		connection = factory.createConnection();
//		connection.start();
//
//		queue = new ActiveMQQueue("testQueue");
//		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}
	
	@Test
	public void testComposite() throws Exception{
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("test1Queue");
		queue = new ActiveMQQueue("test2Queue");
		queue = new ActiveMQQueue("test3Queue");
		Topic topic = new ActiveMQTopic("topic1");
		queue = new ActiveMQQueue("test1Queue,test2Queue,test3Queue,topic://topic1");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 100; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			producer.send(message);
		}

		Thread.sleep(100000);
	}
	
	/*----------------------测试虚拟主题--------------------------*/
	@Test
	public void testVirtualTopic_Producer() throws Exception{
		 ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(this.userName,this.password,"tcp://192.168.8.88:61619");  
	        Connection connection = factory.createConnection();  
	        connection.start();  
	        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);  
	        // 创建主题  
	        Topic topic = session.createTopic("VirtualTopic.TEST");  
	        MessageProducer producer = session.createProducer(topic);  
	        // NON_PERSISTENT 非持久化 PERSISTENT 持久化,发送消息时用使用持久模式  
	        producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
	        TextMessage message = session.createTextMessage();  
	        message.setText("topic 消息。");  
	        message.setStringProperty("property", "消息Property");  
	        // 发布主题消息  
	        producer.send(message);  
	        System.out.println("Sent message: " + message.getText());  
	        session.close();  
	        connection.close();  
	}
	
	@Test
	public void testVirtualTopic_Consumer() throws Exception{
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(this.userName,this.password,"tcp://192.168.8.88:61619");  
        Connection connection = factory.createConnection();  
        connection.start();  
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);  
        // 创建主题   
        Queue topicA = session.createQueue("Consumer.A.VirtualTopic.TEST");  
        Queue topicB = session.createQueue("Consumer.B.VirtualTopic.TEST");  
        // 消费者A组创建订阅  
        MessageConsumer consumerA1 = session.createConsumer(topicA);  
        consumerA1.setMessageListener(new MessageListener() {  
            // 订阅接收方法  
            public void onMessage(Message message) {  
                TextMessage tm = (TextMessage) message;  
                try {  
                    System.out.println("Received message A1: " + tm.getText()+":"+tm.getStringProperty("property"));  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
          
        MessageConsumer consumerA2 = session.createConsumer(topicA);  
        consumerA2.setMessageListener(new MessageListener() {  
            // 订阅接收方法  
            public void onMessage(Message message) {  
                TextMessage tm = (TextMessage) message;  
                try {  
                    System.out.println("Received message A2: " + tm.getText()+":"+tm.getStringProperty("property"));  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
          
        //消费者B组创建订阅  
        MessageConsumer consumerB1 = session.createConsumer(topicB);  
        consumerB1.setMessageListener(new MessageListener() {  
            // 订阅接收方法  
            public void onMessage(Message message) {  
                TextMessage tm = (TextMessage) message;  
                try {  
                    System.out.println("Received message B1: " + tm.getText()+":"+tm.getStringProperty("property"));  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
        MessageConsumer consumerB2 = session.createConsumer(topicB);  
        consumerB2.setMessageListener(new MessageListener() {  
            // 订阅接收方法  
            public void onMessage(Message message) {  
                TextMessage tm = (TextMessage) message;  
                try {  
                    System.out.println("Received message B2: " + tm.getText()+":"+tm.getStringProperty("property"));  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
        });
        Thread.sleep(100000);
//        session.close();  
//        connection.close();  
	}
	
	
	/*---------------------测试虚拟主题结束------------------------*/
	
	/*----------------------测试镜像队列--------------------------*/
	/**
	 * 服务器配置
	 * broker 增加配置seMirroredQueues=”true“
	 * 增加如下配置
	 * <destinationInterceptors>
    		<mirroredQueue copyMessage="true" postfix=".qmirror" prefix=""/>
		</destinationInterceptors>
	 * @throws Exception
	 */
	@Test
	public void testMirroredQueue() throws Exception{
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);

		Connection persistentConnection = factory.createConnection();
		persistentConnection.setClientID("clientid2");
		session = persistentConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		persistentConnection.start();
		Topic topicA = session.createTopic("myQueue.qmirror");  
		TopicSubscriber subscriber = session.createDurableSubscriber(topicA, "Subscriber");
		subscriber.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					Thread.sleep(1);
					String text = ((TextMessage) m).getText();
					System.out.println("日志监听:"+text);
				} catch (JMSException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("myQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 100; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			producer.send(message);
		}
		
		Thread.sleep(100000);
	}
	/*---------------------测试镜像队列结束------------------------*/
	
}
