package com.yino.drudgery.Factory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.jobrunner.JobRunner;
import com.yino.drudgery.mq.service.MessageService;

/**
 * @Description 通过反射创建JobRunnable的子类
 * 
 * @Copyright:
 * 
 * @author Wxb
 * @date
 * @version
 *
 */
public class JobRunnerFactory {

	/**
	 * @Description 通过反射创建JobRunner的子类
	 * @param job
	 * @return JobRunner实例
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public static JobRunner createJobRunner(Job job,MessageService service) throws Exception
	{
		JobRunner runner=null;
	
		String className = "";
		String jarFile = "";
		File file = new File(jarFile);
		URL url=file.toURI().toURL();  
		ClassLoader loader=new URLClassLoader(new URL[]{url});//创建类加载器  
	    Class<?> c = loader.loadClass(className);
	    Object o= c.newInstance();
	    if (o instanceof JobRunner) {
	    	 runner = (JobRunner) o;
	    	 runner.setJob(job);
	    	 runner.setMsgService(service);
		}
	    else
	    {
	    	throw new Exception("创建的接口类型与JobRunner不匹配！");
	    }
		return runner;
	}
}

