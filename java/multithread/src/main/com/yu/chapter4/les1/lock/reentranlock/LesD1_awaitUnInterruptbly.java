package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesD1_awaitUnInterruptbly {

	public static class Service {

		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public void testMethod() {
			try {
				lock.lock();
				System.out.println("wait begin");
				condition.await();
				System.out.println("wait   end");
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("catch");
			} finally {
				lock.unlock();
			}

		}
	}
	
	public static class MyThread extends Thread {

		private Service service;

		public MyThread(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.testMethod();
		}

	}
	
	/**
	 * Å×³öÒì³£
	 * 
	 * wait begin
java.lang.InterruptedException
catch
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Service service = new Service();
			MyThread myThread = new MyThread(service);
			myThread.start();
			Thread.sleep(3000);
			myThread.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
