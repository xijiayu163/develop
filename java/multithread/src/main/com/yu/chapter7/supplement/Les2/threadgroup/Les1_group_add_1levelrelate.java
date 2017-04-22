package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les1_group_add_1levelrelate {

	public static class ThreadA extends Thread {

		@Override
		public void run() {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					System.out
							.println("ThreadName=" + 
									Thread.currentThread().
									getName());
					Thread.sleep(3000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public static class ThreadB extends Thread {

		@Override
		public void run() {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					System.out
							.println("ThreadName=" + 
									Thread.currentThread().
									getName());
					Thread.sleep(3000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * ����߳���Ϊ��2
�߳��������Ϊ���ߺ��ҵ��߳���
ThreadName=Thread-2
ThreadName=Thread-3
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadA aRunnable = new ThreadA();
		ThreadB bRunnable = new ThreadB();

		ThreadGroup group = new ThreadGroup("�ߺ��ҵ��߳���");

		Thread aThread = new Thread(group, aRunnable);
		Thread bThread = new Thread(group, bRunnable);
		aThread.start();
		bThread.start();

		System.out.println("����߳���Ϊ��" + group.activeCount());
		System.out.println("�߳��������Ϊ��" + group.getName());

	}
}
