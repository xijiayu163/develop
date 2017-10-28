package org.apache.tomcat.util.modeler.test;

import javax.management.DynamicMBean;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.Registry;

public class MBeanUtils {
	private static Registry registry = createRegistry();
	private static MBeanServer mserver = createServer();

	private static synchronized Registry createRegistry() {
		if (registry == null) {
			registry = Registry.getRegistry(null, null);
			ClassLoader cl = MBeanUtils.class.getClassLoader();

			registry.loadDescriptors("org.apache.tomcat.util.modeler.test", cl);
		}
		return (registry);
	}

	private static synchronized MBeanServer createServer() {
		if (mserver == null) {
			mserver = Registry.getRegistry(null, null).getMBeanServer();
		}
		return (mserver);
	}

	static String createManagedName(Object component) {
		String className = component.getClass().getName();
		// Perform the standard transformation
		int period = className.lastIndexOf('.');
		if (period >= 0)
			className = className.substring(period + 1);
		return (className);
	}

	public static DynamicMBean createMBean(ContextResource resource) throws Exception {

		String mname = createManagedName(resource);
		ManagedBean managed = registry.findManagedBean(mname);
		if (managed == null) {
			Exception e = new Exception("ManagedBean is not found with " + mname);
			throw new MBeanException(e);
		}
		String domain = managed.getDomain();
		if (domain == null)
			domain = mserver.getDefaultDomain();
		DynamicMBean mbean = managed.createMBean(resource);
		ObjectName oname = new ObjectName("xxx");
		if (mserver.isRegistered(oname)) {
			mserver.unregisterMBean(oname);
		}
		mserver.registerMBean(mbean, oname);
		return (mbean);
	}

}
