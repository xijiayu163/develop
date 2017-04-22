package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesA3_getWaitQueueLength {

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
				System.out.println("有" + lock.getWaitQueueLength(newCondition)
						+ "个线程正在等待newCondition");
				newCondition.signal();
				System.out.println("signal后有" + lock.getWaitQueueLength(newCondition)
				+ "个线程正在等待newCondition");
			} finally {
				lock.unlock();
			}
		}

	}
	
	/**
	 * getWaitQueueLength(Condition condition)返回等待与此锁定相关的
	 * 给定条件Condition的线程估计数。
	 * 
	 * 运行结果:
	 * 有10个线程正在等待newCondition
signal后有9个线程正在等待newCondition
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
