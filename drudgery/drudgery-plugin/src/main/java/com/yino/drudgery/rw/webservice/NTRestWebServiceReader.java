package com.yino.drudgery.rw.webservice;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yino.util.JsonUtil;
import com.yino.util.httpClient.HttpProtocolHandler;
import com.yino.util.httpClient.HttpRequest;
import com.yino.util.httpClient.HttpRequestParamType;
import com.yino.util.httpClient.HttpResponse;
import com.yino.util.httpClient.HttpResultType;

/**
 * 南通webservice
 * @author fangzhibin
 *
 */
public class NTRestWebServiceReader extends RestWebService{
	private String token;
	private static final String GRANT_TYPE="password";
	//用户名
	private static final String USER_NAME="yn@live.cn";
	//密码
	private static final String PASSWORD="Pass01!~";
	//获取南通Token数据地址
	private static final String GET_TOKEN_URL="http://218.91.232.242:1893/Token";
	
	@Override
	protected void authenticate() throws HttpException, IOException{
		token= getToken();
		headerMap.put("Authorization", "Bearer "+token);
	}
	
	private String getToken() throws HttpException, IOException{
		//设置请求参数
		NameValuePair[]  nvNameValuePairs = {new NameValuePair("grant_type", GRANT_TYPE),new NameValuePair("username", USER_NAME),new NameValuePair("password", PASSWORD)};
		HttpRequest request = new HttpRequest(HttpResultType.STRING, HttpRequestParamType.DEFAULT);
		request.setMethod(HttpRequest.METHOD_POST);
		request.setUrl(GET_TOKEN_URL);
		request.setParameters(nvNameValuePairs);
		
		HttpResponse response = HttpProtocolHandler.getInstance().execute(request , "", "");
		if(response.getStatusCode()==200){
			TypeReference<HashMap<String, Object>> typeRef =new TypeReference<HashMap<String,Object>>() {};
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap=JsonUtil.rtisJson2Obj(response.getStringResult(), typeRef);
			//设置token，并设置token的有效时长，单位：秒
//			redisService.set(REDIS_NANTONG_TOKEN_KEY, map.get("access_token").toString(),Integer.parseInt(map.get("expires_in").toString())-60,TimeUnit.SECONDS);
			return  resultMap.get("access_token").toString();
		}
	
		return "";
	}
}
