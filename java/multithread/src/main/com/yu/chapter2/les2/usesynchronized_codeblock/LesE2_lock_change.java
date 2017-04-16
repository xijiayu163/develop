package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesE2_lock_change {

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
	 * 锁被改变了，但结果是同步的，因为AB共同争抢的锁是"123"
	 * A begin 1492323431213
A   end 1492323433214
B begin 1492323433214
B   end 1492323435215
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
//		Thread.sleep(50);// 存在50毫秒
		b.start();
	}
}
