package com.yu.chapter7.supplement.Les7.exception.transfer;

import java.lang.Thread.UncaughtExceptionHandler;

public class Run2 {

	public static class ObjectUncaughtExceptionHandler implements UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println("������쳣����");
			e.printStackTrace();
		}

	}
	
	public static class StateUncaughtExceptionHandler implements UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println("��̬���쳣����");
			e.printStackTrace();
		}

	}
	
	public static class MyThread extends Thread {

		private String num = "a";

		public MyThread() {
			super();
		}

		public MyThread(ThreadGroup group, String name) {
			super(group, name);
		}

		@Override
		public void run() {
			int numInt = Integer.parseInt(num);
			System.out.println("���߳��д�ӡ��" + (numInt + 1));
		}

	}
	
	public static class MyThreadGroup extends ThreadGroup {

		public MyThreadGroup(String name) {
			super(name);
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			super.uncaughtException(t, e);
			System.out.println("�߳�����쳣����");
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * ��̬���쳣����
java.lang.NumberFormatException: For input string: "a"
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Integer.parseInt(Integer.java:580)
	at java.lang.Integer.parseInt(Integer.java:615)
	at com.yu.chapter7.supplement.Les7.exception.transfer.Run2$MyThread.run(Run2.java:37)
java.lang.NumberFormatException: For input string: "a"�߳�����쳣����

	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Integer.parseInt(Integer.java:580)
	at java.lang.Integer.parseInt(Integer.java:615)
	at com.yu.chapter7.supplement.Les7.exception.transfer.Run2$MyThread.run(Run2.java:37)

	 * @param args
	 */
	public static void main(String[] args) {
		MyThreadGroup group = new MyThreadGroup("�ҵ��߳���");
		MyThread myThread = new MyThread(group, "�ҵ��߳�");
//		 ����
		 myThread
		 .setUncaughtExceptionHandler(new ObjectUncaughtExceptionHandler());
//		 ��
//		 MyThread
//		 .setDefaultUncaughtExceptionHandler(new
//		 StateUncaughtExceptionHandler());
		myThread.start();

	}
}
