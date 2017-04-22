package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesA3_getWaitQueueLength {

	public static class Service {

		private ReentrantLock lock = new ReentrantLock();
		private Condition newCondition = lock.newCondition();

		public void waitMethod() {
			try {
				lock.lock();
				newCondition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void notityMethod() {
			try {
				lock.lock();
				System.out.println("��" + lock.getWaitQueueLength(newCondition)
						+ "���߳����ڵȴ�newCondition");
				newCondition.signal();
				System.out.println("signal����" + lock.getWaitQueueLength(newCondition)
				+ "���߳����ڵȴ�newCondition");
			} finally {
				lock.unlock();
			}
		}

	}
	
	/**
	 * getWaitQueueLength(Condition condition)���صȴ����������ص�
	 * ��������Condition���̹߳�������
	 * 
	 * ���н��:
	 * ��10���߳����ڵȴ�newCondition
signal����9���߳����ڵȴ�newCondition
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

		Thread[] threadArray = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threadArray[i] = new Thread(runnable);
		}
		for (int i = 0; i < 10; i++) {
			threadArray[i].start();
		}
		Thread.sleep(2000);
		service.notityMethod();
	}
}
