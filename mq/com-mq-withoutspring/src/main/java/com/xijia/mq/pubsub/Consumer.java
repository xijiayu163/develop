package com.xijia.mq.pubsub;

import java.text.DecimalFormat;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;


public class Consumer{
	private static String userName="xijia";
	private static String password="xijia";
	private static String brokerURL="tcp://192.168.8.101:61616";
	
	private Session session;
	private Connection connection;
	private ConnectionFactory factory;
	
	public static void main(String[] args) throws JMSException{
		args = new String[]{"topic1","topic2","topic3"};
		Consumer consumer = new Consumer();  
	    for (String stock : args) {  
		    Destination destination = consumer.getSession().createTopic("STOCKS." + stock);  
		    MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);  
		    messageConsumer.setMessageListener(consumer.new Listener());  
	    }  
	}
	
	
    public Consumer() throws JMSException {  
    	factory = new ActiveMQConnectionFactory(userName,password,brokerURL);  
        connection = factory.createConnection();  
        connection.start();  
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
    }  
	
    public Session getSession() {  
        return session;  
    }
    
    public class Listener implements MessageListener {  
        
        public void onMessage(Message message) {  
            try {  
                MapMessage map = (MapMessage)message;  
                String stock = map.getString("stock");  
                double price = map.getDouble("price");  
                double offer = map.getDouble("offer");  
                boolean up = map.getBoolean("up");  
                DecimalFormat df = new DecimalFormat( "#,###,###,##0.00" );  
                System.out.println(stock + "\t" + df.format(price) + "\t" + df.format(offer) + "\t" + (up?"up":"down"));  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}


