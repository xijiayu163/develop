package com.yu.webservice.cxf;

import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * 开发语言排行描述服务
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
        //Endpoint :地址  ， 实现对象
        bean.setAddress("http://127.0.0.1:8888/ws/cxf/languangeService");
        bean.setServiceClass(LanguageService.class);//对外提供webservcie的业务类或者接口
        bean.setServiceBean(languageService);//服务的实现bean
        bean.create();//创建，发布webservice
        System.out.println("wsdl地址:http://127.0.0.1:8888/ws/cxf/languangeService?WSDL");
    }
	/**
	 * 利用endpoint发布webservice
	 * @param args
	 */
//	public static void main(String[] args) {
//        LanguageService languageService=new LanguageServiceImpl();
//        JaxWsServerFactoryBean bean=new JaxWsServerFactoryBean();
//        //Endpoint :地址  ， 实现对象
//        bean.setAddress("http://127.0.0.1:9999/ws/cxf/languangeService");
//        bean.setServiceClass(LanguageService.class);//对外提供webservcie的业务类或者接口
//        bean.setServiceBean(languageService);//服务的实现bean
//        //添加输入拦截器  :输入显示日志信息的拦截器
//        bean.getInInterceptors().add(new LoggingInInterceptor());
//        //添加输出拦截器  :输出显示日志信息的拦截器
//        bean.getOutInterceptors().add(new LoggingOutInterceptor());
//
//        bean.create();//创建，发布webservice
//        System.out.println("wsdl地址:http://127.0.0.1:9999/ws/cxf/languangeService?WSDL");
//    }
}
