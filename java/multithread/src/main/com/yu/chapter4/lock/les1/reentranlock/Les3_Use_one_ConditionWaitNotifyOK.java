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
				System.out.println(" await时间为" + System.currentTimeMillis());
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
				System.out.println("signal时间为" + System.currentTimeMillis());
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
	 * condition 成功实现等待/通知模式
	 * Object类中的wait方法相当于Condition类中的await方法
	 * Object类中的wait(long)相当于Condition类中的await(long time,TimeUnit unit)
	 * Object类中的notify()方法是的于Condition类的signal()方法
	 * Object类中的notifyAll()方法相当于Condition类中的signalAll()方法
	 * 
	 *  await时间为1492471373899
		signal时间为1492471376899
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
