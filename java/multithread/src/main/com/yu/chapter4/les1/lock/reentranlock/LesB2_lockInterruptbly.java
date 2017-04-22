package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesB2_lockInterruptbly {

	public static class MyService {

		public ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public void waitMethod() {
			try {
				lock.lockInterruptibly();
				System.out.println("lock " + Thread.currentThread().getName());
				for (int i = 0; i < Integer.MAX_VALUE / 10; i++) {
					String newString = new String();
					Math.random();
				}
			} catch (InterruptedException e) {
				System.out.println("线程"+Thread.currentThread().getName()+"进入catch~!");
				e.printStackTrace();
			} finally {
				if (lock.isHeldByCurrentThread()) {
					lock.unlock();
				}
			}
		}
	}
	
	
	/**
	 * interrupt后抛异常
	 * 
	 * lock A
main end!
线程B进入catch~!
java.lang.InterruptedException
	at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireInterruptibly(AbstractQueuedSynchronizer.java:1220)
	at java.util.concurrent.l
	

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
