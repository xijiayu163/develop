package com.yu.webservice.endpoint;

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
	public static void main(String[] args) {
		//访问网络天气
//		//创建一个MobileCodeWS工厂  
        MobileCodeWS factory = new MobileCodeWS();  
        //根据工厂创建一个MobileCodeWSSoap对象  
        MobileCodeWSSoap mobileCodeWSSoap = factory.getMobileCodeWSSoap();  
        //调用WebService提供的getMobileCodeInfo方法查询手机号码的归属地  
        String searchResult = mobileCodeWSSoap.getMobileCodeInfo("18512155752", null);  
        System.out.println(searchResult);  
        
        //访问自定的
//        PhoneManager pm = new PhoneManager();
//        PhoneService phoneServicePort = pm.getPhoneServicePort();
//        Phone mObileInfo = phoneServicePort.getMObileInfo("fdsfd");
//        System.out.println(mObileInfo.getName());
//        System.out.println(mObileInfo.getOwner());
    }
}
