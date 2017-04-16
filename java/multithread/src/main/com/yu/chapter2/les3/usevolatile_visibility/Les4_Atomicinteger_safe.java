package com.yu.chapter2.les3.usevolatile_visibility;

import java.util.concurrent.atomic.AtomicInteger;

public class Les4_Atomicinteger_safe {

	public static class AddCountThread extends Thread {
		private AtomicInteger count = new AtomicInteger(0);

		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				System.out.println(count.incrementAndGet());
			}
		}
	}
	
	/**
	 * 成功累加到5000
	 * @param args
	 */
	public static void main(String[] args) {
		AddCountThread countService = new AddCountThread();

		Thread t1 = new Thread(countService);
		t1.start();

		Thread t2 = new Thread(countService);
		t2.start();

		Thread t3 = new Thread(countService);
		t3.start();

		Thread t4 = new Thread(countService);
		t4.start();

		Thread t5 = new Thread(countService);
		t5.start();

	}

}
