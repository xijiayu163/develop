package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Les2_reentranlock_order {

	public static class MyService {

		private Lock lock = new ReentrantLock();

		public void methodA() {
			try {
				lock.lock();
				System.out.println("methodA begin ThreadName="
						+ Thread.currentThread().getName() + " time="
						+ System.currentTimeMillis());
				Thread.sleep(5000);
				System.out.println("methodA  end ThreadName="
						+ Thread.currentThread().getName() + " time="
						+ System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void methodB() {
			try {
				lock.lock();
				System.out.println("methodB begin ThreadName="
						+ Thread.currentThread().getName() + " time="
						+ System.currentTimeMillis());
				Thread.sleep(5000);
				System.out.println("methodB  end ThreadName="
						+ Thread.currentThread().getName() + " time="
						+ System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
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
			service.methodA();
		}
	}
	
	public static class ThreadAA extends Thread {

		private MyService service;

		public ThreadAA(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.methodA();
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
			service.methodB();
		}
	}
	
	public static class ThreadBB extends Thread {

		private MyService service;

		public ThreadBB(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.methodB();
		}
	}
	
	/**
	 * 多个线程顺序执行
	 * 
	 * methodA begin ThreadName=A time=1492470607712
methodA  end ThreadName=A time=1492470612713
methodA begin ThreadName=AA time=1492470612713
methodA  end ThreadName=AA time=1492470617714
methodB begin ThreadName=B time=1492470617714
methodB  end ThreadName=B time=1492470622714
methodB begin ThreadName=BB time=1492470622714
methodB  end ThreadName=BB time=1492470627715
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		MyService service = new MyService();

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();
		ThreadAA aa = new ThreadAA(service);
		aa.setName("AA");
		aa.start();

		Thread.sleep(100);

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();
		
		ThreadBB bb = new ThreadBB(service);
		bb.setName("BB");
		bb.start();

	}

}
