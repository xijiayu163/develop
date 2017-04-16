package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesB2_string_sync_inpool {

	public static class Service {
		public static void print(String stringParam) {
			try {
				synchronized (stringParam) {
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
			service.print("AA");
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
			service.print("AA");
		}
	}
	
	/**
	 * string的两个值都是AA，两个线程持有相同的锁，所以造成线程B不能执行。
	 * 这就是String常量池所带来的问题。因此在大多数的情况下，同步synchronized代码块
	 * 都不使用String作为锁对象，而改用其他
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
