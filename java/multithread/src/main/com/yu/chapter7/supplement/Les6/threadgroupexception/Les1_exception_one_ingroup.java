package com.yu.chapter7.supplement.Les6.threadgroupexception;

public class Les1_exception_one_ingroup {

	public static class MyThread extends Thread {

		private String num;

		public MyThread(ThreadGroup group, String name, String num) {
			super(group, name);
			this.num = num;
		}

		@Override
		public void run() {
			int numInt = Integer.parseInt(num);
			while (true) {
				System.out.println("死循环中�?" + Thread.currentThread().getName());
			}

		}

	}
	
	/**
	 * 线程组中其中�?个线程出了异常，对其他线程没有影�?  
	 * 
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadGroup group = new ThreadGroup("我的线程�?");
		MyThread[] myThread = new MyThread[10];
		for (int i = 0; i < myThread.length; i++) {
			myThread[i] = new MyThread(group, "线程" + (i + 1), "1");
			myThread[i].start();
		}
		MyThread newT = new MyThread(group, "报错线程", "a");
		newT.start();
	}

}
