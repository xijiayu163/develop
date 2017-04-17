package com.yu.chapter3.les3.threadlocal;

import java.util.Date;

public class Les5_multi_threadlocal_initialValue {

	public static class ThreadLocalExt extends ThreadLocal {
		@Override
		protected Object initialValue() {
			return new Date().getTime();
		}
	}
	
	public static class Tools {
		public static ThreadLocalExt tl = new ThreadLocalExt();
	}
	
	public static class ThreadA extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					System.out.println("在ThreadA线程中取值=" + Tools.tl.get());
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 多个线程的线程局部变量初始化
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println("       在Main线程中取值=" + Tools.tl.get());
				Thread.sleep(100);
			}
			Thread.sleep(5000);
			ThreadA a = new ThreadA();
			a.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
