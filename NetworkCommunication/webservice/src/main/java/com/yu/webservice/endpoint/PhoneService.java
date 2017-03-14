package com.yu.webservice.endpoint;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**�ֻ���ҵ���࣬��ҵ����ͨ��webservice �����ṩ����
 * 1. ������ @webservice
 * 2. ���� EndPointִ��main����������������http://127.0.0.1:8888/ws/phoneService?WSDL ���Կ���wsdl��xml����
 * 3. wsimport ���ɴ�����wsimport -s / -p com.yu.webservice.endpoint.wsimport http://127.0.0.1:8888/ws/phoneService?WSDL
 * 4. ��д�ͻ��˴��룬����webservice
 * 
 */
@WebService (serviceName="PhoneManager",//�޸ķ�����
   targetNamespace="http://WebXml.com.cn/") //�޸������ռ� ��Ĭ�ϰ�����ȡ��
//������ҵ���� �����ṩwebservice����   ,Ĭ��ֻ�Ƕ�public ���εķ���������webservice��ʽ����
public class PhoneService {
    /**@WebMethod(operationName="getMObileInfo"): �޸ķ�����
     * @WebResult(name="phone")���޸ķ��ز�����
     * @WebParam(name="osName")���޸����������
     */
    @WebMethod(operationName="getMObileInfo")
    public @WebResult(name="phone") Phone getPhoneInfo(@WebParam(name="osName")String osName){
        Phone phone=new Phone();
        if(osName.endsWith("android")){
            phone.setName("android");phone.setOwner("google");phone.setTotal(80);
        }else if(osName.endsWith("ios")){
            phone.setName("ios");phone.setOwner("apple");phone.setTotal(15);
        }else{
            phone.setName("windows phone");phone.setOwner("microsoft");phone.setTotal(5);
        }
        return phone;
    }
    @WebMethod(exclude=true)//�Ѹ÷����ų�����
    public void sayHello(String city){
        System.out.println("��ã�"+city);
    }
    private void sayLuck(String city){
        System.out.println("���ѣ�"+city);
    }
     void sayGoodBye(String city){
        System.out.println("�ݰ�:"+city);
    }
    protected void saySayalala(String city){
         System.out.println("�ټ���"+city);
     }

    
    public static void main(String[] args) {
        String address1="http://127.0.0.1:8888/ws/phoneService";
//        String address2="http://127.0.0.1:8888/ws/phoneManager";
        /**
         * ����webservice����
         * 1.address������ĵ�ַ
         * 2��implementor �����ʵ�ֶ���
         */
        Endpoint.publish(address1, new PhoneService());
//        Endpoint.publish(address2, new PhoneService());
        System.out.println("wsdl��ַ :"+address1+"?WSDL");
    }
}
