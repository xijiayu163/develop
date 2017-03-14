package com.yu.webservice.cxf;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.yu.webservice.cxf.wsimport.LanguageServicePortType;

/**根据WSDL生成描述
 * D:\>wsimport -s / -p com.yu.webservice.cxf.wsimport http://127.0.0.1:8888/ws/cxf
/languangeService?WSDL
 * 
 *
 */
public class Client {
	public static void main(String[] args) throws HttpException, IOException {
//		com.yu.webservice.cxf.wsimport.LanguageService ws=new com.yu.webservice.cxf.wsimport.LanguageService();
//		LanguageServicePortType languageServicePort = ws.getLanguageServicePort();
//        System.out.println(languageServicePort.getLanguage(1));
        
        soap();
    }
	
	public static void soap() throws HttpException, IOException{
		HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://127.0.0.1:8888/ws/cxf/languangeService/getLanguage");
        //3.设置请求参数
        NameValuePair[] data = { new NameValuePair("arg0", "1") };
        postMethod.setRequestBody(data);  
        //postMethod.setRequestBody(new FileInputStream("c:/soap.xml")); 
          //修改请求的头部
          //postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
        //4.执行请求 ,结果码
        int code=client.executeMethod(postMethod);
        System.out.println("结果码:"+code);
        //5. 获取结果
        String result=postMethod.getResponseBodyAsString();
        System.out.println("Post请求的结果："+result);
	}
}
