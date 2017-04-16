package com.yu.chapter2.les2.usesynchronized_codeblock;

public class Les7_async_used_nosame_monitor_object {

	public static class Service {

		private String anyString = new String();

		public void a() {
			try {
				synchronized (anyString) {
					System.out.println("a begin");
					Thread.sleep(3000);
					System.out.println("a   end");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public void b() {
			System.out.println("b begin");
			System.out.println("b   end");
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
			service.a();

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
			service.b();

		}

	}

	/**
	 * 锁对象不一样，不会互相同步
	 * a begin
b begin
b   end
a   end
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
