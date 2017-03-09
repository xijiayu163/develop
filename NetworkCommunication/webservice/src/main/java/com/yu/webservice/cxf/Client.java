package com.yu.webservice.cxf;

import com.yu.webservice.cxf.wsimport.LanguageServicePortType;

/**根据WSDL生成描述
 * D:\>wsimport -s / -p com.yu.webservice.cxf.wsimport http://127.0.0.1:8888/ws/cxf
/languangeService?WSDL
 * 
 *
 */
public class Client {
	public static void main(String[] args) {
		com.yu.webservice.cxf.wsimport.LanguageService ws=new com.yu.webservice.cxf.wsimport.LanguageService();
		LanguageServicePortType languageServicePort = ws.getLanguageServicePort();
        System.out.println(languageServicePort.getLanguage(1));
    }
}
