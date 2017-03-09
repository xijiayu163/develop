package com.yu.webservice.soap;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**���õ�������webservice���� ����ȡ�绰������Ϣ
 * 
 * 
 */
public class MobileCodeService {
    //1. http-get��ʽ����webservice
    public void get(String mobileCode ,String userID ) throws Exception{
        URL url=new URL("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo?mobileCode="+mobileCode+
                "&userID="+userID);
        HttpURLConnection conn=(HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){ //�����=200
            InputStream is=conn.getInputStream();
            //�ڴ��� ��  
            ByteArrayOutputStream boas=new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len=-1;
            while((len=is.read(buffer))!=-1){
                boas.write(buffer, 0, len);
            }
            System.out.println("GET�����ȡ������:"+boas.toString());    
            boas.close();
            is.close();
        }
    }

    //2.Post���� ��ͨ��Http-Client �����ģ��ʵ�� Http����
    public void post(String mobileCode ,String userID) throws Exception{
        /**HttpClient���������ʵ�ֲ��裺
         *  1. ׼��һ������ͻ���:����� 
         *  2. ׼������ʽ�� GET ��POST
         *  3. ����Ҫ���ݵĲ���
         *  4.ִ������
         *  5. ��ȡ���
         */
        HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo");
        //3.�����������
        postMethod.setParameter("mobileCode", mobileCode);
        postMethod.setParameter("userID", userID);
        //4.ִ������ ,�����
        int code=client.executeMethod(postMethod);
        //5. ��ȡ���
        String result=postMethod.getResponseBodyAsString();
        System.out.println("Post����Ľ����"+result);
    }
    //2.Post���� ��ͨ��Http-Client �����ģ��ʵ�� Http����
    public void soap() throws Exception{
        HttpClient client=new HttpClient();
        PostMethod postMethod=new PostMethod("http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo");
        //3.�����������
        NameValuePair[] data = { new NameValuePair("mobileCode", "15626217879"),new NameValuePair("userID", "") };
        postMethod.setRequestBody(data);  
        //postMethod.setRequestBody(new FileInputStream("c:/soap.xml")); 
          //�޸������ͷ��
          //postMethod.setRequestHeader("Content-Type", "text/xml; charset=utf-8");
        //4.ִ������ ,�����
        int code=client.executeMethod(postMethod);
        System.out.println("�����:"+code);
        //5. ��ȡ���
        String result=postMethod.getResponseBodyAsString();
        System.out.println("Post����Ľ����"+result);
    }

    public static void main(String[] args) throws Exception{
        MobileCodeService ws=new MobileCodeService();
//        ws.get("15626217879", "");
//        ws.post("15626217879", "");
        ws.soap();

    }

}