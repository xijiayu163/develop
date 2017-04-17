package com.yu.chapter4.lock.les1.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Les5_MustUseMoreCondition_Error {

	public static class MyService {

		private Lock lock = new ReentrantLock();
		public Condition condition = lock.newCondition();

		public void awaitA() {
			try {
				lock.lock();
				System.out.println("begin awaitA时间为" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
				condition.await();
				System.out.println("  end awaitA时间为" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void awaitB() {
			try {
				lock.lock();
				System.out.println("begin awaitB时间为" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
				condition.await();
				System.out.println("  end awaitB时间为" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void signalAll() {
			try {
				lock.lock();
				System.out.println("  signalAll时间为" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
				condition.signalAll();
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
			service.awaitA();
		}
	}
	
	public static class ThreadB extends Thread {

		private MyService service;

		public ThreadB(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.awaitB();
		}
	}
	
	/**
	 * 程序运行后，线程A和B都被唤醒了
	 * 如果想单独唤醒部分线程，可以使用多个condition对象，也就是condition对象可以唤醒部分
	 * 指定线程，有助于提升程序运行的效果。可以对线程进行分组，然后再唤醒指定组中的线程
	 * 
	 * begin awaitA时间为1492471719424 ThreadName=A
begin awaitB时间为1492471719425 ThreadName=B
  signalAll时间为1492471722424 ThreadName=main
  end awaitA时间为1492471722424 ThreadName=A
  end awaitB时间为1492471722424 ThreadName=B
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		MyService service = new MyService();

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();

		Thread.sleep(3000);

		service.signalAll();

	}

}
