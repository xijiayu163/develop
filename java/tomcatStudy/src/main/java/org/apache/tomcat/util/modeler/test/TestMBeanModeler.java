package org.apache.tomcat.util.modeler.test;

import java.io.InputStream;

import javax.management.DynamicMBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.Registry;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class TestMBeanModeler {
	public static class Hello { // 注意这里没有implements任何东西
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void printHello() {
			System.out.println("Hello world, " + name);
		}

		public void printHello(String whoName) {
			System.out.println("Hello, " + whoName);
		}
	}

	public static void main(String[] args) throws Exception {
		// 需要将xml信息读入到Registry对象中
		Registry registry = Registry.getRegistry(null, null);
		InputStream stream = TestMBeanModeler.class.getResourceAsStream("mbeans-descriptors.xml");
		registry.loadMetadata(stream);
		MBeanServer server = registry.getMBeanServer();

		// 之前是：MBeanServer server = ManagementFactory.getPlatformMBeanServer();
		ManagedBean managed = registry.findManagedBean("Hello");
		ObjectName helloName = new ObjectName(managed.getDomain() + ":name=HelloWorld");
		// 以前是Hello hello = new Hello();
		// 为什么要多个createMBean？因为现在的写法没有写MBean,所以才要动态生成一个，以前就直接
		// 把new hello()注册到MBeanServer就可以了，server会自动找到它的HelloMBean接口文件。
		DynamicMBean hello = managed.createMBean(new TestMBeanModeler.Hello());
		server.registerMBean(hello, helloName);

		ObjectName adapterName = new ObjectName(managed.getDomain() + ":name = htmladapter,port=8082");
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		server.registerMBean(adapter, adapterName);
		adapter.start();
	}
}
