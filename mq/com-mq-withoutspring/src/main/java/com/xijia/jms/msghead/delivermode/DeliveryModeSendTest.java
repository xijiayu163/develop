package com.xijia.jms.msghead.delivermode;

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

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class DeliveryModeSendTest extends TestCase{
	private static String userName = "xijia";
	private static String password = "xijia";
	private static String brokerURL = "tcp://192.168.8.100:61616";

	private Session session;
	private Queue queue;
	private Connection connection;

	@Override
	protected void setUp() throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

//	 public DeliveryModeSendTest() throws JMSException{
//	 ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(userName,
//	 password, brokerURL);
//	 connection=factory.createConnection();
//	 connection.start();
//	
//	 queue=new ActiveMQQueue("testQueue");
//	 session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//	 }

	/**
	 * @param args
	 * @throws JMSException
	 */
	public static void main(String[] args) throws JMSException {
		DeliveryModeSendTest SendTest = new DeliveryModeSendTest();
		//SendTest.testJMSCorrelationID();
		
		SendTest.testJMSReplyTo();
	}

	/**
	 * 参考链接:http://activemq.apache.org/what-is-the-difference-between-persistent
	 * -and-non-persistent-delivery.html 测试消息发送模式，JMS规范
	 * 消息的发送模式包括DeliveryMode.NON_PERSISTENT和DeliveryMode.PERSISTENT两种
	 * 默认的消息发送模式是PERSISTENT 可以设置在MessageProducer，也可以为单独的message设置
	 * 如果使用PERSISTENT，消息将会持久化到硬盘或数据库.当broker(MQ节点)重启时，未被消费的消息将会重新放到队列中
	 * 如果使用NON_PERSISTENT,broker挂掉时，消息会丢失 测试步骤: 1
	 * 启动生产者，MQ管理界面会看到两条消息,一条是持久的，一条是非持久的 2 关闭掉mq服务 3 重新启动mq服务,会看到一条消息在pending
	 * messages栏目中，该条消息是持久的，非持久消息已经丢失(testQueue 1 0 0 0)
	 * 注：重新启动后，Enqueued和Dequeued栏目会被清空 4 启动消费者,打印记录显示只会消费一条消息
	 * 
	 * setJMSMessageID()在客户端设置是无效的，可以设置不传JMSMessageID以节省空间，但是不能主动设置
	 * 如下面设置了setJMSMessageID("xxx")，但在消费端看到的依然是MQ服务自动生成的.
	 * messageID:(ID:yuxijia-pc-6087-1489117829328-1:1:1:1:1) 默认的messageID
	 * 
	 * setJMSTimestamp()和setDisableMessageTimestamp()均有效
	 * 
	 * @author yuxijia
	 *
	 */
	private void testDeliveryMode() throws JMSException {
		// 创建一个消息的生产者
		MessageProducer producer = session.createProducer(queue);
		// 设置消息的DeliveryMode为Persistent
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		producer.setDisableMessageID(true);
		// producer.setDisableMessageTimestamp(true);

		TextMessage createTextMessage = session.createTextMessage("A persistent Message");
		createTextMessage.setJMSMessageID("xxx");
		// createTextMessage.setJMSTimestamp(111);
		producer.send(createTextMessage);
		// 设置消息的DeliveryMode为Non-Persistent
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		TextMessage createTextMessage2 = session.createTextMessage("A non persistent Message");
		// createTextMessage2.setJMSMessageID("yyy");
		producer.send(createTextMessage2);

		System.out.println("Send messages successfully!");
		connection.close();
	}

	/**
	 * 客户端能用JMSCorrelationID
	 * 头字段将一个消息同另一个消息相联接。一个典型的用法就是将一个响应消息同它的请求消息相连接,在请求响应模式中应用
	 * 
	 * 
	 * 
	 * 
	 * @throws JMSException
	 */
	private void testJMSCorrelationID() throws JMSException {
		MessageProducer producer = session.createProducer(queue);
		TextMessage createTextMessage = session.createTextMessage("A persistent Message");
		createTextMessage.setJMSCorrelationID("myCorrlationID");
		producer.send(createTextMessage);
		System.out.println("Send messages successfully!");
		connection.close();
	}

	/**
	 * JMSReplyTo
	 * 根据message.setJMSReplyTo(replyQueue);可以设置请求相应模式
	 * @throws JMSException
	 */
	@Test
	public void testJMSReplyTo() throws JMSException {
		// 消息发送到这个Queue
		Queue queue = new ActiveMQQueue("testQueue");
		// 消息回复到这个Queue
		Queue replyQueue = new ActiveMQQueue("replyQueue");

		// 创建一个消息，并设置它的JMSReplyTo为replyQueue。
		TextMessage message = session.createTextMessage("Andy");
		message.setJMSReplyTo(replyQueue);
		MessageProducer producer = session.createProducer(queue);


		// 消息的接收者
		MessageConsumer comsumer = session.createConsumer(queue);
		comsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					// 创建一个新的MessageProducer来发送一个回复消息。
					MessageProducer producer = session.createProducer(m.getJMSReplyTo());
					producer.send(session.createTextMessage("Hello " + ((TextMessage) m).getText()));
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
			}
		});

		// 这个接收者用来接收回复的消息
		MessageConsumer comsumer2 = session.createConsumer(replyQueue);
		comsumer2.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					System.out.println(((TextMessage) m).getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		producer.send(message);

		System.out.println("Send messages successfully!");
		connection.close();
	}
}
