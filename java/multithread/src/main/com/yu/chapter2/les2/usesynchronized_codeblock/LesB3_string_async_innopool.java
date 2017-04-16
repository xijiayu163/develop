package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesB3_string_async_innopool {
	
	public static class Service {
		public static void print(Object object) {
			try {
				synchronized (object) {
					while (true) {
						System.out.println(Thread.currentThread().getName());
						Thread.sleep(1000);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
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
			service.print(new Object());
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
			service.print(new Object());
		}
	}
	
	/**
	 * new Object()实例化一个Object对象，但它并不放入缓存中
	 * @param args
	 */
	public static void main(String[] args) {

		Service service = new Service();

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();

	}

}
