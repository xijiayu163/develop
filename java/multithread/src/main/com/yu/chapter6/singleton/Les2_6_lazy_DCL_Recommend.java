package com.yu.chapter6.singleton;

public class Les2_6_lazy_DCL_Recommend {

	public static class MyObject {

		private volatile static MyObject myObject;

		private MyObject() {
		}

		// 使用双检测机制来解决问题
		// 即保证了不需要同步代码的异步
		// 又保证了单例的效果
		public static MyObject getInstance() {
			try {
				if (myObject != null) {
				} else {
					// 模拟在创建对象之前做一些准备性的工作
					Thread.sleep(3000);
					synchronized (MyObject.class) {
						if (myObject == null) {
							myObject = new MyObject();
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return myObject;
		}
		// 此版本的代码称为：
		// 双重检查Double-Check Locking

	}
	
	public static class MyThread extends Thread {

		@Override
		public void run() {
			System.out.println(MyObject.getInstance().hashCode());
		}

	}
	
	/**
	 * 双检锁机制,即保证了不需要同步代码的异步,又保证了单例的效果
	 * 
	 * 1980094282
1980094282
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
