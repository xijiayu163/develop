package com.yino.drudgery.rw;

import org.apache.commons.httpclient.HttpClient;

import com.yino.drudgery.entity.JobData;
import com.yino.util.StringUtil;
import com.yino.util.httpClient.HttpProtocolHandler;
import com.yino.util.httpClient.HttpRequest;
import com.yino.util.httpClient.HttpResponse;
import com.yino.util.httpClient.HttpResultType;

public class RestWebServiceReader extends WebServiceReader{

	@Override
	public JobData getData() {
		
		return null;
	}
	
	private String getResponseData(){
		
//		HttpRequest request = new HttpRequest(HttpResultType.STRING, httpRequestParamType);
//		if(requestEnum.equals(HttpRequestEnum.get)){
//			request.setMethod(HttpRequest.METHOD_GET);
//		}else{
//			request.setMethod(HttpRequest.METHOD_POST);
//		}
//		request.setUrl(serviceUrl);
//		if(!StringUtil.isEmpty(param)){
//			request.setQueryString(param);
//		}
//		initRequestInternal(request);
//		
//		if(this.requestEnum.equals(HttpRequestEnum.get)){
//			HttpResponse response = HttpProtocolHandler.getInstance().execute(request , "", "");
//			if(response.getStatusCode()==200){
//				return response.getStringResult();	
//			}
//			return "";
//		}else{
//			return getDataByPost();
//		}
	}
	

	protected void initRequestInternal(HttpRequest request){
		
	}
}
