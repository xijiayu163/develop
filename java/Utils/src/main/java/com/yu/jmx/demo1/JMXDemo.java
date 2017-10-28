package com.yu.jmx.demo1;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class JMXDemo {
	public static void main(String[] args) throws MalformedObjectNameException, InstanceAlreadyExistsException,
	MBeanRegistrationException, NotCompliantMBeanException, InterruptedException {
	MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
	ObjectName objectName = new ObjectName("Catalina:type=Connector,name=xxxx,port=8080");
	User user = new User();
	beanServer.registerMBean(user, objectName);
	String oldusername = null;
	String oldpassword = null;
	while(true){
		if(oldusername!=user.getUsername()||oldpassword!=user.getPassword()){
			System.out.println(user.getUsername()+","+user.getPassword());
			oldusername = user.getUsername();
			oldpassword= user.getPassword();
		}
		Thread.sleep(1000);
	}
}
}
