package com.yu.chapter4.lock.les1.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Les3_Use_one_ConditionWaitNotifyOK {

	public static class MyService {

		private Lock lock = new ReentrantLock();
		public Condition condition = lock.newCondition();

		public void await() {
			try {
				lock.lock();
				System.out.println(" awaitʱ��Ϊ" + System.currentTimeMillis());
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void signal() {
			try {
				lock.lock();
				System.out.println("signalʱ��Ϊ" + System.currentTimeMillis());
				condition.signal();
			} finally {
				lock.unlock();
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
	 * condition �ɹ�ʵ�ֵȴ�/֪ͨģʽ
	 * Object���е�wait�����൱��Condition���е�await����
	 * Object���е�wait(long)�൱��Condition���е�await(long time,TimeUnit unit)
	 * Object���е�notify()�����ǵ���Condition���signal()����
	 * Object���е�notifyAll()�����൱��Condition���е�signalAll()����
	 * 
	 *  awaitʱ��Ϊ1492471373899
		signalʱ��Ϊ1492471376899
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		MyService service = new MyService();

		ThreadA a = new ThreadA(service);
		a.start();

		Thread.sleep(3000);

		service.signal();

	}

}
