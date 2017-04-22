package com.yu.chapter4.les2.lock.reentranreadwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Les1_readread_share {

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
				// TODO Auto-generated catch block
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
			service.read();
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
			service.read();
		}
	}
	
	/**
	 * ¶Á¶Á¹²Ïí
	 * 
	 * »ñµÃ¶ÁËøA 1492853114834
		»ñµÃ¶ÁËøB 1492853114835
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
