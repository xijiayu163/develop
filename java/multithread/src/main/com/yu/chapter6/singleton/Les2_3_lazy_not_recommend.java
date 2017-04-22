package com.yu.chapter6.singleton;

public class Les2_3_lazy_not_recommend {

	public static class MyObject {

		private static MyObject myObject;

		private MyObject() {
		}

		// 设置同步方法效率太低了
		// 整个方法被上锁
		synchronized public static MyObject getInstance() {
			try {
				if (myObject != null) {
				} else {
					// 模拟在创建对象之前做一些准备性的工作
					Thread.sleep(3000);
					myObject = new MyObject();
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
	 * 正确的单例模式，不过效率有点低，因为整个方法被加锁了
	 * 672430613
672430613
672430613
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
