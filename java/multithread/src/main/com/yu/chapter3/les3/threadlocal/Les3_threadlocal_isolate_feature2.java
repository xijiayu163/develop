package com.yu.chapter3.les3.threadlocal;

import java.util.Date;

public class Les3_threadlocal_isolate_feature2 {

	public static class Tools {
		public static ThreadLocal<Date> tl = new ThreadLocal<Date>();
	}
	
	public static class ThreadA extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 20; i++) {
					if (Tools.tl.get() == null) {
						Tools.tl.set(new Date());
					}
					System.out.println("A " + Tools.tl.get().getTime());
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public static class ThreadB extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 20; i++) {
					if (Tools.tl.get() == null) {
						Tools.tl.set(new Date());
					}
					System.out.println("B " + Tools.tl.get().getTime());
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * threadlocal 线程局部变量隔离性 
	 * 两个线程拥有自己的日期值
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ThreadA a = new ThreadA();
			a.start();

			Thread.sleep(1000);

			ThreadB b = new ThreadB();
			b.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
