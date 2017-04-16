package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesA3_static_method_classLock_toAllInstance {

	public static class Service {

		synchronized public static void printA() {
			try {
				System.out.println(
						"线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入printA");
				Thread.sleep(3000);
				System.out.println(
						"线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开printA");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public static void printB() {
			System.out.println(
					"线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入printB");
			System.out.println(
					"线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开printB");
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
	
	/**
	 * class锁可以对类的所有实例对象起作用
	 * 线程名称为：A在1492317185758进入printA
线程名称为：A在1492317188760离开printA
线程名称为：B在1492317188760进入printB
线程名称为：B在1492317188760离开printB

	 * @param args
	 */
	public static void main(String[] args) {

		Service service1 = new Service();
		Service service2 = new Service();

		ThreadA a = new ThreadA(service1);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service2);
		b.setName("B");
		b.start();

	}

}
