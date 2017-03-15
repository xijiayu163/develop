//package com.yu.webservice.transfer.service;
//
//import java.io.ByteArrayInputStream;
//import java.util.Iterator;
//
//import javax.xml.soap.MessageFactory;
//import javax.xml.soap.MimeHeaders;
//import javax.xml.soap.SOAPBody;
//import javax.xml.soap.SOAPElement;
//import javax.xml.soap.SOAPMessage;
//
//public class SoapUtil {
//    
//    /**
//     * ����soapXML
//     * @param soapXML
//     * @return
//     */
//    public static WebserviceResultBean parseSoapMessage(String soapXML) {
//        WebserviceResultBean resultBean = new WebserviceResultBean();
//        try {
//            SOAPMessage msg = formatSoapString(soapXML);
//            SOAPBody body = msg.getSOAPBody();
//            Iterator<SOAPElement> iterator = body.getChildElements();
//            parse(iterator, resultBean);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return resultBean;
//    }
//
//    public static void main(String[] args) {
//        System.out.println("��ʼ����soap...");
//
//        String deptXML = "<SOAP:Envelope xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP:Header/><SOAP:Body><ns:OP_SDMS_Consume_Material_SynResponse xmlns:ns=\"http://toSDMS.material.service.ffcs.com\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><ns:return><ns:BASEINFO><MSGID>?</MSGID><PMSGID>?</PMSGID><SENDTIME>20140212094609</SENDTIME><S_PROVINCE>?</S_PROVINCE><S_SYSTEM>?</S_SYSTEM><SERVICENAME>?</SERVICENAME><T_PROVINCE>?</T_PROVINCE><T_SYSTEM>?</T_SYSTEM><RETRY>?</RETRY></ns:BASEINFO><ns:MESSAGE><RESULT>E</RESULT><REMARK/><XMLDATA><![CDATA[<response><RESULT>E</RESULT><MESSAGE>ƽ̨ϵͳ����ʱ�����쳣!����ӿڽ������ݳ���!</MESSAGE></response>]]></XMLDATA></ns:MESSAGE></ns:return></ns:OP_SDMS_Consume_Material_SynResponse></SOAP:Body></SOAP:Envelope>";
//        WebserviceResultBean ret = parseSoapMessage(deptXML);
//        try {
//            SOAPMessage msg = formatSoapString(deptXML);
//            SOAPBody body = msg.getSOAPBody();
//            Iterator<SOAPElement> iterator = body.getChildElements();
//            PrintBody(iterator, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("����soap����...");
//    }
//
//    /**
//     * ��soap�ַ�����ʽ��ΪSOAPMessage
//     * 
//     * @param soapString
//     * @return
//     * @see [�ࡢ��#��������#��Ա]
//     */
//    private static SOAPMessage formatSoapString(String soapString) {
//        MessageFactory msgFactory;
//        try {
//            msgFactory = MessageFactory.newInstance();
//            SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
//                    new ByteArrayInputStream(soapString.getBytes("UTF-8")));
//            reqMsg.saveChanges();
//            return reqMsg;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * ����soap xml
//     * @param iterator
//     * @param resultBean
//     */
//    private static void parse(Iterator<SOAPElement> iterator, WebserviceResultBean resultBean) {
//        while (iterator.hasNext()) {
//            SOAPElement element = iterator.next();
////            System.out.println("Local Name:" + element.getLocalName());
////            System.out.println("Node Name:" + element.getNodeName());
////            System.out.println("Tag Name:" + element.getTagName());
////            System.out.println("Value:" + element.getValue());
//            if ("ns:BASEINFO".equals(element.getNodeName())) {
//                continue;
//            } else if ("ns:MESSAGE".equals(element.getNodeName())) {
//                Iterator<SOAPElement> it = element.getChildElements();
//                SOAPElement el = null;
//                while (it.hasNext()) {
//                    el = it.next();
//                    if ("RESULT".equals(el.getLocalName())) {
//                        resultBean.setResult(el.getValue());
//                        System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
//                    } else if ("REMARK".equals(el.getLocalName())) {
//                        resultBean.setRemark(null != el.getValue() ? el.getValue() : "");
//                        System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
//                    } else if ("XMLDATA".equals(el.getLocalName())) {
//                        resultBean.setXmlData(el.getValue());
//                        System.out.println("#### " + el.getLocalName() + "  ====  " + el.getValue());
//                    }
//                }
//            } else if (null == element.getValue()
//                    && element.getChildElements().hasNext()) {
//                parse(element.getChildElements(), resultBean);
//            }
//        }
//    }
//    
//    private static void PrintBody(Iterator<SOAPElement> iterator, String side) {
//        while (iterator.hasNext()) {
//            SOAPElement element = (SOAPElement) iterator.next();
//            System.out.println("Local Name:" + element.getLocalName());
//            System.out.println("Node Name:" + element.getNodeName());
//            System.out.println("Tag Name:" + element.getTagName());
//            System.out.println("Value:" + element.getValue());
//            if (null == element.getValue()
//                    && element.getChildElements().hasNext()) {
//                PrintBody(element.getChildElements(), side + "-----");
//            }
//        }
//    }
//}