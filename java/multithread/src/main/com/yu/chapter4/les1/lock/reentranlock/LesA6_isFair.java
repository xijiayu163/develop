package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.ReentrantLock;

public class LesA6_isFair {
	
	public static class Service {

		private ReentrantLock lock;

		public Service(boolean isFair) {
			super();
			lock = new ReentrantLock(isFair);
		}

		public void serviceMethod() {
			try {
				lock.lock();
				System.out.println("公平锁情况：" + lock.isFair());
			} finally {
				lock.unlock();
			}
		}

	}
	
	/**
	 * isFair()判断是不是公平锁,默认情况下ReentrantLock使用的是非公平锁
	 * 
	 * 公平锁情况：true
公平锁情况：false
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final Service service1 = new Service(true);
		Runnable runnable = new Runnable() {
			public void run() {
				service1.serviceMethod();
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();

		final Service service2 = new Service(false);
		runnable = new Runnable() {
			public void run() {
				service2.serviceMethod();
			}
		};
		thread = new Thread(runnable);
		thread.start();

	}
}
