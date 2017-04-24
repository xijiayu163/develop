package com.yino.drudgery.rw.webservice;

import java.io.IOException;
import java.util.Map;

import javax.xml.soap.SOAPException;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSONArray;
import com.yino.drudgery.entity.JobData;
import com.yino.drudgery.enums.ResultEnum;

public class SoapWebServiceReader extends WebServiceReader{
	private String namespace;
	private Map<String,Object> paramMap;
	private String wsdlUrl;
	private static final  String requestNs="ns";
	
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
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
		
		try{
	    	JSONArray jsonArray =SoapUtil.performRequest(requestNs,methodName,namespace,paramMap,wsdlUrl);
	    	String result = jsonArray.toJSONString();
	    	
	    	jobData.setJsonData(result);
			jobData.setStatus(ResultEnum.success);
		}catch(SOAPException ex){
			jobData.setErrorMessage(ex.getMessage());
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
		return execute();
	}
	
	/**
	 * 鉴权，由子类实现
	 * @throws IOException 
	 * @throws HttpException 
	 */
	protected void authenticate() throws HttpException, IOException{}



}
