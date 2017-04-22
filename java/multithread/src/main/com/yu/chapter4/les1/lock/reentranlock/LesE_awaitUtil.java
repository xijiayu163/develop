package com.yu.chapter4.les1.lock.reentranlock;

import java.util.Calendar;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesE_awaitUtil {

	public static class Service {

		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public void waitMethod() {
			try {
				Calendar calendarRef = Calendar.getInstance();
				calendarRef.add(Calendar.SECOND, 10);
				lock.lock();
				System.out
						.println("wait begin timer=" + System.currentTimeMillis());
				condition.awaitUntil(calendarRef.getTime());
				System.out
						.println("wait   end timer=" + System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}

		}

		public void notifyMethod() {
			try {
				Calendar calendarRef = Calendar.getInstance();
				calendarRef.add(Calendar.SECOND, 10);
				lock.lock();
				System.out
						.println("notify begin timer=" + System.currentTimeMillis());
				condition.signalAll();
				System.out
						.println("notify   end timer=" + System.currentTimeMillis());
			} finally {
				lock.unlock();
			}

		}
	}
	
	public static class MyThreadA extends Thread {

		private Service service;

		public MyThreadA(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.waitMethod();
		}

	}
	
	public static class MyThreadB extends Thread {

		private Service service;

		public MyThreadB(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.notifyMethod();
		}

	}
	
//	/**
//	 * awaitUnit(datetime) 到指定时间前如果没有被唤醒则自动唤醒 
//	 * 
//	 * 10秒后自动唤醒
//	 * 
//	 * wait begin timer=1492852268545
//wait   end timer=1492852278461
//	 * @param args
//	 * @throws InterruptedException
//	 */
//	public static void main(String[] args) throws InterruptedException {
//		Service service = new Service();
//		MyThreadA myThreadA = new MyThreadA(service);
//		myThreadA.start();
//	}

	/**
	 * 说明线程在等待时间到达可以被其他线程唤醒
	 * 
	 * wait begin timer=1492852490401
notify begin timer=1492852492350
notify   end timer=1492852492351
wait   end timer=1492852492351
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Service service = new Service();
		MyThreadA myThreadA = new MyThreadA(service);
		myThreadA.start();

		Thread.sleep(2000);

		MyThreadB myThreadB = new MyThreadB(service);
		myThreadB.start();
	}
}
