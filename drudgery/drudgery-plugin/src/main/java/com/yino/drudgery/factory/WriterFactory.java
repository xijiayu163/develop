package com.yino.drudgery.factory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.yino.drudgery.rw.IWrite;

/**
 * @Description 通过反射创建IWrite的实现类
 * @param jarFile
 * @param className
 * @return IWrite实例
 * @throws Exception
 */
@SuppressWarnings("resource")
public class WriterFactory {

	/**
	 * 
	 * @param jarFile
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static IWrite createInstance(String jarFile,String className) throws Exception
	{
		IWrite writer=null;
		File file = new File(jarFile);
		URL url=file.toURI().toURL();  
		ClassLoader loader=new URLClassLoader(new URL[]{url});//创建类加载器  
	    Class<?> c = loader.loadClass(className);
	    Object o= c.newInstance();
	    if (o instanceof IWrite) {
	    	writer = (IWrite) o;
		}
	    else
	    {
	    	throw new Exception("创建的接口类型与IWrite不匹配！");
	    }
		return writer;
	}
}
