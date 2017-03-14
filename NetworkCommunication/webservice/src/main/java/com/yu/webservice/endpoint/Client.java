package com.yu.webservice.endpoint;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.yu.webservice.endpoint.mywsimport.Phone;
import com.yu.webservice.endpoint.mywsimport.PhoneManager;
import com.yu.webservice.endpoint.mywsimport.PhoneService;
import com.yu.webservice.endpoint.wsimport.MobileCodeWS;
import com.yu.webservice.endpoint.wsimport.MobileCodeWSSoap;

/**
 * D:\>wsimport -s / -p com.yu.webservice.endpoint.wsimport http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?wsdl
 * @author xijia
 *
 */
public class Client {
	public static void main(String[] args) throws HttpException, IOException {
		//访问网络天气
////		//创建一个MobileCodeWS工厂  
//        MobileCodeWS factory = new MobileCodeWS();  
//        //根据工厂创建一个MobileCodeWSSoap对象  
//        MobileCodeWSSoap mobileCodeWSSoap = factory.getMobileCodeWSSoap();  
//        //调用WebService提供的getMobileCodeInfo方法查询手机号码的归属地  
//        String searchResult = mobileCodeWSSoap.getMobileCodeInfo("18512155752", null);  
//        System.out.println(searchResult);  
//        
//        //访问自定的
////        PhoneManager pm = new PhoneManager();
////        PhoneService phoneServicePort = pm.getPhoneServicePort();
////        Phone mObileInfo = phoneServicePort.getMObileInfo("fdsfd");
////        System.out.println(mObileInfo.getName());
////        System.out.println(mObileInfo.getOwner());
		
		soap();
    }
	
	/**
	 * 可通过Eclipse的Launching the WSDL Explorer 测试，输入WSDL文件路径，例如http://127.0.0.1:8888/ws/phoneService?WSDL，可看到访问需要的XML格式数据
	 * soapXml.xml 可参照
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void soap() throws HttpException, IOException{
		HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://127.0.0.1:8888/ws/phoneService/getMObileInfo");
        //3.设置请求参数 
        postMethod.setRequestBody(new FileInputStream("E:/GitHub/develop/NetworkCommunication/webservice/src/main/java/com/yu/webservice/endpoint/soapXml.xml")); 
        //修改请求的头部
        postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
        //4.执行请求 ,结果码
        int code=client.executeMethod(postMethod);
        System.out.println("结果码:"+code);
        //5. 获取结果
        String result=postMethod.getResponseBodyAsString();
        System.out.println("Post请求的结果："+result);
		
		
//			String osName="android";
//			String wsURL = "http://127.0.0.1:8888/ws/phoneService";
//			URL url = new URL(wsURL);
////			String soapXml =   
////					" <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://litwinconsulting.com/webservices/\">\n" +
////							" <soapenv:Header/>\n" +
////							" <soapenv:Body>\n" +
////							" <getMObileInfo>\n" +
////							" <!--Optional:-->\n" +
////							" <osName>" + osName + "</osName>\n" +
////							" </getMObileInfo>\n" +
////							" </soapenv:Body>\n" +
////							" </soapenv:Envelope>";
//			
//			
//			
//			java.net.URLConnection conn = url.openConnection();
//			// Set the necessary header fields
//			conn.setRequestProperty("SOAPAction", "http://127.0.0.1:8888/ws/phoneService/getMObileInfo");
//			conn.setDoOutput(true);
//			// Send the request
//			java.io.OutputStreamWriter wr = new java.io.OutputStreamWriter(conn.getOutputStream());
//			wr.write(soapXml);
//			wr.flush();
//			// Read the response
//			java.io.BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
//			String line;
//			while ((line = rd.readLine()) != null) { System.out.println(line); /*jEdit: print(line); */ }      
	}
	
	public static void readXml(){
		
	}
}
