package com.yino.drudgery.rw;

import com.yino.drudgery.entity.JobData;

public abstract class WebServiceReader implements IRead{

	protected String serviceUrl;
	protected String methodName;
	protected String jsonParams;
	protected HttpRequestEnum requestEnum;
	
	
	public abstract JobData getData();
	
}
