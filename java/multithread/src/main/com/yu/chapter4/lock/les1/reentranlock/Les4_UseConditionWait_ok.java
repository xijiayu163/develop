package com.yu.chapter4.lock.les1.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Les4_UseConditionWait_ok {

	public static class MyService {
		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public void waitMethod() {
			try {
				lock.lock();
				System.out.println("A");
				condition.await();
				System.out.println("B");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
				System.out.println("锁释放了！");
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
			myService.waitMethod();
		}

	}
	
	/**
	 * 在控制台只打印了一个A，原因是调用了Condition对象的await()方法，使当前执行任务的
	 * 线程进入了等待waiting状态
	 * @param args
	 */
	public static void main(String[] args) {
		MyService myService = new MyService();
		MyThreadA a = new MyThreadA(myService);
		a.start();
	}
}
