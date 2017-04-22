package com.yu.chapter6.singleton;

public class Les3_1_lazy_staticInnerClass {

	public static class MyObject {

		// 内部类方式
		private static class MyObjectHandler {
			private static MyObject myObject = new MyObject();
		}

		private MyObject() {
		}

		public static MyObject getInstance() {
			return MyObjectHandler.myObject;
		}

	}
	
	public static class MyThread extends Thread {

		@Override
		public void run() {
			System.out.println(MyObject.getInstance().hashCode());
		}

	}
	
	/**
	 * 静态内部类方式实现多线程单例模式
	 * 缺点:如果遇到序列化对象时，使用默认的方式运行得到的结果还是多例的
	 * 
	 * 856722497
856722497
856722497
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
