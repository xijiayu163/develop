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
				System.out.println(Thread.currentThread().getName() + "������ҵ�񷽷���");
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
	 * BLOCKED:ĳ���߳����ڵȴ�����״̬
	 * 
	 * 
	 * a������ҵ�񷽷���
main�����е�t2״̬��BLOCKED
b������ҵ�񷽷���
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
		System.out.println("main�����е�t2״̬��" + t2.getState());

	}
}
