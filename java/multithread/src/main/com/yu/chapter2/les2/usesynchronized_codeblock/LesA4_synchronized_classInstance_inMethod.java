package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesA4_synchronized_classInstance_inMethod {

	public static class Service {

		public void printA() {
			synchronized (Service.class) {
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

		}

		public void printB() {
			synchronized (Service.class) {
				System.out.println("线程名称为：" + Thread.currentThread().getName()
						+ "在" + System.currentTimeMillis() + "进入printB");
				System.out.println("线程名称为：" + Thread.currentThread().getName()
						+ "在" + System.currentTimeMillis() + "离开printB");
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
	 * 线程名称为：A在1492317403671进入printA
线程名称为：A在1492317406673离开printA
线程名称为：B在1492317406673进入printB
线程名称为：B在1492317406673离开printB
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
