package com.yu.chapter3.les1.thread.communication;

public class Les7_notify_hold_lock_feature {

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

		public void synNotifyMethod(Object lock) {
			try {
				synchronized (lock) {
					System.out.println("begin notify() ThreadName="
							+ Thread.currentThread().getName() + " time="
							+ System.currentTimeMillis());
					lock.notify();
					Thread.sleep(5000);
					System.out.println("  end notify() ThreadName="
							+ Thread.currentThread().getName() + " time="
							+ System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
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
	
	public static class NotifyThread extends Thread {
		private Object lock;

		public NotifyThread(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			Service service = new Service();
			service.synNotifyMethod(lock);
		}

	}
	
	public static class synNotifyMethodThread extends Thread {
		private Object lock;

		public synNotifyMethodThread(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			Service service = new Service();
			service.synNotifyMethod(lock);
		}

	}
	
	/**
	 * 验证notify并不会立即释放锁
	 * 
	 * begin wait() ThreadName=Thread-0
begin notify() ThreadName=Thread-2 time=1492383406893
  end notify() ThreadName=Thread-2 time=1492383411894
  end wait() ThreadName=Thread-0
begin notify() ThreadName=Thread-1 time=1492383411894
  end notify() ThreadName=Thread-1 time=1492383416894
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		Object lock = new Object();

		ThreadA a = new ThreadA(lock);
		a.start();

		NotifyThread notifyThread = new NotifyThread(lock);
		notifyThread.start();

		synNotifyMethodThread c = new synNotifyMethodThread(lock);
		c.start();

	}

}
