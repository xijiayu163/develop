package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Les8_many_product_many_consumer_noInterval {

	public static class MyService {

		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();
		private boolean hasValue = false;

		public void set() {
			try {
				lock.lock();
				while (hasValue == true) {
					System.out.println("�п��ܡ������");
					condition.await();
				}
				System.out.println("��ӡ��");
				hasValue = true;
				condition.signal();
//				condition.signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void get() {
			try {
				lock.lock();
				while (hasValue == false) {
					System.out.println("�п��ܡ������");
					condition.await();
				}
				System.out.println("��ӡ��");
				hasValue = false;
				condition.signal();
//				condition.signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

	}
	
	public static class MyThreadA extends Thread {

		private MyService myService;

		public MyThreadA(MyService myService) {
			super();
			this.myService = myService;
		}

		@Override
		public void run() {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				myService.set();
			}
		}

	}
	
	public static class MyThreadB extends Thread {

		private MyService myService;

		public MyThreadB(MyService myService) {
			super();
			this.myService = myService;
		}

		@Override
		public void run() {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				myService.get();
			}
		}

	}
	
	/**
	 * �п��ܳ���������ӡ���������
	 * ������������sign��signAll����
	 * 
	 * ���н�� 
	 * �п��ܡ������
�п��ܡ������
�п��ܡ������
��ӡ��
�п��ܡ������
�п��ܡ������
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		MyService service = new MyService();

		MyThreadA[] threadA = new MyThreadA[10];
		MyThreadB[] threadB = new MyThreadB[10];

		for (int i = 0; i < 10; i++) {
			threadA[i] = new MyThreadA(service);
			threadB[i] = new MyThreadB(service);
			threadA[i].start();
			threadB[i].start();
		}

	}
}
