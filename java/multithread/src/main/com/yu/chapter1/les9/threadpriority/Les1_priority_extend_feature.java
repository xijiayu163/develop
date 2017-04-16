package com.yu.chapter1.les9.threadpriority;

public class Les1_priority_extend_feature{
	public static class MyThread1 extends Thread {
		@Override
		public void run() {
			System.out.println("MyThread1 run priority=" + this.getPriority());
			MyThread2 thread2 = new MyThread2();
			thread2.start();
		}
	}
	
	public static class MyThread2 extends Thread {
		@Override
		public void run() {
			System.out.println("MyThread2 run priority=" + this.getPriority());
		}
	}

	/**
	 * main thread begin priority=5
main thread end   priority=5
MyThread1 run priority=5
MyThread2 run priority=5

将设置优先级的代码恢复打印结果
main thread begin priority=5
main thread end   priority=6
MyThread1 run priority=6
MyThread2 run priority=6

	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("main thread begin priority="
				+ Thread.currentThread().getPriority());
		Thread.currentThread().setPriority(6);
		System.out.println("main thread end   priority="
				+ Thread.currentThread().getPriority());
		MyThread1 thread1 = new MyThread1();
		thread1.start();
	}
}


