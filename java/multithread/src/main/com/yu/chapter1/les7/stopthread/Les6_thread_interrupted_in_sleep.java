package com.yu.chapter1.les7.stopthread;

public class Les6_thread_interrupted_in_sleep extends Thread {
	@Override
	public void run() {
		super.run();
		try {
			System.out.println("run begin");
			Thread.sleep(200000);
			System.out.println("run end");
		} catch (InterruptedException e) {
			System.out.println("在沉睡中被停止!进入catch!"+this.isInterrupted());
			e.printStackTrace();
		}
	}
	
	/**
	 * 如果在sleep状态下停止某一线程，会进入catch语句，并且清除停止状态值，使之变成false
	 * end!
	在沉睡中被停止!进入catch!false
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Les6_thread_interrupted_in_sleep thread = new Les6_thread_interrupted_in_sleep();
			thread.start();
			Thread.sleep(200);
			thread.interrupt();
		} catch (InterruptedException e) {
			System.out.println("main catch");
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
