package com.yu.webservice.cxf;

import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * ��������������������
 * 
 * 
 * 
 */
public class LanguageServiceImpl implements LanguageService {

	public String getLanguage(int position) {
		String language = null;
		switch (position) {
		case 1:
			language = "java";
			break;
		case 2:
			language = "C";
			break;
		case 3:
			language = "Objective-C";
			break;
		case 4:
			language = "C#";
			break;

		default:
			break;
		}
		return language;
	}
	
	public static void main(String[] args) {
        LanguageService languageService=new LanguageServiceImpl();
        ServerFactoryBean bean=new ServerFactoryBean();
        //Endpoint :��ַ  �� ʵ�ֶ���
        bean.setAddress("http://127.0.0.1:8888/ws/cxf/languangeService");
        bean.setServiceClass(LanguageService.class);//�����ṩwebservcie��ҵ������߽ӿ�
        bean.setServiceBean(languageService);//�����ʵ��bean
        bean.create();//����������webservice
        System.out.println("wsdl��ַ:http://127.0.0.1:8888/ws/cxf/languangeService?WSDL");
    }
	/**
	 * ����endpoint����webservice
	 * @param args
	 */
//	public static void main(String[] args) {
//        LanguageService languageService=new LanguageServiceImpl();
//        JaxWsServerFactoryBean bean=new JaxWsServerFactoryBean();
//        //Endpoint :��ַ  �� ʵ�ֶ���
//        bean.setAddress("http://127.0.0.1:9999/ws/cxf/languangeService");
//        bean.setServiceClass(LanguageService.class);//�����ṩwebservcie��ҵ������߽ӿ�
//        bean.setServiceBean(languageService);//�����ʵ��bean
//        //�������������  :������ʾ��־��Ϣ��������
//        bean.getInInterceptors().add(new LoggingInInterceptor());
//        //������������  :�����ʾ��־��Ϣ��������
//        bean.getOutInterceptors().add(new LoggingOutInterceptor());
//
//        bean.create();//����������webservice
//        System.out.println("wsdl��ַ:http://127.0.0.1:9999/ws/cxf/languangeService?WSDL");
//    }
}
