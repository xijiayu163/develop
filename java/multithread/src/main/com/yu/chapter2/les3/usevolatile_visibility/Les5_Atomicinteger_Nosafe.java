package com.yu.chapter2.les3.usevolatile_visibility;

import java.util.concurrent.atomic.AtomicLong;

public class Les5_Atomicinteger_Nosafe {
	
	public static class MyService {

		public static AtomicLong aiRef = new AtomicLong();

	    public void addNum() {
			System.out.println(Thread.currentThread().getName() + "加了100之后的值是:"
					+ aiRef.addAndGet(100));
			aiRef.addAndGet(1);
		}

	}
	
	public static class MyThread extends Thread {
		private MyService mySerivce;

		public MyThread(MyService mySerivce) {
			super();
			this.mySerivce = mySerivce;
		}

		@Override
		public void run() {
			mySerivce.addNum();
		}

	}
	
	/**
	 * 打印顺序错了，应该每加1次100再加1次1。出现这样的情况是因为addAndGet()方法是原子的，但方法
	 * 和方法之间的调用却不是原子的。解决这样的问题必须要用同步
	 * 
	 * Thread-1加了100之后的值是:100
Thread-0加了100之后的值是:200
Thread-2加了100之后的值是:300
Thread-3加了100之后的值是:403
Thread-4加了100之后的值是:504
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyService service = new MyService();

			MyThread[] array = new MyThread[5];
			for (int i = 0; i < array.length; i++) {
				array[i] = new MyThread(service);
			}
			for (int i = 0; i < array.length; i++) {
				array[i].start();
			}
			Thread.sleep(1000);
			System.out.println(service.aiRef.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
