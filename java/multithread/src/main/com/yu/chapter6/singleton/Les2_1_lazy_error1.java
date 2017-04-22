package com.yu.chapter6.singleton;

public class Les2_1_lazy_error1 {

	public static class MyObject {

		private static MyObject myObject;

		private MyObject() {
		}

		public static MyObject getInstance() {
			// 延迟加载
			if (myObject != null) {
			} else {
				myObject = new MyObject();
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
	 * 懒汉单例模式
	 * 此实例虽然取得一个对象的实例，但如果是在多线程的环境中，就会出现取出多个实例的情况，与
	 * 单例模式的初衷是相背离的
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		t1.start();
	}

}
