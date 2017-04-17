package com.yu.chapter4.lock.les1.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Les3_UseConditionWaitNotifyError {

	public static class MyService {

		private Lock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public void await() {
			try {
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadA extends Thread {

		private MyService service;

		public ThreadA(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.await();
		}
	}
	
	/**
	 * condition错误用法
	 * 报错的异常信息是监视器出错，解决的办法是必须在condition.await()方法调用之前调用
	 * lock.lock()代码获得同步监视器
	 * @param args
	 */
	public static void main(String[] args) {
		MyService service = new MyService();
		ThreadA a = new ThreadA(service);
		a.start();
	}
}
