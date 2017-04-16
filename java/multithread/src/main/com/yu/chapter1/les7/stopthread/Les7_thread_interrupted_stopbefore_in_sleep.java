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
			System.out.println("��ֹͣ����������sleep!����catch!");
			e.printStackTrace();
		}
	}
	
	/**
	 * run begin
��ֹͣ����������sleep!����catch!
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
