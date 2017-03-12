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
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;

import junit.framework.TestCase;

public class DeliveryModeSendTest extends TestCase {
	private static String userName = "xijia";
	private static String password = "xijia";
	private static String brokerURL = "tcp://192.168.8.88:61616?jms.prefetchPolicy.all=1";

	private ActiveMQConnectionFactory factory;
	private Session session;
	private Queue queue;
	private Connection connection;

	@Override
	protected void setUp() throws JMSException {
		factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public DeliveryModeSendTest() throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connection = factory.createConnection();
		connection.start();

		queue = new ActiveMQQueue("testQueue");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	/**
	 * @param args
	 * @throws JMSException
	 */
	public static void main(String[] args) throws JMSException {
		DeliveryModeSendTest SendTest = new DeliveryModeSendTest();
		SendTest.testJMSCorrelationID();
		SendTest.testDeliveryMode();
		// SendTest.testJMSReplyTo();
	}

	/**
	 * 消息的持久属性
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
	@Test
	public void testJMSCorrelationID() throws JMSException {
		MessageProducer producer = session.createProducer(queue);
		TextMessage createTextMessage = session.createTextMessage("A persistent Message");
		createTextMessage.setJMSCorrelationID("myCorrlationID");
		producer.send(createTextMessage);
		System.out.println("Send messages successfully!");
		connection.close();
	}

	/**
	 * JMSReplyTo 根据message.setJMSReplyTo(replyQueue);可以设置请求相应模式
	 * 
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
	
	/**
	 * 默认的消息确认模式，当消费者从Receive或onMessage方法返回时，会话自动确认客户收到的消息
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void testJMSAUTO_ACKNOWLEDGE() throws JMSException, InterruptedException {
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
					System.out.println("JMSRedelivered:" + m.getJMSRedelivered() + "	" + text);
				} catch (JMSException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread.sleep(100000);
	}

	/**事务和自定义重传策略
	 * createSession的事务参数被设置为true时，第二个参数被忽略，默认为SESSION_TRANSACTED
	 * 消息生产者调用send()后必须调用commit,使消息提交到消息服务器
	 * 消息消费者消费消息后必须调用commit,使确认消息提交到消息服务器，如果不调用commit，消费者将会重新消费消息
	 * 确认是在消息层面上确认，即对单个消息确认,不同于CLIENT_ACKNOWLEDGE.
	 * 如果消费者处理发生异常，产生回滚，消息将会被服务器重新发送,同时设置JMSRedelivered=true,默认是重发6次,重发间隔为1秒.
	 * 打印结果如下 JMSRedelivered:false 0 JMSRedelivered:true 0 JMSRedelivered:true 0
	 * JMSRedelivered:false 1 JMSRedelivered:true 1 JMSRedelivered:true 1
	 * JMSRedelivered:false 2 JMSRedelivered:true 2 JMSRedelivered:true 2
	 * 需要注意的是消息的生产和消费不能包含在同一个事务中
	 * 
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void testJMSTransactionAndCustomRediveryPolicy() throws JMSException, InterruptedException {
		session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

		// 默认重发配置在这里可以看到
		// RedeliveryPolicyMap redeliveryPolicyMap =
		// ((ActiveMQConnection)connection).getRedeliveryPolicyMap();

		RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
		queuePolicy.setInitialRedeliveryDelay(0);
		queuePolicy.setRedeliveryDelay(1000);
		queuePolicy.setMaximumRedeliveries(2);// 若设为-1表示无限次
		queuePolicy.setUseExponentialBackOff(false);
		RedeliveryPolicyMap map = ((ActiveMQConnection) connection).getRedeliveryPolicyMap();
		map.put((ActiveMQQueue) queue, queuePolicy);

		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 100; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			producer.send(message);
		}
		session.commit();

		// 消息的接收者
		MessageConsumer comsumer = session.createConsumer(queue);
		comsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					Thread.sleep(1000);
					String text = ((TextMessage) m).getText();
					// 如果将该行注释，则会收到重复的消息，消息消费后，在管控台上还是入队状态
					// session.commit();
					session.rollback();
					System.out.println("JMSRedelivered:" + m.getJMSRedelivered() + "	" + text);
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
	 * 客户端确认类型,消费者调用acknowledge确认消息 该方法是在会话层面上生效，确认一个被消费的消息将自动确认所有已被会话消费的消息
	 * 例如:如果一个消息消费者消费了10个消息，然后确认第5个消息，那么所有10个消息都会被确认
	 * 
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void testJMSCLIENT_ACKNOWLEDGE() throws JMSException, InterruptedException {
		session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
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
					Thread.sleep(100);
					String text = ((TextMessage) m).getText();
					// 如果将该行注释，则会收到重复的消息，消息消费后，在管控台上还是入队状态
					// 注意：该方法是在会话层面上生效，确认一个被消费的消息将自动确认所有已被会话消费的消息
					// 例如:如果一个消息消费者消费了10个消息，然后确认第5个消息，那么所有10个消息都会被确认
					m.acknowledge();
					System.out.println("JMSRedelivered:" + m.getJMSRedelivered() + "	" + text);
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
	 * 消息确认类型,调用msg.acknowledge()该只会对当前消息有效,没有调用的消息将会被重复消费
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void testINDIVIDUAL_ACKNOWLEDGE() throws JMSException, InterruptedException{
		session = connection.createSession(false, ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE);
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
					Thread.sleep(100);
					String text = ((TextMessage) m).getText();
					if(Integer.valueOf(text)==5){
						// 调用该只会对当前消息有效,没有调用的消息将会被重复消费
						m.acknowledge();
					}
					System.out.println("JMSRedelivered:" + m.getJMSRedelivered() + "	" + text);
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
	 * 消息选择器测试
	 * 
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void testMsgSelector() throws JMSException, InterruptedException {
		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 20; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			message.setIntProperty("value", i);
			producer.send(message);
		}

		// 消息的接收者
		MessageConsumer comsumer = session.createConsumer(queue, "value>10");
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

		// 消息的接收者
		MessageConsumer comsumer2 = session.createConsumer(queue, "value<10");
		comsumer2.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					Thread.sleep(1000);
					String text = ((TextMessage) m).getText();
					System.out.println("consumer2:" + text);
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
	 * 多个消费者同时消费
	 * 
	 * @throws JMSxception
	 * @throws InterruptedException
	 */
	@Test
	public void testMultiConsumer() throws JMSException, InterruptedException {
		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 200; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			producer.send(message);
		}

		// 消息的接收者1
		new Thread(new Runnable() {
			public void run() {
				try {
					MessageConsumer comsumer1 = session.createConsumer(queue);
					comsumer1.setMessageListener(new MessageListener() {
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
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}).start();

		// 消息的接收者2
		new Thread(new Runnable() {
			public void run() {
				try {
					MessageConsumer comsumer2 = session.createConsumer(queue);
					comsumer2.setMessageListener(new MessageListener() {
						public void onMessage(Message m) {
							try {
								Thread.sleep(1000);
								String text = ((TextMessage) m).getText();
								System.out.println("consumer2:" + text);
							} catch (JMSException e1) {
								e1.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}).start();

		Thread.sleep(100000);
	}

	/**
	 * 独家消费者 参考链接:http://activemq.apache.org/exclusive-consumer.html
	 * 当有多个消费者时，设置排它消费可以保证消息的顺序处理，如果正在使用的消费端终止，则自动故障转移到另一个消费者,实现高可用
	 * 
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void testExclusiveConsumer() throws JMSException, InterruptedException {
		queue = new ActiveMQQueue("testQueue?consumer.exclusive=true");
		// 消息发送者
		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 200; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			message.setJMSPriority(1);
			producer.send(message);
		}

		// 消息的接收者1
		new Thread(new Runnable() {
			public void run() {
				try {
					final MessageConsumer comsumer1 = session.createConsumer(queue);
					comsumer1.setMessageListener(new MessageListener() {
						public void onMessage(Message m) {
							try {
								Thread.sleep(1000);
								String text = ((TextMessage) m).getText();
								System.out.println(text);
								if (Integer.valueOf(text).intValue() > 3) {
									comsumer1.close();
								}
							} catch (JMSException e1) {
								e1.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}).start();

		// 消息的接收者2
		new Thread(new Runnable() {
			public void run() {
				try {
					MessageConsumer comsumer2 = session.createConsumer(queue);
					comsumer2.setMessageListener(new MessageListener() {
						public void onMessage(Message m) {
							try {
								Thread.sleep(1000);
								String text = ((TextMessage) m).getText();
								System.out.println("consumer2:" + text);

							} catch (JMSException e1) {
								e1.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		}).start();

		Thread.sleep(100000);
	}

	/**
	 * JMSPriority 头字段包含了消息的优先级。在消息被发送的时候，这个字段被忽略，当消
	 * 息发送完成后，它持有了发送方法指定的值。JMS定义了10级的优先级，0作为最低级，9
	 * 是最高级。除此之外，客户端可以认为0-4级是普通优先级，而5-9作为加速优先级 qSender.send(msg2,
	 * DeliveryMode.PERSISTENT, 1, 30000) 可使用选择器来代替优先级做处理
	 * 需要在producer上面去设置优先级才会生效
	 * 发送时指定过期时间
	 */
	@Test
	public void testMsgPriority() throws JMSException, InterruptedException {
		// 消息发送
		MessageProducer producer = session.createProducer(queue);
		for (int i = 0; i < 200; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			// message.setJMSPriority(i%9);
			producer.send(message, DeliveryMode.PERSISTENT, i % 9, 3000000);
		}

		// 消费者
		MessageConsumer comsumer = session.createConsumer(queue);
		comsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					Thread.sleep(1000);
					String text = ((TextMessage) m).getText();
					System.out.println(text + "	" + m.getJMSPriority());

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
	 * 消息权重
	 */
	@Test
	public void testMsgWeight() {

	}

	/**
	 * 消息生产者在发送非持久消息时默认是使用的同步机制
	 * 消息生产者使用持久（persistent）传递模式发送消息的时候，Producer.send() 方法会被阻塞，
	 * 直到 broker 发送一个确认消息给生产者，这个确认消息暗示生产者 broker 
	 * 已经成功地将它发送的消息路由到目标目的并把消息保存到二级存储中。这个过程通常称为同步发送。
	 * 但有一个例外，当发送方法在一个事物上下文中时，被阻塞的是 commit 方法而不是 send 方法。
	 * commit 方法成功返回意味着所有的持久消息都以被写到二级存储中。
	 * 可在容忍部分消息丢失的前提下开启异步传送机制，从而提高效率
	 * 设置 tcp://localhost:61616?jms.useAsyncSend=true的方式将会启用异步机制
	 * 
	 * @throws JMSException
	 */
	@Test
	public void testSyncSendMsg() throws JMSException {
		
	}

	/**
	 * 消息异步传送 消费者注册消息监听器，调用onMessage方法监听
	 */
	@Test
	public void testAsyncSendMsg() {

	}
	
	/**
	 * temporaryQueue 是为Connection或者QueueConnection持久化而创建的唯一的Queue对
	象，它由“系统定义”的且只能被创建它的Connection或者QueueConnection所消费。
	 * @throws JMSException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testTemporaryQueue() throws JMSException, InterruptedException{
		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer(queue);
		TemporaryQueue temporaryQueue = session.createTemporaryQueue();
		
		for (int i = 0; i < 200; i++) {
			TextMessage message = session.createTextMessage(String.valueOf(i));
			message.setJMSReplyTo(temporaryQueue);
			producer.send(message);
		}

		// 消费者,收到消息后发送响应
		final MessageProducer replyProducer = session.createProducer(null);  
		MessageConsumer comsumer = session.createConsumer(queue);
		comsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					Thread.sleep(1000);
					String text = ((TextMessage) m).getText();
					System.out.println(text + "	" + m.getJMSPriority());
					TextMessage response = session.createTextMessage(text);  
					replyProducer.send(m.getJMSReplyTo(), response);

				} catch (JMSException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		//生产者收到响应消息
		MessageConsumer responseConsumer = session.createConsumer(temporaryQueue);  
		responseConsumer.setMessageListener(new MessageListener() {
			public void onMessage(Message m) {
				try {
					Thread.sleep(1000);
					String text = ((TextMessage) m).getText();
					System.out.println("response:" + text);
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
	 * 持久订阅者
	 * 订阅者默认传送模式是非持久的
	 * 测试步骤:
	 * 1 先启动订阅者,会向MQ服务器订阅 
	 * 2 关闭订阅者,模拟测试离线访问的消息不会丢失
	 * 3 启动发布者，发布消息，MQ服务器检测是否有持久的订阅者，如果有，消息会被存储下来，如果没有，消息会被丢弃
	 * 4 关闭发布者 
	 * 5 再次启动订阅者，消费持久存储的消息 
	 * @throws JMSException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testPersistentSubscribe() throws JMSException, InterruptedException{
		//发布者
//		Topic topic = (Topic)session.createTopic("myTopic");
//		MessageProducer producer = session.createProducer(topic);
//		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//		
//		for (int i = 0; i < 200; i++) {
//			TextMessage message = session.createTextMessage(String.valueOf(i));
//			producer.send(message);
//		}
		
		//订阅者
		Connection clientConnection= factory.createConnection();
		clientConnection.setClientID("clientid2");
		clientConnection.start();
		Session subscribeSession = clientConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic subscribeTopic = (Topic)subscribeSession.createTopic("myTopic");
		TopicSubscriber subscriber = subscribeSession.createDurableSubscriber(subscribeTopic, "Subscriber");
		subscriber.setMessageListener(new MessageListener() {
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
//		
		
		Thread.sleep(100000);
	}

	/**
	 * 非持久订阅者
	 * 按持久订阅者的测试步骤，订阅者再次启动后并不能消费消息
	 * 
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	@Test
	public void testNonPersistentSubscribe() throws JMSException, InterruptedException{
		//发布者
//		Topic topic = (Topic)session.createTopic("myTopic");
//		MessageProducer producer = session.createProducer(topic);
//		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
//		
//		for (int i = 0; i < 200; i++) {
//			TextMessage message = session.createTextMessage(String.valueOf(i));
//			producer.send(message);
//		}
		
		//订阅者
		Connection clientConnection= factory.createConnection();
		clientConnection.setClientID("clientid2");
		clientConnection.start();
		Session subscribeSession = clientConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic subscribeTopic = (Topic)subscribeSession.createTopic("myTopic");
		MessageConsumer consumer = subscribeSession.createConsumer(subscribeTopic);
		consumer.setMessageListener(new MessageListener() {
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
//		
		
		Thread.sleep(100000);
	}
}
