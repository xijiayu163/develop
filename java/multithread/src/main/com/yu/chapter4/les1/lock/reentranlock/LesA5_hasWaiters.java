package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesA5_hasWaiters {

	public static class Service {

		private ReentrantLock lock = new ReentrantLock();
		private Condition newCondition = lock.newCondition();

		public void waitMethod() {
			try {
				lock.lock();
				newCondition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void notityMethod() {
			try {
				lock.lock();
				System.out.println("有没有线程正在等待newCondition？"
						+ lock.hasWaiters(newCondition) + " 线程数是多少？"
						+ lock.getWaitQueueLength(newCondition));
				newCondition.signal();
				System.out.println("signal后有没有线程正在等待newCondition？"
						+ lock.hasWaiters(newCondition) + " 线程数是多少？"
						+ lock.getWaitQueueLength(newCondition));
			} finally {
				lock.unlock();
			}
		}

	}
	
	/**
	 * boolean hasWaiters(Condition condition) 查询是否有线程正在等待与此锁定有关的
	 * condition条件
	 * 
	 * 有没有线程正在等待newCondition？true 线程数是多少？10
signal后有没有线程正在等待newCondition？true 线程数是多少？9
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final Service service = new Service();

		Runnable runnable = new Runnable() {
			public void run() {
				service.waitMethod();
			}
		};

		Thread[] threadArray = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threadArray[i] = new Thread(runnable);
		}
		for (int i = 0; i < 10; i++) {
			threadArray[i].start();
		}
		Thread.sleep(2000);
		service.notityMethod();
	}
}
