package com.yu.chapter3.les1.thread.communication;

public class Les8_wait_interrupt_exception {

	public static class Service {

		public void testMethod(Object lock) {
			try {
				synchronized (lock) {
					System.out.println("begin wait()");
					lock.wait();
					System.out.println("  end wait()");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("出现异常了，因为呈wait状态的线程被interrupt了！");
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
	
	/**
	 * 当线程呈wait()状态时，调用线程对象的interrupt()方法会出现InterruptException
	 * begin wait()
java.lang.InterruptedException
出现异常了，因为呈wait状态的线程被interrupt了！
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Object lock = new Object();

			ThreadA a = new ThreadA(lock);
			a.start();

			Thread.sleep(5000);

			a.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
