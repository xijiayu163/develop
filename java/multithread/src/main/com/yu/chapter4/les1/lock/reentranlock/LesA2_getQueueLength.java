package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.ReentrantLock;

public class LesA2_getQueueLength {

	public static class Service {

		public ReentrantLock lock = new ReentrantLock();

		public void serviceMethod1() {
			try {
				lock.lock();
				System.out.println("ThreadName=" + Thread.currentThread().getName()
						+ "进入方法！");
				Thread.sleep(Integer.MAX_VALUE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

	}
	
	/**
	 *getQueueLength()的作用是返回正等待获取此锁定的线程数。比如有5个线程，
	 *1 个线程首先执行await方法，那么在调用getQueueLength方法后返回值是4，说明
	 *有4个线程同时在等待lock的释放 
	 * 
	 * ThreadName=Thread-0进入方法！
有线程数：9在等待获取锁！
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final Service service = new Service();

		Runnable runnable = new Runnable() {
			public void run() {
				service.serviceMethod1();
			}
		};

		Thread[] threadArray = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threadArray[i] = new Thread(runnable);
		}
		for (int i = 0; i < 10; i++) {
			threadArray[i].start();
		}
		Thread.sleep(2000);
		System.out.println("有线程数：" + service.lock.getQueueLength() + "在等待获取锁！");

	}
}
