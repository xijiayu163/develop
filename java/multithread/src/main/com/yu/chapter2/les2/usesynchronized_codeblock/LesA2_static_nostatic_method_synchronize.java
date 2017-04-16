package com.yu.chapter2.les2.usesynchronized_codeblock;


public class LesA2_static_nostatic_method_synchronize {

	public static class Service {

		synchronized public static void printA() {
			try {
				System.out.println("线程名称为：" + Thread.currentThread().getName()
						+ "在" + System.currentTimeMillis() + "进入printA");
				Thread.sleep(3000);
				System.out.println("线程名称为：" + Thread.currentThread().getName()
						+ "在" + System.currentTimeMillis() + "离开printA");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public static void printB() {
			System.out.println("线程名称为：" + Thread.currentThread().getName() + "在"
					+ System.currentTimeMillis() + "进入printB");
			System.out.println("线程名称为：" + Thread.currentThread().getName() + "在"
					+ System.currentTimeMillis() + "离开printB");
		}

		synchronized public void printC() {
			System.out.println("线程名称为：" + Thread.currentThread().getName() + "在"
					+ System.currentTimeMillis() + "进入printC");
			System.out.println("线程名称为：" + Thread.currentThread().getName() + "在"
					+ System.currentTimeMillis() + "离开printC");
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
			service.printA();
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
			service.printB();
		}
	}
	
	public static class ThreadC extends Thread {

		private Service service;

		public ThreadC(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.printC();
		}
	}
	
	/**
	 * AB是顺序执行，C和AB不为顺序执行，因为锁的对象不一样
	 * 线程名称为：A在1492316881634进入printA
线程名称为：C在1492316881636进入printC
线程名称为：C在1492316881636离开printC
线程名称为：A在1492316884635离开printA
线程名称为：B在1492316884635进入printB
线程名称为：B在1492316884635离开printB
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

		ThreadC c = new ThreadC(service);
		c.setName("C");
		c.start();

	}

}
