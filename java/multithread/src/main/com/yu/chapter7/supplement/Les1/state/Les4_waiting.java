package com.yu.chapter7.supplement.Les1.state;

public class Les4_waiting {

	// NEW,
	// RUNNABLE,
	// TERMINATED,
	// BLOCKED,
	// WAITING,
	// TIMED_WAITING,

	public static class Lock {
		public static final Byte lock = new Byte("0");
	}

	public static class MyThread extends Thread {

		@Override
		public void run() {
			try {
				synchronized (Lock.lock) {
					Lock.lock.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * WAITING:线程执行了Object.wait()方法后的状态
	 * 
	 * main方法中的t状态：WAITING
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread t = new MyThread();
			t.start();
			Thread.sleep(1000);
			System.out.println("main方法中的t状态：" + t.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
