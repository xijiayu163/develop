package com.yino.drudgery.factory;

import java.util.Map;

import com.yino.drudgery.entity.DataSyncRequestData;
import com.yino.drudgery.entity.QueryRequestData;
import com.yino.drudgery.entity.RequestData;

public class RequestDataFactory {
	
	public static RequestData createRequestData(String jobName,String callbackUrl,Map<String, String> queryParams)
	{
		QueryRequestData requestData = new QueryRequestData();
		requestData.setJobName(jobName);
		requestData.setCallbackUrl(callbackUrl);
		Boolean async = callbackUrl!=null&&!callbackUrl.isEmpty();
		requestData.setAsync(async);
		requestData.setQueryParams(queryParams);
		return requestData;
	}
	
	public static RequestData createRequestData(String jobName,String inputData)
	{
		DataSyncRequestData requestData = new DataSyncRequestData();
		requestData.setJobName(jobName);
		requestData.setAsync(false);
		requestData.setInputData(inputData);
		return requestData;
	}
}
