package org.shirdrn.spring.remote.rmi;  
  
import java.io.FileNotFoundException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;  
  
public class RmiServer {  
	static {
        try {
			Log4jConfigurer.initLogging("classpath:log4j.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  
    public static void main(String[] args) throws InterruptedException {  
        ClassPathXmlApplicationContext ctx = 
        		new ClassPathXmlApplicationContext("classpath:server.xml");  
          
        Object lock = new Object();  
        synchronized (lock) {  
            lock.wait();  
        }  
        
        //ctx.close();
    }  
} 