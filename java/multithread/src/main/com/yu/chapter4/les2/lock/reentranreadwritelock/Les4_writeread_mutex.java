package com.yu.chapter4.les2.lock.reentranreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
public class Les4_writeread_mutex {

	public static class Service {

		private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

		public void read() {
			try {
				try {
					lock.readLock().lock();
					System.out.println("»ñµÃ¶ÁËø" + Thread.currentThread().getName()
							+ " " + System.currentTimeMillis());
					Thread.sleep(10000);
				} finally {
					lock.readLock().unlock();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void write() {
			try {
				try {
					lock.writeLock().lock();
					System.out.println("»ñµÃÐ´Ëø" + Thread.currentThread().getName()
							+ " " + System.currentTimeMillis());
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
	 * Ð´¶Á»¥³â
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		Service service = new Service();

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();

		Thread.sleep(1000);

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();

	}

}
