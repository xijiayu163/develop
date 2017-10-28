package com.yu.jmx.notification2;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import com.yu.jmx.demo3.Hello;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class HelloAgent {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, 
InstanceAlreadyExistsException, MBeanRegistrationException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        ObjectName helloName = new ObjectName("MyMBean:name=HelloWorld");
        Hello hello = new Hello();
        server.registerMBean(hello,helloName);
        
        XiaoSi xs = new XiaoSi();
        server.registerMBean(xs,new ObjectName("MyMBean:name=xiaosi"));
        xs.addNotificationListener(new HelloListener(),null,hello);

        ObjectName adapterName = new ObjectName("MyBean:name=htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        server.registerMBean(adapter,adapterName);

        adapter.start();
    }
}
