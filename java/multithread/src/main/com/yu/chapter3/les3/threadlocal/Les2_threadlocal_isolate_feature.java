package com.yu.chapter3.les3.threadlocal;

public class Les2_threadlocal_isolate_feature {

	public static class Tools {
		public static ThreadLocal tl = new ThreadLocal();
	}
	
	public static class ThreadA extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 100; i++) {
					if (Tools.tl.get() == null) {
						Tools.tl.set("ThreadA" + (i + 1));
					} else {
						System.out.println("ThreadA get Value=" + Tools.tl.get());
					}
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class ThreadB extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 100; i++) {
					if (Tools.tl.get() == null) {
						Tools.tl.set("ThreadB" + (i + 1));
					} else {
						System.out.println("ThreadB get Value=" + Tools.tl.get());
					}
					Thread.sleep(200);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * threadlocal 线程局部变量隔离性
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			ThreadA a = new ThreadA();
			ThreadB b = new ThreadB();
			a.start();
			b.start();

			for (int i = 0; i < 100; i++) {
				if (Tools.tl.get() == null) {
					Tools.tl.set("Main" + (i + 1));
				} else {
					System.out.println("Main get Value=" + Tools.tl.get());
				}
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
