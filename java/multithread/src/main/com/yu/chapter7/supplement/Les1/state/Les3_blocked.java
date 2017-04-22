package com.yu.chapter7.supplement.Les1.state;


public class Les3_blocked {

	// NEW,
	// RUNNABLE,
	// TERMINATED,
	// BLOCKED,
	// WAITING,
	// TIMED_WAITING,

	public static class MyService {

		synchronized static public void serviceMethod() {
			try {
				System.out.println(Thread.currentThread().getName() + "进入了业务方法！");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class MyThread1 extends Thread {

		@Override
		public void run() {
			MyService.serviceMethod();
		}

	}
	
	public static class MyThread2 extends Thread {

		@Override
		public void run() {
			MyService.serviceMethod();
		}

	}
	
	/**
	 * BLOCKED:某个线程正在等待锁的状态
	 * 
	 * 
	 * a进入了业务方法！
main方法中的t2状态：BLOCKED
b进入了业务方法！
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread1 t1 = new MyThread1();
		t1.setName("a");
		t1.start();
		MyThread2 t2 = new MyThread2();
		t2.setName("b");
		t2.start();
		Thread.yield();
		System.out.println("main方法中的t2状态：" + t2.getState());

	}
}
