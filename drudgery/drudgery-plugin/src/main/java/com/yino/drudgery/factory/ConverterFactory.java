package com.yino.drudgery.factory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.yino.drudgery.rw.IConvert;

/**
 * @Description 通过反射创建IConvert的实现类
 * @param jarFile
 * @param className
 * @return IConvert实例
 * @throws Exception
 */
@SuppressWarnings("resource")
public class ConverterFactory {

	/**
	 * 
	 * @param jarFile
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static IConvert createInstance(String jarFile,String className) throws Exception
	{
		IConvert converter=null;
		File file = new File(jarFile);
		URL url=file.toURI().toURL();  
		ClassLoader loader=new URLClassLoader(new URL[]{url});//创建类加载器  
	    Class<?> c = loader.loadClass(className);
	    Object o= c.newInstance();
	    if (o instanceof IConvert) {
	    	converter = (IConvert) o;
		}
	    else
	    {
	    	throw new Exception("创建的接口类型与IConvert不匹配！");
	    }
		return converter;
	}
}
