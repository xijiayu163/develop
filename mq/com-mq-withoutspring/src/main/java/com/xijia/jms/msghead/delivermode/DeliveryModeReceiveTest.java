package com.xijia.jms.msghead.delivermode;

import javax.jms.Connection;  
import javax.jms.JMSException;  
import javax.jms.Message;  
import javax.jms.MessageConsumer;  
import javax.jms.MessageListener;  
import javax.jms.Queue;  
import javax.jms.Session;  
import javax.jms.TextMessage;  
  
import org.apache.activemq.ActiveMQConnectionFactory;  
import org.apache.activemq.command.ActiveMQQueue;  
  
public class DeliveryModeReceiveTest {  
	private static String userName = "xijia";
	private static String password = "xijia";
	private static String brokerURL = "tcp://192.168.8.100:61616";
  
    /** 
     * @param args 
     * @throws JMSException  
     */  
    public static void main(String[] args) throws JMSException {  
        // TODO Auto-generated method stub  
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(userName, password, brokerURL);
        Connection connection=factory.createConnection();  
        connection.start();  
          
        Queue queue=new ActiveMQQueue("testQueue");  
        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
        //创建消息的接收者，来接受DeliveryModeSendTest中发送的消息  
        MessageConsumer consumer=session.createConsumer(queue);  
        consumer.setMessageListener(new MessageListener(){  
  
            public void onMessage(Message m) {  
                // TODO Auto-generated method stub  
                try {  
                    System.out.println("Consumer get: "+((TextMessage)m).getText());  
                    String messageID = m.getJMSMessageID(); 
                    System.out.println("messageID:"+messageID+"	timestamp:"+m.getJMSTimestamp());
                    System.out.println("JMSCorrelationID:"+m.getJMSCorrelationID());
                } catch (JMSException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
              
        });  
    }  
  
}  