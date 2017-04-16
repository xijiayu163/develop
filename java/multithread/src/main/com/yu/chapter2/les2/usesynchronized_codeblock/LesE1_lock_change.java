package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesE1_lock_change {

	public static class MyService {
		private String lock = "123";

		public void testMethod() {
			try {
				synchronized (lock) {
					System.out.println(Thread.currentThread().getName() + " begin "
							+ System.currentTimeMillis());
					lock = "456";
					Thread.sleep(2000);
					System.out.println(Thread.currentThread().getName() + "   end "
							+ System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class ThreadA extends Thread {

		private MyService service;

		public ThreadA(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.testMethod();
		}
	}
	
	public static class ThreadB extends Thread {

		private MyService service;

		public ThreadB(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.testMethod();
		}
	}
	
	/**
	 * 锁被改变了，不再锁同一个对象
	 * A begin 1492323219198
B begin 1492323219248
A   end 1492323221199
B   end 1492323221248
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		MyService service = new MyService();

		ThreadA a = new ThreadA(service);
		a.setName("A");

		ThreadB b = new ThreadB(service);
		b.setName("B");

		a.start();
		Thread.sleep(50);// 存在50毫秒
		b.start();
	}
}
