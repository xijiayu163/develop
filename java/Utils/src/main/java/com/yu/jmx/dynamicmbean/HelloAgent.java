package com.yu.jmx.dynamicmbean;

import com.sun.jdmk.comm.HtmlAdaptorServer;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class HelloAgent {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName helloName = new ObjectName("MyMBean:name=helloDynamic");
        HelloDynamic hello = new HelloDynamic();
        server.registerMBean(hello,helloName);

        ObjectName adapterName = new ObjectName("MyMBean:name=htmladapter");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        server.registerMBean(adapter,adapterName);
        adapter.start();
    }
}