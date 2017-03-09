
package com.yu.webservice.cxf.wsimport;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.yu.webservice.cxf.wsimport package. 
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

    private final static QName _GetLanguageResponse_QNAME = new QName("http://cxf.webservice.yu.com/", "getLanguageResponse");
    private final static QName _GetLanguage_QNAME = new QName("http://cxf.webservice.yu.com/", "getLanguage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.yu.webservice.cxf.wsimport
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLanguageResponse }
     * 
     */
    public GetLanguageResponse createGetLanguageResponse() {
        return new GetLanguageResponse();
    }

    /**
     * Create an instance of {@link GetLanguage }
     * 
     */
    public GetLanguage createGetLanguage() {
        return new GetLanguage();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLanguageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxf.webservice.yu.com/", name = "getLanguageResponse")
    public JAXBElement<GetLanguageResponse> createGetLanguageResponse(GetLanguageResponse value) {
        return new JAXBElement<GetLanguageResponse>(_GetLanguageResponse_QNAME, GetLanguageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLanguage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://cxf.webservice.yu.com/", name = "getLanguage")
    public JAXBElement<GetLanguage> createGetLanguage(GetLanguage value) {
        return new JAXBElement<GetLanguage>(_GetLanguage_QNAME, GetLanguage.class, null, value);
    }

}
