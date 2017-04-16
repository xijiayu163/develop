package com.yu.chapter1.les7.suspendthread_notrecommend;

public class Les2_weakness_suspend_resume_dead_lock {

	synchronized public void printString() {
		System.out.println("begin");
		if (Thread.currentThread().getName().equals("a")) {
			System.out.println("a线程永远 suspend了！");
			Thread.currentThread().suspend();
		}
		System.out.println("end");
	}

	/**
	 * begin
a线程永远 suspend了！
thread2启动了，但进入不了printString()方法！只打印1个begin
因为printString()方法被a线程锁定并且永远的suspend暂停了！
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final Les2_weakness_suspend_resume_dead_lock object = new Les2_weakness_suspend_resume_dead_lock();

			Thread thread1 = new Thread() {
				@Override
				public void run() {
					object.printString();
				}
			};

			thread1.setName("a");
			thread1.start();

			Thread.sleep(1000);

			Thread thread2 = new Thread() {
				@Override
				public void run() {
					System.out
							.println("thread2启动了，但进入不了printString()方法！只打印1个begin");
					System.out
							.println("因为printString()方法被a线程锁定并且永远的suspend暂停了！");
					object.printString();
				}
			};
			thread2.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
