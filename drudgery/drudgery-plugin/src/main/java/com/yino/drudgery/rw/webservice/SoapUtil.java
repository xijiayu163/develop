package com.yino.drudgery.rw.webservice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
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

public class SoapUtil {
	
	private static Logger logger = Logger.getLogger(SoapUtil.class.getName());
	
    public static void main(String [] args) throws SOAPException{
//    	Map<String,Object> map = new HashMap<String, Object>();
//    	map.put("arg0", "1");
//    	JSONArray jsonArray = performRequest("q1111","getDoctor1","http://service.transfer.webservice.yu.com/",map,"http://127.0.0.1:8888/ws/cxf/DoctorService1?WSDL");
//    	String jsonString = jsonArray.toJSONString();
//    	System.out.println(jsonString);
    }
	
    /**
     * 返回JSONArray格式的结果
     * 
     * params中如果同一个层次有重名元素，需要将重名元素放到list中，key不变，value使用新的list
     * @param prefix 方法前缀
     * @param methodName 方法名
     * @param ns 命名空间
     * @param params 参数
     * @param wsdl wsdl
     * @return
     * @throws SOAPException 生成soap报文时发生异常
     */
    public static JSONArray performRequest(String prefix, String methodName, String ns, Map<String, Object> params, String wsdl) throws SOAPException
    {
        return parseXML(getSOAPMessage(prefix, methodName, ns, params, wsdl));
    }

    /**
     * 
     * @param prefix 方法前缀
     * @param methodName 方法名
     * @param ns 命名空间
     * @param params 参数
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

        SOAPPart soapPart = message.getSOAPPart();// 创建soap部分
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
     * 请求WebService获取结果
     * 
     * @param xml
     * @param endPoint
     * @return
     */
    private static String getResultFromWebService(String xml, String endPoint)
    {

        String result = "";

        // 创建连接实例
        PostMethod postMethod = new PostMethod(endPoint);
        try
        {
            byte[] b = xml.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b, 0, b.length);
            RequestEntity re = new InputStreamRequestEntity(is, b.length, "text/xml; charset=utf-8");
            postMethod.setRequestEntity(re);
            HttpClient httpClient = new HttpClient();
            // 执行
            int statusCode = httpClient.executeMethod(postMethod);
            // 判断是否执行成功
            if (statusCode != HttpStatus.SC_OK)
            {
                logger.error("Method failed: " + postMethod.getStatusLine());
            }
            else
            {
                // 返回数据
                result = postMethod.getResponseBodyAsString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 释放连接，无论执行方法是否成功，都必须释放连接。
            if (postMethod != null)
            {
                postMethod.releaseConnection();
            }
        }

//        logger.debug("结果：" + result);
        return result;
    }

    /**
     * 解析xml字符串
     * 
     * @param xmlDoc
     *            调用wsdl返回的数据
     * @return 请求结果，如果网络连接失败，返回null
     */
    private static JSONArray parseXML(String xmlDoc)
    {
        JSONArray resultArray = null;
        StringReader read = new StringReader(xmlDoc);
        // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
        InputSource source = new InputSource(read);
        // 创建一个新的SAXBuilder
        SAXBuilder sb = new SAXBuilder();
        // List infoList = new ArrayList();
        try
        {
            // 通过输入源构造一个Document
            Document doc = sb.build(source);
            // 取的根元素
            Element root = doc.getRootElement();
            // 得到根元素所有子元素的集合
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
                //有些接口，返回的数据是重名的
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
                //有些接口，返回的数据是重名的
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
     * map中以key为开始的条目数量
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
     * 将带索引的key对应的数据下移一层
     * @param map
     */
    private static JSONObject reformatMap(JSONObject map)
    {
        JSONObject result = new JSONObject();
        for(Map.Entry<String, Object> entry : map.entrySet())
        {
            //当前key还有后续带索引的数据
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
