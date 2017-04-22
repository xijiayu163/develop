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
				System.out.println("��ƽ�������" + lock.isFair());
			} finally {
				lock.unlock();
			}
		}

	}
	
	/**
	 * isFair()�ж��ǲ��ǹ�ƽ��,Ĭ�������ReentrantLockʹ�õ��Ƿǹ�ƽ��
	 * 
	 * ��ƽ�������true
��ƽ�������false
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
