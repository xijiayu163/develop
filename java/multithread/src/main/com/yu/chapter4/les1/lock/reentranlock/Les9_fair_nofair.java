package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.ReentrantLock;

public class Les9_fair_nofair {

	public static class Service {

		private ReentrantLock lock;

		public Service(boolean isFair) {
			super();
			lock = new ReentrantLock(isFair);
		}

		public void serviceMethod() {
			try {
				lock.lock();
				System.out.println("ThreadName=" + Thread.currentThread().getName()
						+ "�������");
			} finally {
				lock.unlock();
			}
		}

	}
	
//	/**
//	 * ��ƽ������������״̬
//	 * 
//	 * ���߳�Thread-0������
//ThreadName=Thread-0�������
//���߳�Thread-1������
//ThreadName=Thread-1�������
//���߳�Thread-2������
//ThreadName=Thread-2�������
//���߳�Thread-3������
//ThreadName=Thread-3�������
//���߳�Thread-4������
//ThreadName=Thread-4�������
//���߳�Thread-5������
//ThreadName=Thread-5�������
//���߳�Thread-6������
//ThreadName=Thread-6�������
//���߳�Thread-7������
//ThreadName=Thread-7�������
//���߳�Thread-8������
//ThreadName=Thread-8�������
//���߳�Thread-9������
//ThreadName=Thread-9�������
//	 * @param args
//	 * @throws InterruptedException
//	 */
//	public static void main(String[] args) throws InterruptedException {
//		final Service service = new Service(true);
//
//		Runnable runnable = new Runnable() {
//			public void run() {
//				System.out.println("���߳�" + Thread.currentThread().getName()
//						+ "������");
//				service.serviceMethod();
//			}
//		};
//
//		Thread[] threadArray = new Thread[10];
//		for (int i = 0; i < 10; i++) {
//			threadArray[i] = new Thread(runnable);
//		}
//		for (int i = 0; i < 10; i++) {
//			threadArray[i].start();
//		}
//
//	}
	
	/**
	 * �ǹ�ƽ��������״̬,˵����start()�������̲߳������Ȼ����
	 * 
	 * ���߳�Thread-6������
���߳�Thread-3������
ThreadName=Thread-2�������
ThreadName=Thread-7�������
ThreadName=Thread-1�������
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final Service service = new Service(false);

		Runnable runnable = new Runnable() {
			public void run() {
				System.out.println("���߳�" + Thread.currentThread().getName()
						+ "������");
				service.serviceMethod();
			}
		};

		Thread[] threadArray = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threadArray[i] = new Thread(runnable);
		}
		for (int i = 0; i < 10; i++) {
			threadArray[i].start();
		}

	}
}
