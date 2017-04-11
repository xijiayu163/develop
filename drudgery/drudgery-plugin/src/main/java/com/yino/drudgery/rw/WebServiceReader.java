package com.yino.drudgery.rw;

import com.yino.drudgery.entity.JobData;
import com.yino.util.httpClient.HttpRequestParamType;

public abstract class WebServiceReader implements IRead{
	protected String serviceUrl;
	protected String methodName;
	protected String param;
	protected HttpRequestEnum requestEnum = HttpRequestEnum.get;
	protected HttpRequestParamType httpRequestParamType = HttpRequestParamType.DEFAULT;
	
	protected boolean isNeedAuthenticate = false;
	protected String userName;
	protected String password;
	
	public abstract JobData getData();
}
