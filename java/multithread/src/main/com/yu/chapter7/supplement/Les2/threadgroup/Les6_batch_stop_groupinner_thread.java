package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les6_batch_stop_groupinner_thread {

	public static class MyThread extends Thread {

		public MyThread(ThreadGroup group, String name) {
			super(group, name);
		}

		@Override
		public void run() {
			System.out.println("ThreadName=" + Thread.currentThread().getName()
					+ "׼����ʼ��ѭ���ˣ�)");
			while (!this.isInterrupted()) {
			}
			System.out.println("ThreadName=" + Thread.currentThread().getName()
					+ "�����ˣ�)");
		}

	}
	
	/**
	 * ����ֹͣ�����߳�
	 * 
	 * ThreadName=�߳�1׼����ʼ��ѭ���ˣ�)
ThreadName=�߳�2׼����ʼ��ѭ���ˣ�)
ThreadName=�߳�3׼����ʼ��ѭ���ˣ�)
ThreadName=�߳�4׼����ʼ��ѭ���ˣ�)
ThreadName=�߳�5׼����ʼ��ѭ���ˣ�)
������interrupt()����
ThreadName=�߳�4�����ˣ�)
ThreadName=�߳�3�����ˣ�)
ThreadName=�߳�1�����ˣ�)
ThreadName=�߳�5�����ˣ�)
ThreadName=�߳�2�����ˣ�)

	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ThreadGroup group = new ThreadGroup("�ҵ��߳���");

			for (int i = 0; i < 5; i++) {
				MyThread thread = new MyThread(group, "�߳�" + (i + 1));
				thread.start();
			}
			Thread.sleep(5000);
			group.interrupt();
			System.out.println("������interrupt()����");
		} catch (InterruptedException e) {
			System.out.println("ͣ��ͣ�ˣ�");
			e.printStackTrace();
		}

	}

}
