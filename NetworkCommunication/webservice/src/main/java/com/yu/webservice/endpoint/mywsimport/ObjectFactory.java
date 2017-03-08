
package com.yu.webservice.endpoint.mywsimport;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.yu.webservice.endpoint.mywsimport package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetMObileInfo_QNAME = new QName("http://WebXml.com.cn/", "getMObileInfo");
    private final static QName _GetMObileInfoResponse_QNAME = new QName("http://WebXml.com.cn/", "getMObileInfoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.yu.webservice.endpoint.mywsimport
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMObileInfo }
     * 
     */
    public GetMObileInfo createGetMObileInfo() {
        return new GetMObileInfo();
    }

    /**
     * Create an instance of {@link GetMObileInfoResponse }
     * 
     */
    public GetMObileInfoResponse createGetMObileInfoResponse() {
        return new GetMObileInfoResponse();
    }

    /**
     * Create an instance of {@link Phone }
     * 
     */
    public Phone createPhone() {
        return new Phone();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMObileInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebXml.com.cn/", name = "getMObileInfo")
    public JAXBElement<GetMObileInfo> createGetMObileInfo(GetMObileInfo value) {
        return new JAXBElement<GetMObileInfo>(_GetMObileInfo_QNAME, GetMObileInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMObileInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebXml.com.cn/", name = "getMObileInfoResponse")
    public JAXBElement<GetMObileInfoResponse> createGetMObileInfoResponse(GetMObileInfoResponse value) {
        return new JAXBElement<GetMObileInfoResponse>(_GetMObileInfoResponse_QNAME, GetMObileInfoResponse.class, null, value);
    }

}
