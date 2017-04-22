package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.ReentrantLock;

public class LesA8_isLocked {

	public static class Service {

		private ReentrantLock lock;

		public Service(boolean isFair) {
			super();
			lock = new ReentrantLock(isFair);
		}

		public void serviceMethod() {
			try {
				System.out.println(lock.isLocked());
				lock.lock();
				System.out.println(lock.isLocked());
			} finally {
				lock.unlock();
			}
		}

	}
	
	
	/**
	 *isLocked() 查询此锁定是否由做生意线程保持
	 * 
	 * false
true
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
	}
}
