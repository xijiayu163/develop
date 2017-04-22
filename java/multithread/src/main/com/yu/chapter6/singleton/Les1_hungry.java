package com.yu.chapter6.singleton;

public class Les1_hungry {
	
	public static class MyObject {

		// 立即加载方式==饿汉模式
		private static MyObject myObject = new MyObject();

		private MyObject() {
		}

		public static MyObject getInstance() {
			// 此代码版本为立即加载
			// 此版本代码的缺点是不能有其它实例变量
			// 因为getInstance()方法没有同步
			// 所以有可能出现非线程安全问题
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
	 * 控制台打印的hashcode为同一个值，说明对象是同一个，也就实现了立即加载型单例设计模式
	 * 
	 * 672430613
672430613
672430613
	 * 
	 * 
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
