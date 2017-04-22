package com.yu.chapter6.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Les5_1_hungry_enum_notrecommend {

	public static enum MyObject {
		connectionFactory;

		private Connection connection;

		private MyObject() {
			try {
				System.out.println("������MyObject�Ĺ���");
				String url = "jdbc:sqlserver://localhost:1079;databaseName=ghydb";
				String username = "sa";
				String password = "";
				String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				Class.forName(driverName);
				connection = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public Connection getConnection() {
			return connection;
		}
	}
	
	public static class MyThread extends Thread {

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println(MyObject.connectionFactory.getConnection()
						.hashCode());
			}
		}
	}

	/**
	 * ö�ٺ;�̬�������������ƣ���ʹ��ö��ʱ�����췽���ᱻ�Զ����ã�Ҳ����Ӧ�����������ʵ�ֵ������ģʽ
	 * ȱ�㣺����Υ���˵�һְ��ԭ��
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		MyThread t3 = new MyThread();

		t1.start();
		t2.start();
		t3.start();

	}
}
