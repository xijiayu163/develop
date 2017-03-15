package com.yu.webservice.transfer.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class WSWorker
{

    private static Logger logger = Logger.getLogger(WSWorker.class.getName());
    
    public static void main(String [] args) throws SOAPException{
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("arg0", "1");
    	JSONArray jsonArray = performRequest("q0","getDoctor1","http://service.transfer.webservice.yu.com/",map,"http://127.0.0.1:8888/ws/cxf/DoctorService1?WSDL");
    	String jsonString = jsonArray.toJSONString();
    	System.out.println(jsonString);
    }

    /**
     * ����JSONArray��ʽ�Ľ��
     * 
     * params�����ͬһ�����������Ԫ�أ���Ҫ������Ԫ�طŵ�list�У�key���䣬valueʹ���µ�list
     * @param prefix ����ǰ׺
     * @param methodName ������
     * @param ns �����ռ�
     * @param params ����
     * @param wsdl wsdl
     * @return
     * @throws SOAPException ����soap����ʱ�����쳣
     */
    public static JSONArray performRequest(String prefix, String methodName, String ns, Map<String, Object> params, String wsdl) throws SOAPException
    {
        return parseXML(getSOAPMessage(prefix, methodName, ns, params, wsdl));
    }

    /**
     * 
     * @param prefix ����ǰ׺
     * @param methodName ������
     * @param ns �����ռ�
     * @param params ����
     * @param wsdl wsdl
     * @return
     * @throws SOAPException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private static String getSOAPMessage(String prefix, String methodName, String ns, Map<String, Object> params, String wsdl) throws SOAPException
    {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();
        message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");

        SOAPPart soapPart = message.getSOAPPart();// ����soap����
        SOAPEnvelope envelope = soapPart.getEnvelope();

        envelope.setAttribute("xmlns:" + prefix, ns);
        
        SOAPBody body = envelope.getBody();
        SOAPElement bodyElement = body.addChildElement(envelope.createName(methodName, prefix, ""));

        if (null != params)
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                if (entry.getValue() instanceof Map)
                {
                    SOAPElement inputParam = bodyElement.addChildElement(entry.getKey());
                    addParams((Map<String, Object>)entry.getValue(), inputParam);
                }
                else if (entry.getValue() instanceof String)
                {
                    bodyElement.addChildElement(entry.getKey()).addTextNode((String)entry.getValue());
                }
                else if (entry.getValue() instanceof List)
                {
                    for(Object item : (List)entry.getValue())
                    {
                        SOAPElement inputParam = bodyElement.addChildElement(entry.getKey());
                        
                        addParams((Map<String, Object>)item, inputParam);
                    }
                }
                else if (entry.getValue() instanceof Integer 
                        || entry.getValue() instanceof Long 
                        || entry.getValue() instanceof Double 
                        || entry.getValue() instanceof Short
                        || entry.getValue() instanceof Float)
                {
                    bodyElement.addChildElement(entry.getKey()).addTextNode(String.valueOf(entry.getValue()));
                }
            }
        }

        message.saveChanges();
        
        String xmlString = "";
        try
        {
            xmlString = getXMLMessage(message);
            
//            logger.debug(xmlString);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return getResultFromWebService(xmlString, wsdl);
    }

  
    @SuppressWarnings("unchecked")
    private static void addParams(Map<String, Object> params, SOAPElement parentElement) throws SOAPException
    {
        if (null != params)
        {
            for (Map.Entry<String, Object> entry : params.entrySet())
            {
                if (entry.getValue() instanceof Map)
                {
                    SOAPElement element = parentElement.addChildElement(entry.getKey());
                    addParams((Map<String, Object>)entry.getValue(), element);
                }
                else if (entry.getValue() instanceof String)
                {
                    parentElement.addChildElement(entry.getKey()).addTextNode((String)entry.getValue());
                }
                else if (entry.getValue() instanceof List)
                {
                    for(Object item : (List)entry.getValue())
                    {
                        SOAPElement inputParam = parentElement.addChildElement(entry.getKey());
                        
                        addParams((Map<String, Object>)item, inputParam);
                    }
                }
            }
        }
    }

    private static String getXMLMessage(SOAPMessage msg) throws SOAPException, IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        msg.writeTo(baos);
        String str = baos.toString("utf-8");
        baos.close();
        return str;
    }


    /**
     * ����WebService��ȡ���
     * 
     * @param xml
     * @param endPoint
     * @return
     */
    private static String getResultFromWebService(String xml, String endPoint)
    {

        String result = "";

        // ��������ʵ��
        PostMethod postMethod = new PostMethod(endPoint);
        try
        {
            byte[] b = xml.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b, 0, b.length);
            RequestEntity re = new InputStreamRequestEntity(is, b.length, "text/xml; charset=utf-8");
            postMethod.setRequestEntity(re);
            HttpClient httpClient = new HttpClient();
            // ִ��
            int statusCode = httpClient.executeMethod(postMethod);
            // �ж��Ƿ�ִ�гɹ�
            if (statusCode != HttpStatus.SC_OK)
            {
                logger.error("Method failed: " + postMethod.getStatusLine());
            }
            else
            {
                // ��������
                result = postMethod.getResponseBodyAsString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // �ͷ����ӣ�����ִ�з����Ƿ�ɹ����������ͷ����ӡ�
            if (postMethod != null)
            {
                postMethod.releaseConnection();
            }
        }

//        logger.debug("�����" + result);
        return result;
    }

    /**
     * ����xml�ַ���
     * 
     * @param xmlDoc
     *            ����wsdl���ص�����
     * @return �������������������ʧ�ܣ�����null
     */
    private static JSONArray parseXML(String xmlDoc)
    {
        JSONArray resultArray = null;
        StringReader read = new StringReader(xmlDoc);
        // �����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����
        InputSource source = new InputSource(read);
        // ����һ���µ�SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        // List infoList = new ArrayList();
        try
        {
            // ͨ������Դ����һ��Document
            Document doc = sb.build(source);
            // ȡ�ĸ�Ԫ��
            Element root = doc.getRootElement();
            // �õ���Ԫ��������Ԫ�صļ���
            // Namespace ns = root.getNamespace();
            List<Element> bodyList = root.getChildren();
            if (bodyList != null && bodyList.size() > 0)
            {
                Element body = (Element) bodyList.get(0);// Body
                List<Element> responses = body.getChildren();
                if (responses != null && responses.size() > 0)
                {
                    Element response = (Element) responses.get(0);
                    List<Element> results = response.getChildren();
                    if (results != null && results.size() > 0)
                    {
                        resultArray = new JSONArray();
                        for (int i = 0; i < results.size(); i++)
                        {
                            Element result = (Element) results.get(i);

                            resultArray.add(convertToJSONOrText(result));
                        }
                    }
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return resultArray;
    }

    private static Object convertToJSONOrText(Element item)
    {

        List<Element> elements = item.getChildren();
        
        if(elements.size() != 0)
        {
            JSONObject map = new JSONObject();
            for(Element element : elements)
            {
                int count = 0;
                String key = element.getName();
                //��Щ�ӿڣ����ص�������������
                if(map.containsKey(key))
                {
                    count = getSameKeyCount(map, key);
                    if(0 != count)
                    {
                        if(1 == count)
                        {
                            key = element.getName() + "_@@_start_" + count;
                        }
                        else
                        {
                            key = element.getName() + "_@@_" + count;
                        }
                    }
                }
                
                map.put(key, convertToJSONOrText1(element));
            }
            map = reformatMap(map);

            return map;
        }
        else
        {
            return item.getText();
        }
        
    }
    

    private static Object convertToJSONOrText1(Element item)
    {

        List<Element> elements = item.getChildren();
        
        if(elements.size() != 0)
        {
            JSONObject map = new JSONObject();
            for(Element element : elements)
            {
                int count = 0;
                String key = element.getName();
                //��Щ�ӿڣ����ص�������������
                if(map.containsKey(key))
                {
                    count = getSameKeyCount(map, key);
                    if(0 != count)
                    {
                        if(1 == count)
                        {
                            key = element.getName() + "_@@_start_" + count;
                        }
                        else
                        {
                            key = element.getName() + "_@@_" + count;
                        }
                    }
                }
                
                map.put(key, convertToJSONOrText1(element));
            }
            map = reformatMap(map);

            return map;
        }
        else
        {
            return item.getText();
        }
        
    }

    /**
     * map����keyΪ��ʼ����Ŀ����
     * @param map
     * @return
     */
    private static int getSameKeyCount(JSONObject map, String key)
    {
        int count = 0;
        for(Map.Entry<String, Object> tmp : map.entrySet())
        {
            if(tmp.getKey().startsWith(key))
            {
                count++;
            }
        }
        return count;
    }

    /**
     * ����������key��Ӧ����������һ��
     * @param map
     */
    private static JSONObject reformatMap(JSONObject map)
    {
        JSONObject result = new JSONObject();
        for(Map.Entry<String, Object> entry : map.entrySet())
        {
            //��ǰkey���к���������������
            String key = entry.getKey();
            
            if(map.containsKey(key+"_@@_start_1"))
            {
                JSONArray array = new JSONArray();
                

                array.add(map.get(key));
                array.add(map.get(key+"_@@_start_1"));
                
                int size = map.entrySet().size();
                for(int i=2; i<size; i++)
                {
                    Object object = map.get(key + "_@@_" + i);
                    if(null != object)
                    {
                        array.add(object);
                    }
                }

                result.put(key, array);
                
            }
            else if(-1 == key.indexOf("_@@_"))
            {
                result.put(key, entry.getValue());
            }
        }
        
        return result;
    }    
    
}