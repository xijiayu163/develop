package com.yu.chapter4.les2.lock.reentranreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Les2_writewrite_mutex {

	public static class Service {

		private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

		public void write() {
			try {
				try {
					lock.writeLock().lock();
					System.out.println("»ñµÃÐ´Ëø" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
					Thread.sleep(10000);
				} finally {
					lock.writeLock().unlock();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static class ThreadA extends Thread {

		private Service service;

		public ThreadA(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.write();
		}

	}

	public static class ThreadB extends Thread {

		private Service service;

		public ThreadB(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.write();
		}
	}

	/**
	 * Ð´Ð´»¥³â
	 * @param args
	 */
	public static void main(String[] args) {

		Service service = new Service();

		ThreadA a = new ThreadA(service);
		a.setName("A");

		ThreadB b = new ThreadB(service);
		b.setName("B");

		a.start();
		b.start();

	}

}
