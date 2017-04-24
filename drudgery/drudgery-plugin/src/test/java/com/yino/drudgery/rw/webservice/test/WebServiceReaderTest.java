package com.yino.drudgery.rw.webservice.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yino.drudgery.entity.JobData;
import com.yino.drudgery.rw.webservice.HttpRequestEnum;
import com.yino.drudgery.rw.webservice.NTRestWebServiceReader;
import com.yino.drudgery.rw.webservice.RestWebService;
import com.yino.drudgery.rw.webservice.SoapWebServiceReader;
import com.yino.drudgery.test.SpringTestCase;
import com.yino.util.JsonUtil;
import com.yino.util.httpClient.HttpRequestParamType;

public class WebServiceReaderTest extends SpringTestCase{
	
	@Test
	public void testNT() throws JsonProcessingException{
		RestWebService webService = new NTRestWebServiceReader();
		webService.setHttpRequestParamType(HttpRequestParamType.DEFAULT);
		webService.setMethodName("HisDepartments");
		webService.setParam(null);
		webService.setRequestEnum(HttpRequestEnum.GET);
		webService.setServiceUrl("http://218.91.232.242:1893/api/");
		
		JobData data = webService.getData();
		String obj2Json = JsonUtil.obj2Json(data);
		System.out.println(obj2Json);
	}
	
	@Test
	public void testSoap() throws JsonProcessingException{
		SoapWebServiceReader webService = new SoapWebServiceReader();
		webService.setMethodName("CallInterface");
		webService.setWsdlUrl("http://61.154.9.208:3682/services/WSInterface?wsdl");
		webService.setNamespace("http://www.zysoft.com/");
		
		Map<String, Object> paramMap = new HashMap<>();
		String body = "<root><orgCode>01</orgCode><id>0739544</id></root>"; 
    	String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root><serverName>GetTumorPatients </serverName> <format>xml</format><callOperator></callOperator><certificate>yfwY7nygVGeNbb2Xf9heItlCGLzAYdOS </certificate></root>";
    	paramMap.put("msgHeader", header);
    	paramMap.put("msgBody", body);
		webService.setParamMap(paramMap);
		
		JobData data = webService.getData();
		String obj2Json = JsonUtil.obj2Json(data);
		System.out.println(obj2Json);
	}
}
