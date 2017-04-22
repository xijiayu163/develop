package com.yu.chapter7.supplement.Les5.threadexception;

import java.lang.Thread.UncaughtExceptionHandler;


public class Les3_exception_handler_toAll {

	public static class MyThread extends Thread {
		@Override
		public void run() {
			String username = null;
			System.out.println(username.hashCode());
		}

	}
	
	
	/**
	 * 使用setDefaultUncaughtExceptionHandler对所有线程对象设置异常处理器
	 * 
	 * 
	 * 
	 * 线程:线程t2 出现了异常：
线程:线程t1 出现了异常：
java.lang.NullPointerException
	at com.yu.chapter7.supplement.Les5.exception.Main3$MyThread.run(Main3.java:12)
java.lang.NullPointerException
	at com.yu.chapter7.supplement.Les5.exception.Main3$MyThread.run(Main3.java:12)
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread
				.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					public void uncaughtException(Thread t, Throwable e) {
						System.out.println("线程:" + t.getName() + " 出现了异常：");
						e.printStackTrace();

					}
				});

		MyThread t1 = new MyThread();
		t1.setName("线程t1");
		t1.start();

		MyThread t2 = new MyThread();
		t2.setName("线程t2");
		t2.start();
	}
}
