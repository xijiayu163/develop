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
	 * WAITING:�߳�ִ����Object.wait()�������״̬
	 * 
	 * main�����е�t״̬��WAITING
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread t = new MyThread();
			t.start();
			Thread.sleep(1000);
			System.out.println("main�����е�t״̬��" + t.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
