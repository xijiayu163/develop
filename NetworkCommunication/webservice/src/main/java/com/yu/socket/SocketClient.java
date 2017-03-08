package com.yu.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws Exception{
        //��ȡ�û����������
        Scanner input=new Scanner(System.in);
        System.out.println("����������:");
        String inputData=input.nextLine();

        //����һ��Socket�˿�
        Socket sc=new Socket("127.0.0.1", 6666);
        OutputStream os=sc.getOutputStream();
        os.write(inputData.getBytes());

        //��ȡ����˻ش�������
        InputStream is = sc.getInputStream(); 
        byte[] buffer=new byte[1024];
        int len=-1;
        len=is.read(buffer);
        String getData=new String(buffer, 0, len);
        System.out.println("�ӷ���˻�ȡ������:"+getData);
        //�Ƿ���
        is.close();
        os.close();
        sc.close();

    }

}
