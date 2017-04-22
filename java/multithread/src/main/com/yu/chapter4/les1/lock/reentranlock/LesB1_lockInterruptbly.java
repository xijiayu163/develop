package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesB1_lockInterruptbly {

	public static class MyService {

		public ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public void waitMethod() {
			try {
				lock.lock();
				System.out
						.println("lock begin " + Thread.currentThread().getName());
				for (int i = 0; i < Integer.MAX_VALUE / 10; i++) {
					String newString = new String();
					Math.random();
				}
				System.out
						.println("lock   end " + Thread.currentThread().getName());
			} finally {
				if (lock.isHeldByCurrentThread()) {
					lock.unlock();
				}
			}
		}
	}
	
	
	/**
	 * interrupt后未抛异常
	 * 
	 * lock begin A
main end!
lock   end A
lock begin B
lock   end B
	

	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final MyService service = new MyService();
		Runnable runnableRef = new Runnable() {
			public void run() {
				service.waitMethod();
			}
		};

		Thread threadA = new Thread(runnableRef);
		threadA.setName("A");
		threadA.start();
		Thread.sleep(500);
		Thread threadB = new Thread(runnableRef);
		threadB.setName("B");
		threadB.start();
		threadB.interrupt();// 打标记
		System.out.println("main end!");
	}
}
