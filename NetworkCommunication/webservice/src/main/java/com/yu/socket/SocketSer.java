package com.yu.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketSer {

	public static void main(String[] args) throws Exception {

		ServerSocket ss = new ServerSocket(6666);
		boolean flag = true;
		while (flag) {
			// ���տͻ��˵�����
			System.out.println("�����ͻ��˵�����:");
			Socket sc = ss.accept();
			InputStream is = sc.getInputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			len = is.read(buffer);
			String getData = new String(buffer, 0, len);
			System.out.println("�ӿͻ��˻�ȡ������:" + getData);
			// ҵ���� ��Сдת��
			String outPutData = getData.toUpperCase();

			// ��ͻ���д����
			OutputStream os = sc.getOutputStream();
			os.write(outPutData.getBytes("UTF-8"));

			// �ͷ���Դ
			os.close();
			is.close();
			sc.close();
		}
		ss.close();
	}
}
