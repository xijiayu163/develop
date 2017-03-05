package com.xijia.mq.pubsub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;


public class Publisher {
	private static String userName="xijia";
	private static String password="xijia";
	private static String brokerURL="tcp://192.168.8.101:61616";
	
	private Session session;
	private MessageProducer producer;
	private Destination[] destinations;
	private Connection connection;
	private ConnectionFactory factory;
	
	public static void main(String[] args) throws JMSException{
	
		args = new String[]{"topic1","topic2","topic3"};
		// Create publisher
		Publisher publisher = new Publisher();
	
		// Set topics
		publisher.setTopics(args);
	
		for (int i = 0; i < 10; i++) {
			publisher.sendMessage(args);
			System.out.println("Publisher '" + i + " price messages");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Close all resources
		publisher.close();
	
	}
	
	
	
    public Publisher() throws JMSException {  
    	factory = new ActiveMQConnectionFactory(userName,password,brokerURL);  
        connection = factory.createConnection();  
        try {  
        connection.start();  
        } catch (JMSException jmse) {  
            connection.close();  
            throw jmse;  
        }  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
        producer = session.createProducer(null);  
    }  
	    
    protected void setTopics(String[] stocks) throws JMSException {  
        destinations = new Destination[stocks.length];  
        for(int i = 0; i < stocks.length; i++) {  
            destinations[i] = session.createTopic("STOCKS." + stocks[i]);  
        }  
    }  
    
    protected void sendMessage(String[] stocks) throws JMSException {  
        for(int i = 0; i < stocks.length; i++) {  
            Message message = createStockMessage(stocks[i], session);  
            System.out.println("Sending: " + ((ActiveMQMapMessage)message).getContentMap() + " on destination: " + destinations[i]);  
            producer.send(destinations[i], message);  
        }  
    }  
    
    protected Message createStockMessage(String stock, Session session) throws JMSException {  
        MapMessage message = session.createMapMessage();  
        message.setString("stock", stock);  
        message.setDouble("price", 1.00);  
        message.setDouble("offer", 0.01);  
        message.setBoolean("up", true);  
              
        return message;  
    }  
    
    public void close() throws JMSException {  
        if (connection != null) {  
            connection.close();  
         }  
    }  
}
