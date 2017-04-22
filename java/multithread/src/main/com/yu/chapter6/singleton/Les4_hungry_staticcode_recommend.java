package com.yu.chapter6.singleton;

public class Les4_hungry_staticcode_recommend {

	public static class MyObject {

		private static MyObject instance = null;

		private MyObject() {
		}

		static {
			instance = new MyObject();
		}

		public static MyObject getInstance() {
			return instance;
		}

	}
	
	public static class MyThread extends Thread {

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println(MyObject.getInstance().hashCode());
			}
		}
	}
	
	/**
	 * 
	 * 静态代码块中的代码在使用类的时候就已经执行了，所以可以应用静态代码块的这个特性来实现单例模式
	 * 
	 * 1980094282
1980094282
1980094282
1980094282
1980094282
1980094282
1980094282
1980094282
1980094282
1980094282
1980094282
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
