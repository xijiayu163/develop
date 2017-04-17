package com.yu.chapter3.les2.thread.communication;

public class Les5_sleeplong_no_release_lock {

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
					b.join();// 说明join释放锁了！
					for (int i = 0; i < Integer.MAX_VALUE; i++) {
						String newString = new String();
						Math.random();
					}
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
			System.out.println("打印了bService timer=" + System.currentTimeMillis());
		}

	}
	
	/**
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
	
	/**
	 * 打印结果:
	 *    b run begin timer=1492466660514
打印了bService timer=1492466661514
   b run   end timer=1492466665514
   
   说明join时锁被释放了
	 * 
	 * @param args
	 */
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
