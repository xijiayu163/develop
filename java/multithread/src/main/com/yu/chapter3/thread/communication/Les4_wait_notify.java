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
					System.out.println("开始      wait time=" + System.currentTimeMillis());
					//阻塞并释放锁
					lock.wait();
					//得到notify后与其它线程通过竞争重新获得了锁
					System.out.println("结束      wait time=" + System.currentTimeMillis());
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
				System.out.println("开始notify time=" + System.currentTimeMillis());
				//发出notify通知，此时并未释放锁
				lock.notify();
				System.out.println("结束notify time=" + System.currentTimeMillis());
				//notify所在的锁代码块处理完毕后释放锁，其它处于Wait状态的线程开始通过竞争得到锁
			}
		}
	}
	
	/**
	 * 
	 * 开始      wait time=1492354172683  
开始notify time=1492354175683
结束notify time=1492354175683
结束      wait time=1492354175683

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
