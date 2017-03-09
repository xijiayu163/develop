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
		//������������
//		//����һ��MobileCodeWS����  
        MobileCodeWS factory = new MobileCodeWS();  
        //���ݹ�������һ��MobileCodeWSSoap����  
        MobileCodeWSSoap mobileCodeWSSoap = factory.getMobileCodeWSSoap();  
        //����WebService�ṩ��getMobileCodeInfo������ѯ�ֻ�����Ĺ�����  
        String searchResult = mobileCodeWSSoap.getMobileCodeInfo("18512155752", null);  
        System.out.println(searchResult);  
        
        //�����Զ���
//        PhoneManager pm = new PhoneManager();
//        PhoneService phoneServicePort = pm.getPhoneServicePort();
//        Phone mObileInfo = phoneServicePort.getMObileInfo("fdsfd");
//        System.out.println(mObileInfo.getName());
//        System.out.println(mObileInfo.getOwner());
    }
}
