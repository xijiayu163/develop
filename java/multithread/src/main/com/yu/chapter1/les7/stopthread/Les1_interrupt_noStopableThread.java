package com.yu.chapter1.les7.stopthread;

public class Les1_interrupt_noStopableThread extends Thread {
	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 500000; i++) {
			System.out.println("i=" + (i + 1));
		}
	}
	
	/**
	 * interrupt方法意为中止线程，使用效果并不像for+break那样，马上停止循环。仅仅是在当前
	 * 线程中打了一个停止标记，并不是真的停止线程
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Les1_interrupt_noStopableThread thread = new Les1_interrupt_noStopableThread();
			thread.start();
			Thread.sleep(2000);
			thread.interrupt();
		} catch (InterruptedException e) {
			System.out.println("main catch");
			e.printStackTrace();
		}
	}
}
