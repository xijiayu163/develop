package com.yu.chapter7.supplement.Les5.threadexception;

import java.lang.Thread.UncaughtExceptionHandler;

public class Les2_exception_handler {

	public static class MyThread extends Thread {
		@Override
		public void run() {
			String username = null;
			System.out.println(username.hashCode());
		}

	}
	
	/**
	 * setUncaughtExceptionHandler �������Ƕ�ָ�����̶߳�������Ĭ�ϵ��쳣������
	 * ��Thread���л�����ʹ��setDefaultUncaughtExceptionHandler�������̶߳���
	 * �����쳣������
	 * 
	 * �߳�:�߳�t1 �������쳣��
java.lang.NullPointerException
	at com.yu.chapter7.supplement.Les5.exception.Main2$MyThread.run(Main2.java:11)
Exception in thread "�߳�t2" java.lang.NullPointerException
	at com.yu.chapter7.supplement.Les5.exception.Main2$MyThread.run(Main2.java:11)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		t1.setName("�߳�t1");
		t1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("�߳�:" + t.getName() + " �������쳣��");
				e.printStackTrace();
			}
		});
		t1.start();

		MyThread t2 = new MyThread();
		t2.setName("�߳�t2");
		t2.start();
	}
}
