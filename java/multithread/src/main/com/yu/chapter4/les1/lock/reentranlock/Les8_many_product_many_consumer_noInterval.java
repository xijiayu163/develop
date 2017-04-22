package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Les8_many_product_many_consumer_noInterval {

	public static class MyService {

		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();
		private boolean hasValue = false;

		public void set() {
			try {
				lock.lock();
				while (hasValue == true) {
					System.out.println("有可能★★连续");
					condition.await();
				}
				System.out.println("打印★");
				hasValue = true;
				condition.signal();
//				condition.signalAll();
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
					System.out.println("有可能☆☆连续");
					condition.await();
				}
				System.out.println("打印☆");
				hasValue = false;
				condition.signal();
//				condition.signalAll();
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
	 * 有可能出现连续打印及假死情况
	 * 解决假死情况将sign用signAll代替
	 * 
	 * 运行结果 
	 * 有可能☆☆连续
有可能☆☆连续
有可能☆☆连续
打印★
有可能★★连续
有可能★★连续
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		MyService service = new MyService();

		MyThreadA[] threadA = new MyThreadA[10];
		MyThreadB[] threadB = new MyThreadB[10];

		for (int i = 0; i < 10; i++) {
			threadA[i] = new MyThreadA(service);
			threadB[i] = new MyThreadB(service);
			threadA[i].start();
			threadB[i].start();
		}

	}
}
