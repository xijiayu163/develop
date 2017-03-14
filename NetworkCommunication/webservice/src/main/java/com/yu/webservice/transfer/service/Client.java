package com.yu.webservice.transfer.service;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import com.yu.webservice.soap.MobileCodeService;

import junit.framework.TestCase;

public class Client extends TestCase{
	
	@Test
	public void getDoctor1() throws HttpException, IOException{
		HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://127.0.0.1:8888/ws/cxf/DoctorService1/getDoctor1");
        //3.设置请求参数
        
        postMethod.setRequestBody(new FileInputStream("c:/soap.xml")); 
          //修改请求的头部
         postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
        //4.执行请求 ,结果码
        int code=client.executeMethod(postMethod);
        System.out.println("结果码:"+code);
        //5. 获取结果
        String result=postMethod.getResponseBodyAsString();
        System.out.println("Post请求的结果："+result);
	}
	
}
