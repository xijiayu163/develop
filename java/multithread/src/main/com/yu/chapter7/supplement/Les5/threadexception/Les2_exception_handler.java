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
	 * setUncaughtExceptionHandler 的作用是对指定的线程对象设置默认的异常处理器
	 * 在Thread类中还可以使用setDefaultUncaughtExceptionHandler对所有线程对象
	 * 设置异常处理器
	 * 
	 * 线程:线程t1 出现了异常：
java.lang.NullPointerException
	at com.yu.chapter7.supplement.Les5.exception.Main2$MyThread.run(Main2.java:11)
Exception in thread "线程t2" java.lang.NullPointerException
	at com.yu.chapter7.supplement.Les5.exception.Main2$MyThread.run(Main2.java:11)
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		t1.setName("线程t1");
		t1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("线程:" + t.getName() + " 出现了异常：");
				e.printStackTrace();
			}
		});
		t1.start();

		MyThread t2 = new MyThread();
		t2.setName("线程t2");
		t2.start();
	}
}
