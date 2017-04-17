package com.yu.chapter3.les1.thread.communication;

public class Les6_wait_auto_release_lock_feature {

	public static class Service {

		public void testMethod(Object lock) {
			try {
				synchronized (lock) {
					System.out.println("begin wait()");
					lock.wait();
					//wait改成sleep就成了同步的效果，只打印一个
					//Thread.sleep(40000);
					System.out.println("  end wait()");
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
	
	/**
	 * 
	 * wait自动释放锁
	 * wait改成sleep就成了同步的效果，只打印一个
	 * begin wait()
begin wait()
	 * @param args
	 */
	public static void main(String[] args) {

		Object lock = new Object();

		ThreadA a = new ThreadA(lock);
		a.start();

		ThreadB b = new ThreadB(lock);
		b.start();

	}

}
