package com.yu.chapter2.les2.usesynchronized_codeblock;


public class LesA1_static_method_classlock {

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

	}

	public static class ThreadA extends Thread {
		@Override
		public void run() {
			Service.printA();
		}

	}
	
	public static class ThreadB extends Thread {
		@Override
		public void run() {
			Service.printB();
		}
	}
	
	/**
	 * 对静态方法加synchronized,锁住的是class类，是类对象，而不是类实体对象
	 * 线程名称为：A在1492316674613进入printA
线程名称为：A在1492316677614离开printA
线程名称为：B在1492316677614进入printB
线程名称为：B在1492316677614离开printB
	 * @param args
	 */
	public static void main(String[] args) {

		ThreadA a = new ThreadA();
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB();
		b.setName("B");
		b.start();

	}

}
