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
			System.out.println("�ڳ�˯�б�ֹͣ!����catch!"+this.isInterrupted());
			e.printStackTrace();
		}
	}
	
	/**
	 * �����sleep״̬��ֹͣĳһ�̣߳������catch��䣬�������ֹͣ״ֵ̬��ʹ֮���false
	 * end!
	�ڳ�˯�б�ֹͣ!����catch!false
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
