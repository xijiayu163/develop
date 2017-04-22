package com.yu.chapter6.singleton;

public class Les2_5_lazy_error {

	public static class MyObject {

		private static MyObject myObject;

		private MyObject() {
		}

		public static MyObject getInstance() {
			try {
				if (myObject != null) {
				} else {
					// 模拟在创建对象之前做一些准备性的工作
					Thread.sleep(3000);
					// 使用synchronized (MyObject.class)
					// 虽然部分代码被上锁
					// 但还是有非线程安全问题
					synchronized (MyObject.class) {
						myObject = new MyObject();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return myObject;
		}

	}
	
	public static class MyThread extends Thread {

		@Override
		public void run() {
			System.out.println(MyObject.getInstance().hashCode());
		}

	}
	
	/**
	 * 672430613
1091862201
1980094282

	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		MyThread t3 = new MyThread();

		t1.start();
		t2.start();
		t3.start();

	}

}
