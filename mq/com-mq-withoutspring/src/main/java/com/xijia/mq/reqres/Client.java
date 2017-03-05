package com.xijia.mq.reqres;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Client implements MessageListener{
	private static String userName="xijia";
	private static String password="xijia";
	private static String brokerURL="tcp://192.168.8.101:61616";
	private static String clientQueueName="clientQueueName";
	
	private MessageProducer producer;
	public Client() {  
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName,password,brokerURL);  
        Connection connection;  
        try {  
            connection = connectionFactory.createConnection();  
            connection.start();  
            //true 支持事务
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);  
            Destination adminQueue = session.createQueue(clientQueueName);  
  
            //Setup a message producer to send message to the queue the server is consuming from  
            this.producer = session.createProducer(adminQueue);  
            this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);  
  
            //Create a temporary queue that this client will listen for responses on then create a consumer  
            //that consumes message from this temporary queue...for a real application a client should reuse  
            //the same temp queue for each message to the server...one temp queue per client  
            Destination tempDest = session.createTemporaryQueue();  
            MessageConsumer responseConsumer = session.createConsumer(tempDest);  
  
            //This class will handle the messages to the temp queue as well  
            responseConsumer.setMessageListener(this);  
  
            //Now create the actual message you want to send  
            TextMessage txtMessage = session.createTextMessage();  
            txtMessage.setText("MyProtocolMessage");  
  
            //Set the reply to field to the temp queue you created above, this is the queue the server  
            //will respond to  
            txtMessage.setJMSReplyTo(tempDest);  
  
            //Set a correlation ID so when you get a response you know which sent message the response is for  
            //If there is never more than one outstanding message to the server then the  
            //same correlation ID can be used for all the messages...if there is more than one outstanding  
            //message to the server you would presumably want to associate the correlation ID with this  
            //message somehow...a Map works good  
            String correlationId = this.createRandomString();  
            txtMessage.setJMSCorrelationID(correlationId);  
            this.producer.send(txtMessage);  
        } catch (JMSException e) {  
            //Handle the exception appropriately  
        }  
    }
	public void onMessage(Message message) {
		String messageText = null;  
        try {  
            if (message instanceof TextMessage) {  
                TextMessage textMessage = (TextMessage) message;  
                messageText = textMessage.getText();  
                System.out.println("messageText = " + messageText);  
            }  
        } catch (JMSException e) {  
        }  
		
	} 
	
	private String createRandomString(){
		return UUID.randomUUID().toString();
	}
	
	public static void main(String[] args){
		Client client = new Client();
		Server server = new Server();
	}


//createSession(paramA,paramB);
//
//paramA 取值有 : true or false 表示是否支持事务
//paramB 取值有:Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE，SESSION_TRANSACTED
//
//createSession(paramA,paramB);
//paramA是设置事务的，paramB设置acknowledgment mode
//paramA设置为false时：paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
//paramA设置为true时：paramB的值忽略， acknowledgment mode被jms服务器设置为SESSION_TRANSACTED 。
//Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。
//Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。
//DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。

}
