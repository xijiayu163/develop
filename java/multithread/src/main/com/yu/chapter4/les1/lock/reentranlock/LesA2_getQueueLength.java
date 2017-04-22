package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.ReentrantLock;

public class LesA2_getQueueLength {

	public static class Service {

		public ReentrantLock lock = new ReentrantLock();

		public void serviceMethod1() {
			try {
				lock.lock();
				System.out.println("ThreadName=" + Thread.currentThread().getName()
						+ "���뷽����");
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
	 *getQueueLength()�������Ƿ������ȴ���ȡ���������߳�����������5���̣߳�
	 *1 ���߳�����ִ��await��������ô�ڵ���getQueueLength�����󷵻�ֵ��4��˵��
	 *��4���߳�ͬʱ�ڵȴ�lock���ͷ� 
	 * 
	 * ThreadName=Thread-0���뷽����
���߳�����9�ڵȴ���ȡ����
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
		System.out.println("���߳�����" + service.lock.getQueueLength() + "�ڵȴ���ȡ����");

	}
}
