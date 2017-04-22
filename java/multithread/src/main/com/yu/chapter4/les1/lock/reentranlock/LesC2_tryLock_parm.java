package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LesC2_tryLock_parm {

	public static class MyService {

		public ReentrantLock lock = new ReentrantLock();

		public void waitMethod() {
			try {
				if (lock.tryLock(3, TimeUnit.SECONDS)) {
					System.out.println("      " + Thread.currentThread().getName()
							+ "获得锁的时间：" + System.currentTimeMillis());
					Thread.sleep(10000);
				} else {
					System.out.println("      " + Thread.currentThread().getName()
							+ "没有获得锁");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (lock.isHeldByCurrentThread()) {
					lock.unlock();
				}
			}
		}
	}
	
	
	/**
	 * tryLock(long timeout,TimeUnit unit)的作用是，如果锁定在给定时间内没有
	 * 被另一个线程保持， 且当前线程未被中断，则获取该锁定
	 * 
	 * 线程A3秒后获取锁，线程B超时未获得锁
	 * 
	 * A调用waitMethod时间：1492849203670
B调用waitMethod时间：1492849203670
      A获得锁的时间：1492849203723
      B没有获得锁
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final MyService service = new MyService();

		Runnable runnableRef = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()
						+ "调用waitMethod时间：" + System.currentTimeMillis());
				service.waitMethod();
			}
		};

		Thread threadA = new Thread(runnableRef);
		threadA.setName("A");
		threadA.start();
		Thread threadB = new Thread(runnableRef);
		threadB.setName("B");
		threadB.start();
	}
}
