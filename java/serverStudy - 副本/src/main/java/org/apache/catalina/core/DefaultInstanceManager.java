package org.apache.catalina.core;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.Context;
import org.apache.catalina.InstanceManager;

public class DefaultInstanceManager implements InstanceManager{

	public DefaultInstanceManager(Context context, Map<String, Map<String, String>> injectionMap, 
			org.apache.catalina.Context catalinaContext, ClassLoader containerClassLoader) {
		
	}
	


	public Object newInstance(Class<?> clazz)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object newInstance(String className)
			throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object newInstance(String fqcn, ClassLoader classLoader)
			throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void newInstance(Object o) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		
	}

	public void destroyInstance(Object o) throws IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		
	}
	
}
