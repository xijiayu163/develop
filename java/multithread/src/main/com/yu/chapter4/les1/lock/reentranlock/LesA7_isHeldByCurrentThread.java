package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.ReentrantLock;

public class LesA7_isHeldByCurrentThread {

	public static class Service {

		private ReentrantLock lock;

		public Service(boolean isFair) {
			super();
			lock = new ReentrantLock(isFair);
		}

		public void serviceMethod() {
			try {
				System.out.println(lock.isHeldByCurrentThread());
				lock.lock();
				System.out.println(lock.isHeldByCurrentThread());
			} finally {
				lock.unlock();
			}
		}

	}
	
	/**
	 * isHeldByCurrentThread 查询当前线程是否保持此锁定
	 * 
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
