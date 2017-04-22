package com.yu.chapter6.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Les5_2_hungry_enum_recommend {
	
	public static class MyObject {

		public enum MyEnumSingleton {
			connectionFactory;

			private Connection connection;

			private MyEnumSingleton() {
				try {
					System.out.println("创建MyObject对象");
					String url = "jdbc:sqlserver://localhost:1079;databaseName=y2";
					String username = "sa";
					String password = "";
					String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
					Class.forName(driverName);
					connection = DriverManager.getConnection(url, username,
							password);
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

		public static Connection getConnection() {
			return MyEnumSingleton.connectionFactory.getConnection();
		}

	}
	
	public static class MyThread extends Thread {

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println(MyObject.getConnection().hashCode());
			}
		}
	}
	
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		MyThread t3 = new MyThread();

		t1.start();
		t2.start();
		t3.start();

	}
}
