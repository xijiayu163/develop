package org.apache.catalina;

import java.lang.reflect.InvocationTargetException;

public interface InstanceManager {
	 public Object newInstance(Class<?> clazz)
	            throws IllegalAccessException, InvocationTargetException,
	                InstantiationException;

	    public Object newInstance(String className)
	        throws IllegalAccessException, InvocationTargetException,
	            InstantiationException, ClassNotFoundException;

	    public Object newInstance(String fqcn, ClassLoader classLoader)
	        throws IllegalAccessException, InvocationTargetException,
	            InstantiationException, ClassNotFoundException;

	    public void newInstance(Object o)
	        throws IllegalAccessException, InvocationTargetException;

	    public void destroyInstance(Object o)
	        throws IllegalAccessException, InvocationTargetException;
}
