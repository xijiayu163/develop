package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Les7_one_product_one_consumer_interval {

	public static class MyService {

		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();
		private boolean hasValue = false;

		public void set() {
			try {
				lock.lock();
				while (hasValue == true) {
					condition.await();
				}
				System.out.println("打印★");
				hasValue = true;
				condition.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void get() {
			try {
				lock.lock();
				while (hasValue == false) {
					condition.await();
				}
				System.out.println("打印☆");
				hasValue = false;
				condition.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

	}
	
	public static class MyThreadA extends Thread {

		private MyService myService;

		public MyThreadA(MyService myService) {
			super();
			this.myService = myService;
		}

		@Override
		public void run() {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				myService.set();
			}
		}

	}
	
	public static class MyThreadB extends Thread {

		private MyService myService;

		public MyThreadB(MyService myService) {
			super();
			this.myService = myService;
		}

		@Override
		public void run() {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				myService.get();
			}
		}

	}
	
	/**
	 * 生产者 消费者交通打印
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		MyService myService = new MyService();

		MyThreadA a = new MyThreadA(myService);
		a.start();

		MyThreadB b = new MyThreadB(myService);
		b.start();

	}
}
