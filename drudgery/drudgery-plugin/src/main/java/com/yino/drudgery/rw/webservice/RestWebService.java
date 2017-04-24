package com.yino.drudgery.rw.webservice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.yino.drudgery.entity.JobData;
import com.yino.drudgery.enums.ResultEnum;
import com.yino.util.StringUtil;
import com.yino.util.httpClient.HttpProtocolHandler;
import com.yino.util.httpClient.HttpRequest;
import com.yino.util.httpClient.HttpRequestParamType;
import com.yino.util.httpClient.HttpResponse;
import com.yino.util.httpClient.HttpResultType;

public class RestWebService extends WebServiceReader{
	
	protected Map<String,String> headerMap=new HashMap<String, String>();
	/**
	 * 服务链接
	 */
	protected String serviceUrl;
	/**
	 * 请求参数
	 */
	protected String param;
	/**
	 * http请求方法类型
	 */
	protected HttpRequestEnum requestEnum = HttpRequestEnum.GET;
	/**
	 * 请求参数类型
	 */
	protected HttpRequestParamType httpRequestParamType = HttpRequestParamType.DEFAULT;
	
	public Map<String, String> getMap() {
		return headerMap;
	}

	public void setMap(Map<String, String> map) {
		this.headerMap = map;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public HttpRequestEnum getRequestEnum() {
		return requestEnum;
	}

	public void setRequestEnum(HttpRequestEnum requestEnum) {
		this.requestEnum = requestEnum;
	}

	public HttpRequestParamType getHttpRequestParamType() {
		return httpRequestParamType;
	}

	public void setHttpRequestParamType(HttpRequestParamType httpRequestParamType) {
		this.httpRequestParamType = httpRequestParamType;
	}
	
	
	private JobData execute(){
	JobData jobData = new JobData();
		
		try {
			authenticate();
		} catch (IOException e) {
			jobData.setStatus(ResultEnum.failure);
			jobData.setErrorMessage("鉴权失败:"+ e.getMessage());
			return jobData;
		}

		
		//设置请求头
		HttpRequest request = new HttpRequest(HttpResultType.STRING, httpRequestParamType);
		request.setMethod(requestEnum.name());
		request.setUrl(serviceUrl+methodName);
		request.setHeaders(headerMap);
		if(!StringUtil.isEmpty(param)){
			if(requestEnum.equals(HttpRequestEnum.GET)){
				request.setQueryString(param);
			}else if(requestEnum.equals(HttpRequestEnum.POST)){
				request.setJsonParamString(param);
			}
		}
		try{
			HttpResponse response = HttpProtocolHandler.getInstance().execute(request , "", "");
			if(response.getStatusCode()==200){
				String result = response.getStringResult();	
				jobData.setJsonData(result);
				jobData.setStatus(ResultEnum.success);
			}
		}catch(HttpException e1){
			jobData.setErrorMessage(e1.getMessage());
			jobData.setStatus(ResultEnum.failure);
			
		}catch (UnsupportedEncodingException e2) {
			jobData.setErrorMessage(e2.getMessage());
			jobData.setStatus(ResultEnum.failure);
		} catch (IOException e3) {
			jobData.setErrorMessage(e3.getMessage());
			jobData.setStatus(ResultEnum.failure);
		}
		
		return jobData;
	}
	
	@Override
	public JobData getData() {
		return execute();
	}
	
	@Override
	public JobData writeData(JobData result) {
		param = result.getJsonData();
		httpRequestParamType = HttpRequestParamType.JSON;
		return execute();
	}
	
	/**
	 * 鉴权，由子类实现
	 * @throws IOException 
	 * @throws HttpException 
	 */
	protected void authenticate() throws HttpException, IOException{}

}
