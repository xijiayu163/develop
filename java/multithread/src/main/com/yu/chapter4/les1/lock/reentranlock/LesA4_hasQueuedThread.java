package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesA4_hasQueuedThread {

	public static class Service {

		public ReentrantLock lock = new ReentrantLock();
		public Condition newCondition = lock.newCondition();

		public void waitMethod() {
			try {
				lock.lock();
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	/**
	 * 
	 * hasQueuedThread(Thread thread) 查询指定的线程是否正在等待获取此锁定
	 * hasQueuedThreads() 查询是否有线程正在等待获取此锁定
	 * 
	 * false
true
true

	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final Service service = new Service();

		Runnable runnable = new Runnable() {
			public void run() {
				service.waitMethod();
			}
		};

		Thread threadA = new Thread(runnable);
		threadA.start();

		Thread.sleep(500);

		Thread threadB = new Thread(runnable);
		threadB.start();

		Thread.sleep(500);
		System.out.println(service.lock.hasQueuedThread(threadA));
		System.out.println(service.lock.hasQueuedThread(threadB));
		System.out.println(service.lock.hasQueuedThreads());
	}
}
