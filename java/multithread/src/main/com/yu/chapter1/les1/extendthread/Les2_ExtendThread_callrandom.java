package com.yu.chapter1.les1.extendthread;

public class Les2_ExtendThread_callrandom extends Thread {
	@Override
	public void run() {
		super.run();
		System.out.println("MyThread Name:"+Thread.currentThread().getName());
	}
	
	/* 调用的随机性
	 * cpu以不确定的方式，或者说以随机的时间来调用线程中的run方法
	 * 运行结束
	 * MyThread Name:Thread-0
	 */
	public static void main(String[] args) {
		Les2_ExtendThread_callrandom eThread = new Les2_ExtendThread_callrandom();
		eThread.start();
		System.out.println("运行结束");
	}
}
