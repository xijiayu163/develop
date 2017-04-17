package com.yu.chapter3.les2.thread.communication;

public class Les6_joinlong_release_lock {

	public static class ThreadA extends Thread {

		private ThreadB b;

		public ThreadA(ThreadB b) {
			super();
			this.b = b;
		}

		@Override
		public void run() {
			try {
				synchronized (b) {
					b.start();
					Thread.sleep(6000);
					// Thread.sleep()���ͷ�����
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadB extends Thread {

		@Override
		public void run() {
			try {
				System.out.println("   b run begin timer="
						+ System.currentTimeMillis());
				Thread.sleep(5000);
				System.out.println("   b run   end timer="
						+ System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public void bService() {
			System.out.println("��ӡ��bService timer=" + System.currentTimeMillis());
		}

	}
	
	/**
	 * �����߳�ThreadAʹ��Thread.sleep(long)����һֱ����Thread���������ʱ��ﵽ6�룬
	 * �����߳�ThreadCֻ����ThreadAʱ��ﵽ6S���ͷ�ThreadB����ʱ���ſ��Ե���ThreadB�е�ͬ
	 * ������synchronized public void bService()
	 * 
	 * 6s��Ŵ�ӡ  ��ӡ��bService timer=1492466032759
	 * Thread.sleep()���ͷ�����
	 * 
	 * @author xijia
	 *
	 */
	public static class ThreadC extends Thread {

		private ThreadB threadB;

		public ThreadC(ThreadB threadB) {
			super();
			this.threadB = threadB;
		}

		@Override
		public void run() {
			threadB.bService();
		}

	}
	
	public static void main(String[] args) {

		try {
			ThreadB b = new ThreadB();

			ThreadA a = new ThreadA(b);
			a.start();

			Thread.sleep(1000);

			ThreadC c = new ThreadC(b);
			c.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
