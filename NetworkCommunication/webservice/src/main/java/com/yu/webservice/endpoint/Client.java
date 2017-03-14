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
		//������������
////		//����һ��MobileCodeWS����  
//        MobileCodeWS factory = new MobileCodeWS();  
//        //���ݹ�������һ��MobileCodeWSSoap����  
//        MobileCodeWSSoap mobileCodeWSSoap = factory.getMobileCodeWSSoap();  
//        //����WebService�ṩ��getMobileCodeInfo������ѯ�ֻ�����Ĺ�����  
//        String searchResult = mobileCodeWSSoap.getMobileCodeInfo("18512155752", null);  
//        System.out.println(searchResult);  
//        
//        //�����Զ���
////        PhoneManager pm = new PhoneManager();
////        PhoneService phoneServicePort = pm.getPhoneServicePort();
////        Phone mObileInfo = phoneServicePort.getMObileInfo("fdsfd");
////        System.out.println(mObileInfo.getName());
////        System.out.println(mObileInfo.getOwner());
		
		soap();
    }
	
	/**
	 * ��ͨ��Eclipse��Launching the WSDL Explorer ���ԣ�����WSDL�ļ�·��������http://127.0.0.1:8888/ws/phoneService?WSDL���ɿ���������Ҫ��XML��ʽ����
	 * soapXml.xml �ɲ���
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void soap() throws HttpException, IOException{
		HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://127.0.0.1:8888/ws/phoneService/getMObileInfo");
        //3.����������� 
        postMethod.setRequestBody(new FileInputStream("E:/GitHub/develop/NetworkCommunication/webservice/src/main/java/com/yu/webservice/endpoint/soapXml.xml")); 
        //�޸������ͷ��
        postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
        //4.ִ������ ,�����
        int code=client.executeMethod(postMethod);
        System.out.println("�����:"+code);
        //5. ��ȡ���
        String result=postMethod.getResponseBodyAsString();
        System.out.println("Post����Ľ����"+result);
		
		
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
