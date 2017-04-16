package com.yu.chapter2.les1.usesynchronized_method;

public class Les4_synchronizedMethodLockObject {

//	/**
//	 *并发执行
//	 * begin methodA threadName=A
//		begin methodA threadName=B
//		end
//		end
//	 */
//	public void methodA() {
//		try {
//			System.out.println("begin methodA threadName=" + Thread.currentThread().getName());
//			Thread.sleep(5000);
//			System.out.println("end");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 排队执行
	 * begin methodA threadName=A
end
begin methodA threadName=B
end
	 */
	synchronized public void methodA() {
		try {
			System.out.println("begin methodA threadName=" + Thread.currentThread().getName());
			Thread.sleep(5000);
			System.out.println("end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static class ThreadA extends Thread {

		private Les4_synchronizedMethodLockObject object;

		public ThreadA(Les4_synchronizedMethodLockObject object) {
			super();
			this.object = object;
		}

		@Override
		public void run() {
			super.run();
			object.methodA();
		}

	}

	public static class ThreadB extends Thread {

		private Les4_synchronizedMethodLockObject object;

		public ThreadB(Les4_synchronizedMethodLockObject object) {
			super();
			this.object = object;
		}

		@Override
		public void run() {
			super.run();
			object.methodA();
		}
	}

	public static void main(String[] args) {
		Les4_synchronizedMethodLockObject object = new Les4_synchronizedMethodLockObject();
		ThreadA a = new ThreadA(object);
		a.setName("A");
		ThreadB b = new ThreadB(object);
		b.setName("B");

		a.start();
		b.start();
	}
}
