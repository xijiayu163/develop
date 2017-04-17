package com.yu.chapter4.lock.les1.reentranlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Les1_reentranlock {

	public static class MyService {

		private Lock lock = new ReentrantLock();

		public void testMethod() {
			lock.lock();
			for (int i = 0; i < 5; i++) {
				System.out.println("ThreadName=" + Thread.currentThread().getName()
						+ (" " + (i + 1)));
			}
			lock.unlock();
		}

	}
	
	/**
	 * ReentrantLock��ʹ��
	 * @author xijia
	 *
	 */
	public static class MyThread extends Thread {

		private MyService service;

		public MyThread(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.testMethod();
		}
	}
	
	/**
	 * �����ӡ
	 * 
	 * ����lock()���������������unlock()�����ͷ���
	 * @param args
	 */
	public static void main(String[] args) {

		MyService service = new MyService();

		MyThread a1 = new MyThread(service);
		MyThread a2 = new MyThread(service);
		MyThread a3 = new MyThread(service);
		MyThread a4 = new MyThread(service);
		MyThread a5 = new MyThread(service);

		a1.start();
		a2.start();
		a3.start();
		a4.start();
		a5.start();

	}

}
