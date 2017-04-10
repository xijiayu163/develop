package com.yino.drudgery.factory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.yino.drudgery.rw.IRead;

/**
 * @Description 通过反射创建IRead的实现类
 * @param jarFile
 * @param className
 * @return IConvert实例
 * @throws Exception
 */
@SuppressWarnings("resource")
public class ReaderFactory {

	/**
	 * 
	 * @param jarFile
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static IRead createInstance(String jarFile,String className) throws Exception
	{
		IRead reader=null;
		File file = new File(jarFile);
		URL url=file.toURI().toURL();  
		ClassLoader loader=new URLClassLoader(new URL[]{url});//创建类加载器  
	    Class<?> c = loader.loadClass(className);
	    Object o= c.newInstance();
	    if (o instanceof IRead) {
	    	reader = (IRead) o;
		}
	    else
	    {
	    	throw new Exception("创建的接口类型与IRead不匹配！");
	    }
		return reader;
	}
}

