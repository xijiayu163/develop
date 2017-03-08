
package com.yu.webservice.endpoint.mywsimport;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PhoneService", targetNamespace = "http://WebXml.com.cn/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PhoneService {


    /**
     * 
     * @param osName
     * @return
     *     returns com.yu.webservice.endpoint.mywsimport.Phone
     */
    @WebMethod
    @WebResult(name = "phone", targetNamespace = "")
    @RequestWrapper(localName = "getMObileInfo", targetNamespace = "http://WebXml.com.cn/", className = "com.yu.webservice.endpoint.mywsimport.GetMObileInfo")
    @ResponseWrapper(localName = "getMObileInfoResponse", targetNamespace = "http://WebXml.com.cn/", className = "com.yu.webservice.endpoint.mywsimport.GetMObileInfoResponse")
    @Action(input = "http://WebXml.com.cn/PhoneService/getMObileInfoRequest", output = "http://WebXml.com.cn/PhoneService/getMObileInfoResponse")
    public Phone getMObileInfo(
        @WebParam(name = "osName", targetNamespace = "")
        String osName);

}