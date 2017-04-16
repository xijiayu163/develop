package com.yu.chapter1.les7.stopthread;

public class Les7_thread_interrupted_stopbefore_in_sleep extends Thread {
	@Override
	public void run() {
		super.run();
		try {
			for(int i=0;i<100000;i++){
				System.out.println("i="+(i+1));
			}
			System.out.println("run begin");
			Thread.sleep(200000);
			System.out.println("run end");
		} catch (InterruptedException e) {
			System.out.println("先停止，再遇到了sleep!进入catch!");
			e.printStackTrace();
		}
	}
	
	/**
	 * run begin
先停止，再遇到了sleep!进入catch!
java.lang.InterruptedException: sleep interrupted
	 * @param args
	 */
	public static void main(String[] args) {
		Les7_thread_interrupted_stopbefore_in_sleep thread = new Les7_thread_interrupted_stopbefore_in_sleep();
		thread.start();
		thread.interrupt();
		System.out.println("end!");
	}
}
