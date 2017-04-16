package com.yu.chapter2.les1.usesynchronized_method;

public class Les2_Global_variable_not_safe{
	private int num = 0;

//	/** 
//	 * 线程a的结果被b覆盖
//	 * a set over!
//b set over!
//b num=200
//a num=200
//	 * @param username
//	 */
//	public void addI(String username) {
//		try {
//			if (username.equals("a")) {
//				num = 100;
//				System.out.println("a set over!");
//				Thread.sleep(2000);
//			} else {
//				num = 200;
//				System.out.println("b set over!");
//			}
//			System.out.println(username + " num=" + num);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 使用同步
	 * a set over!
a num=100
b set over!
b num=200
	 * @param username
	 */
	synchronized public void addI(String username) {
		try {
			if (username.equals("a")) {
				num = 100;
				System.out.println("a set over!");
				Thread.sleep(2000);
			} else {
				num = 200;
				System.out.println("b set over!");
			}
			System.out.println(username + " num=" + num);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class ThreadA extends Thread {

		private Les2_Global_variable_not_safe numRef;

		public ThreadA(Les2_Global_variable_not_safe numRef) {
			super();
			this.numRef = numRef;
		}

		@Override
		public void run() {
			super.run();
			numRef.addI("a");
		}
	}
	
	public static class ThreadB extends Thread {

		private Les2_Global_variable_not_safe numRef;

		public ThreadB(Les2_Global_variable_not_safe numRef) {
			super();
			this.numRef = numRef;
		}

		@Override
		public void run() {
			super.run();
			numRef.addI("b");
		}
	}
	
	public static void main(String[] args) {

		Les2_Global_variable_not_safe numRef = new Les2_Global_variable_not_safe();

		ThreadA athread = new ThreadA(numRef);
		athread.start();

		ThreadB bthread = new ThreadB(numRef);
		bthread.start();

	}
	
	
}

