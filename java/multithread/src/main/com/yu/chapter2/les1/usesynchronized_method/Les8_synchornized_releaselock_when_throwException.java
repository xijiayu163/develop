package com.yu.chapter2.les1.usesynchronized_method;

public class Les8_synchornized_releaselock_when_throwException {
	public static class Service {
		synchronized public void testMethod() {
			if (Thread.currentThread().getName().equals("a")) {
				System.out.println("ThreadName=" + Thread.currentThread().getName()
						+ " run beginTime=" + System.currentTimeMillis());
				int i = 1;
				while (i == 1) {
					if (("" + Math.random()).substring(0, 8).equals("0.123456")) {
						System.out.println("ThreadName="
								+ Thread.currentThread().getName()
								+ " run   exceptionTime="
								+ System.currentTimeMillis());
						Integer.parseInt("a");
					}
				}
			} else {
				System.out.println("Thread B run Time="
						+ System.currentTimeMillis());
			}
		}
	}
	
	public static class ThreadA extends Thread {

		private Service service;

		public ThreadA(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.testMethod();
		}

	}
	
	public static class ThreadB extends Thread {
		private Service service;

		public ThreadB(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.testMethod();
		}

	}
	
	/**
	 * 当a线程抛出异常时，自动释放锁
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Service service = new Service();

			ThreadA a = new ThreadA(service);
			a.setName("a");
			a.start();

			Thread.sleep(500);

			ThreadB b = new ThreadB(service);
			b.setName("b");
			b.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
