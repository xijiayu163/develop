package com.yu.jmx.modelmbean.commonsmodeler;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import org.apache.commons.modeler.ManagedBean;
import org.apache.commons.modeler.Registry;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import java.io.InputStream;

public class HelloAgent  {
    public static void main(String[] args) throws Exception {
// 需要将xml信息读入到Registry对象中
        Registry registry = Registry.getRegistry(null,null);
        InputStream stream = HelloAgent.class.getResourceAsStream("mbeans-descriptors.xml");
        registry.loadMetadata(stream);
        MBeanServer server = registry.getMBeanServer();

// 之前是：MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ManagedBean managed = registry.findManagedBean("Hello");
        ObjectName helloName = new ObjectName(managed.getDomain()+":name=HelloWorld");
// 以前是Hello hello = new Hello(); 为什么要多个createMBean？因为现在的写法没有写MBean,所以才要动态生成一个，以前就直接
// 把new hello()注册到MBeanServer就可以了，server会自动找到它的HelloMBean接口文件。
        ModelMBean hello = managed.createMBean(new Hello());
        server.registerMBean(hello,helloName);

        ObjectName adapterName = new ObjectName(managed.getDomain()+":name = htmladapter,port=8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        server.registerMBean(adapter,adapterName);
        adapter.start();
    }
    
    
}
