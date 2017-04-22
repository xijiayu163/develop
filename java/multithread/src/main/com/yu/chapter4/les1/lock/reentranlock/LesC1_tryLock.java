package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.ReentrantLock;

public class LesC1_tryLock {

	public static class MyService {

		public ReentrantLock lock = new ReentrantLock();

		public void waitMethod() {
			if (lock.tryLock()) {
				System.out.println(Thread.currentThread().getName() + "�����");
			} else {
				System.out.println(Thread.currentThread().getName() + "û�л����");
			}
		}
	}
	
	/**
	 * tryLock()���ڵ���ʱ����δ����һ�̱߳��ֵ�����£��Ż�ȡ������
	 * 
	 * A�����
Bû�л����
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final MyService service = new MyService();

		Runnable runnableRef = new Runnable() {
			public void run() {
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
