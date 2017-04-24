package com.yino.drudgery.rw.webservice;

import com.yino.drudgery.entity.JobData;
import com.yino.drudgery.rw.IRead;
import com.yino.drudgery.rw.IWrite;

public abstract class WebServiceReader implements IRead,IWrite{
	/**
	 * web服务方法名
	 */
	protected String methodName;
	
	public abstract JobData getData();
	
	public abstract JobData writeData(JobData result);

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
}
