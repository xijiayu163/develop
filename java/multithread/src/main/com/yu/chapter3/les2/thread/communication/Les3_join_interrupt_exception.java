package com.yu.chapter3.les2.thread.communication;

public class Les3_join_interrupt_exception {

	public static class ThreadA extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				String newString = new String();
				Math.random();
			}
		}
	}

	public static class ThreadB extends Thread {

		@Override
		public void run() {
			try {
				ThreadA a = new ThreadA();
				a.start();
				a.join();

				System.out.println("�߳�B��run end����ӡ��");
			} catch (InterruptedException e) {
				System.out.println("�߳�B��catch����ӡ��");
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadC extends Thread {

		private ThreadB threadB;

		public ThreadC(ThreadB threadB) {
			super();
			this.threadB = threadB;
		}

		@Override
		public void run() {
			threadB.interrupt();
		}

	}
	
	/**
	 * ���н��:
	 * �߳�B��catch����ӡ��
	 * java.lang.InterruptedException
	 * 
	 * ��join�����У������ǰ�̶߳����жϣ���ǰ�̳߳����쳣
	 * ����join��interrupt�˴��������������쳣�������̰�ť����"��ɫ"��ԭ�����߳�ThreadA
	 * ���ڼ������У��߳�ThreadA��δ�����쳣��������ִ�е�״̬
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			ThreadB b = new ThreadB();
			b.start();

			Thread.sleep(500);

			ThreadC c = new ThreadC(b);
			c.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
