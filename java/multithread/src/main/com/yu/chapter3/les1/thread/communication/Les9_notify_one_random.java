package com.yu.chapter3.les1.thread.communication;

public class Les9_notify_one_random {

	public static class Service {

		public void testMethod(Object lock) {
			try {
				synchronized (lock) {
					System.out.println("begin wait() ThreadName="
							+ Thread.currentThread().getName());
					lock.wait();
					System.out.println("  end wait() ThreadName="
							+ Thread.currentThread().getName());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class NotifyThread extends Thread {
		private Object lock;

		public NotifyThread(Object lock) {
			super();
			this.lock = lock;
		}

		/**
		 * 方法notify仅随机唤醒一个线程
		 * begin wait() ThreadName=Thread-0
begin wait() ThreadName=Thread-1
begin wait() ThreadName=Thread-2
  end wait() ThreadName=Thread-0

		 * 
		 * 多次调用notify，会随机将wait状态的线程唤醒。 
		 * begin wait() ThreadName=Thread-1
begin wait() ThreadName=Thread-0
begin wait() ThreadName=Thread-2
  end wait() ThreadName=Thread-1
  end wait() ThreadName=Thread-2
  end wait() ThreadName=Thread-0
		 */
		@Override
		public void run() {
			synchronized (lock) {
				lock.notify();
//				lock.notify();
//				lock.notify();
//				lock.notify();
//				lock.notify();
//				lock.notify();
//				lock.notify();
//				lock.notify();
//				lock.notify();
			}
		}

	}
	
	public static class ThreadA extends Thread {
		private Object lock;

		public ThreadA(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			Service service = new Service();
			service.testMethod(lock);
		}

	}
	
	public static class ThreadB extends Thread {
		private Object lock;

		public ThreadB(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			Service service = new Service();
			service.testMethod(lock);
		}

	}
	
	public static class ThreadC extends Thread {
		private Object lock;

		public ThreadC(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			Service service = new Service();
			service.testMethod(lock);
		}

	}
	
	public static void main(String[] args) throws InterruptedException {

		Object lock = new Object();

		ThreadA a = new ThreadA(lock);
		a.start();

		ThreadB b = new ThreadB(lock);
		b.start();

		ThreadC c = new ThreadC(lock);
		c.start();

		Thread.sleep(1000);

		NotifyThread notifyThread = new NotifyThread(lock);
		notifyThread.start();

	}

}
