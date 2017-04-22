package com.yu.chapter7.supplement.Les1.state;

public class Les1_new_runnable_terminated {

	// NEW,
	// RUNNABLE,
	// TERMINATED,

	// BLOCKED,
	// WAITING,
	// TIMED_WAITING,

	public static class MyThread extends Thread {

		public MyThread() {
			System.out.println("构造方法中的状态：" + Thread.currentThread().getState());
		}

		@Override
		public void run() {
			System.out.println("run方法中的状态：" + Thread.currentThread().getState());
		}

	}
	
	/**
	 * NEW:是线程实例化后还从未执行start()方法时的状态
	 * RUNNABLE:是线程进入运行的状态
	 * TERMINATED:是线程被销毁时的状态
	 * 
	 * 构造方法中的状态：RUNNABLE  其实是main主线程的状态
main方法中的状态1：NEW
run方法中的状态：RUNNABLE
main方法中的状态2：TERMINATED

	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread t = new MyThread();
			System.out.println("main方法中的状态1：" + t.getState());
			Thread.sleep(1000);
			t.start();
			Thread.sleep(1000);
			System.out.println("main方法中的状态2：" + t.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
