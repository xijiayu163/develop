package org.apache.catalina.util;

import java.net.URL;
import java.net.URLClassLoader;

public class LogHelper {
	
	public static String printClassLoader(ClassLoader classLoader){
		if(classLoader==null){
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("class:"+classLoader.getClass().getName());
		URL[] urls = ((URLClassLoader)classLoader).getURLs();
    	sb.append("parentClassLoader,urls 长度:"+urls.length+"\r\n");
    	for(URL url :urls){
    		sb.append("url:"+url.getPath()+"\r\n");
    	}
    	
    	return sb.toString();
	}
}
