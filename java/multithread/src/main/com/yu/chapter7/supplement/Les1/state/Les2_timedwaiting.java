package com.yu.chapter7.supplement.Les1.state;

public class Les2_timedwaiting {

	// NEW,
	// RUNNABLE,
	// TERMINATED,

	// BLOCKED,
	// WAITING,
	// TIMED_WAITING,

	public static class MyThread extends Thread {

		@Override
		public void run() {
			try {
				System.out.println("begin sleep");
				Thread.sleep(10000);
				System.out.println("  end sleep");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	/**
	 * TIMED_WAITING:代表线程执行了Thread.sleep()方法，呈等待状态，等待时间到达 ，继续向下运行
	 * 
	 * begin sleep
main方法中的状态：TIMED_WAITING
  end sleep

	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread t = new MyThread();
			t.start();
			Thread.sleep(1000);
			System.out.println("main方法中的状态：" + t.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
