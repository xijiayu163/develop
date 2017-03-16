package com.yu.webservice.transfer.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.xml.serialize.OutputFormat;
//import org.apache.xml.serialize.XMLSerializer;
import org.junit.Test;
import org.omg.CORBA.Environment;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.yu.webservice.soap.MobileCodeService;

import junit.framework.TestCase;

public class Client extends TestCase {

	@Test
	public void testGetDoctor1() throws IOException {
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod("http://127.0.0.1:8888/ws/cxf/DoctorService1/getDoctor1");
		// 3.设置请求参数

		postMethod.setRequestBody(new FileInputStream("c:/soap.xml"));
		// 修改请求的头部
		postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
		// 4.执行请求 ,结果码
		int code = client.executeMethod(postMethod);
		System.out.println("结果码:" + code);
		// 5. 获取结果
		String result = postMethod.getResponseBodyAsString();
		System.out.println("Post请求的结果：" + result);
		
		//String formatXML = formatXML(result);
//		System.out.println("formatXml:"+formatXML);
		
		
		
	}

//	public String formatXML(String unformattedXml) {
//		try {
//			Document document = parseXmlFile(unformattedXml);
//			OutputFormat format = new OutputFormat(document);
//			format.setIndenting(true);
//			format.setIndent(3);
//			format.setOmitXMLDeclaration(true);
//			Writer out = new StringWriter();
//			XMLSerializer serializer = new XMLSerializer(out, format);
//			serializer.serialize(document);
//			return out.toString();
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	private Document parseXmlFile(String in) {
//		try {
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			InputSource is = new InputSource(new StringReader(in));
//			return db.parse(is);
//		} catch (ParserConfigurationException e) {
//			throw new RuntimeException(e);
//		} catch (SAXException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
	    
}
