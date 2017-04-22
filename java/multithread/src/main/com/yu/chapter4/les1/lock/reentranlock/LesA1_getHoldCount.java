package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.ReentrantLock;

public class LesA1_getHoldCount {

	public static class Service {

		private ReentrantLock lock = new ReentrantLock();

		public void serviceMethod1() {
			try {
				lock.lock();
				System.out.println("serviceMethod1 getHoldCount="
						+ lock.getHoldCount());
				serviceMethod2();
			} finally {
				lock.unlock();
			}
		}

		public void serviceMethod2() {
			try {
				lock.lock();
				System.out.println("serviceMethod2 getHoldCount="
						+ lock.getHoldCount());
			} finally {
				lock.unlock();
			}
		}
	}
	
	/**
	 * getHoldCount()的作用是查询当前线程保持此锁定的个数，也就是调用lock()方法的次数
	 * 
	 * serviceMethod1 getHoldCount=1
serviceMethod2 getHoldCount=2
	 * @param args
	 */
	public static void main(String[] args) {
		Service service = new Service();
		service.serviceMethod1();
	}
}
