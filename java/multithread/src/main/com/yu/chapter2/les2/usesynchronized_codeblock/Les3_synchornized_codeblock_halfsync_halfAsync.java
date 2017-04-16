package com.yu.chapter2.les2.usesynchronized_codeblock;

public class Les3_synchornized_codeblock_halfsync_halfAsync {

	public static class Task {
		public void doLongTimeTask() {
			for (int i = 0; i < 100; i++) {
				System.out.println("nosynchronized threadName="
						+ Thread.currentThread().getName() + " i=" + (i + 1));
			}
			System.out.println("");
			synchronized (this) {
				for (int i = 0; i < 100; i++) {
					System.out.println("synchronized threadName="
							+ Thread.currentThread().getName() + " i=" + (i + 1));
				}
			}

		}
	}
	
	public static class MyThread1 extends Thread {

		private Task task;

		public MyThread1(Task task) {
			super();
			this.task = task;
		}

		@Override
		public void run() {
			super.run();
			task.doLongTimeTask();
		}

	}
	
	public static class MyThread2 extends Thread {

		private Task task;

		public MyThread2(Task task) {
			super();
			this.task = task;
		}

		@Override
		public void run() {
			super.run();
			task.doLongTimeTask();
		}

	}
	
	/**
	 * 进入非同步的是交替执行,同步的是顺序执行，一半同步一半异步
	 * nosynchronized threadName=Thread-0 i=1
nosynchronized threadName=Thread-0 i=2
nosynchronized threadName=Thread-1 i=1
nosynchronized threadName=Thread-0 i=3
nosynchronized threadName=Thread-1 i=2
nosynchronized threadName=Thread-1 i=3

synchronized threadName=Thread-1 i=3
synchronized threadName=Thread-1 i=4
synchronized threadName=Thread-1 i=5
synchronized threadName=Thread-1 i=6
synchronized threadName=Thread-1 i=7
synchronized threadName=Thread-1 i=8
	 * @param args
	 */
	public static void main(String[] args) {
		Task task = new Task();

		MyThread1 thread1 = new MyThread1(task);
		thread1.start();

		MyThread2 thread2 = new MyThread2(task);
		thread2.start();
	}
}
