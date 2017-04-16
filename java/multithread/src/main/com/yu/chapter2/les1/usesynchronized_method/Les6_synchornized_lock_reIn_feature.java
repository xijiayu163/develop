package com.yu.chapter2.les1.usesynchronized_method;

public class Les6_synchornized_lock_reIn_feature {

	synchronized public void service1() {
		System.out.println("service1");
		service2();
	}

	synchronized public void service2() {
		System.out.println("service2");
		service3();
	}

	synchronized public void service3() {
		System.out.println("service3");
	}
	
	public static class MyThread extends Thread {
		@Override
		public void run() {
			Les6_synchornized_lock_reIn_feature service = new Les6_synchornized_lock_reIn_feature();
			service.service1();
		}
	}
	
	/**
	 * 锁重入，在service1里面调用service2,在service2里调用service3
	 * 锁重入先决条件，同一个线程，同一个锁
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t = new MyThread();
		t.start();
	}
}
