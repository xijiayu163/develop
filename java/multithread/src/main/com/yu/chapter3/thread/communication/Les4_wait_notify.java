package com.yu.chapter3.thread.communication;

public class Les4_wait_notify {
	
	public static class MyThread1 extends Thread {
		private Object lock;

		public MyThread1(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			try {
				synchronized (lock) {
					System.out.println("��ʼ      wait time=" + System.currentTimeMillis());
					//�������ͷ���
					lock.wait();
					//�õ�notify���������߳�ͨ���������»������
					System.out.println("����      wait time=" + System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class MyThread2 extends Thread {
		private Object lock;

		public MyThread2(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			synchronized (lock) {
				System.out.println("��ʼnotify time=" + System.currentTimeMillis());
				//����notify֪ͨ����ʱ��δ�ͷ���
				lock.notify();
				System.out.println("����notify time=" + System.currentTimeMillis());
				//notify���ڵ�������鴦����Ϻ��ͷ�������������Wait״̬���߳̿�ʼͨ�������õ���
			}
		}
	}
	
	/**
	 * 
	 * ��ʼ      wait time=1492354172683  
��ʼnotify time=1492354175683
����notify time=1492354175683
����      wait time=1492354175683

	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Object lock = new Object();

			MyThread1 t1 = new MyThread1(lock);
			t1.start();

			Thread.sleep(3000);

			MyThread2 t2 = new MyThread2(lock);
			t2.start();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
