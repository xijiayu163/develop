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
	private static String userName="xijia";
	private static String password="xijia";
	private static String brokerURL="tcp://192.168.8.101:61616";
	
	private Session session;
	private MessageProducer producer;
	private Connection connection;
	private ConnectionFactory factory;
	
	
    public Publisher() throws JMSException {  
        factory = new ActiveMQConnectionFactory(userName,password,brokerURL);  
        connection = factory.createConnection();  
        connection.start();  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
        producer = session.createProducer(null);  
    }  
    
    public void sendMessage() throws JMSException {  
        for(int i = 0; i < 10; i++)  
        {   
            Destination destination = session.createQueue("JOBS." + i);  
            Message message = session.createObjectMessage(i);  
            System.out.println("Sending: id: " + ((ObjectMessage)message).getObject() + " on queue: " + destination);  
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
        for(int i = 0; i < 10; i++) {  
            publisher.sendMessage();  
            System.out.println("Published " + i + " job messages");  
        try {  
                Thread.sleep(1000);  
            } catch (InterruptedException ex) {  
            	ex.printStackTrace();  
            }  
        }  
        publisher.close();  
    }  
}
