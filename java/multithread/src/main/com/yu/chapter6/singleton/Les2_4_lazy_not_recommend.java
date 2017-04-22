package com.yu.chapter6.singleton;

public class Les2_4_lazy_not_recommend {

	public static class MyObject {

		private static MyObject myObject;

		private MyObject() {
		}

		public static MyObject getInstance() {
			try {
				// 此种写法等同于：
				// synchronized public static MyObject getInstance()
				// 的写法，效率一样很低，全部代码被上锁
				synchronized (MyObject.class) {
					if (myObject != null) {
					} else {
						// 模拟在创建对象之前做一些准备性的工作
						Thread.sleep(3000);

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
	 * 		// 此版本代码虽然是正确的
		// 但public static MyObject getInstance()方法
		// 中的全部代码都是同步的了，这样做有损效率
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
