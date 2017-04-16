package com.yu.chapter1.les7.stopthread;

public class Les6_thread_interrupted_stop_by_exception_recommend extends Thread {
	@Override
	public void run() {
		super.run();
		try {
			for (int i = 0; i < 500000; i++) {
				if (this.interrupted()) {
					System.out.println("�Ѿ���ֹͣ״̬��!��Ҫ�˳���!");
					throw new InterruptedException();
				}
				System.out.println("i=" + (i + 1));
			}
			System.out.println("����for����");
		} catch (InterruptedException e) {
			System.out.println("��MyThread.java��run�����е�catch�ˣ�");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Les6_thread_interrupted_stop_by_exception_recommend thread = new Les6_thread_interrupted_stop_by_exception_recommend();
			thread.start();
			Thread.sleep(2000);
			thread.interrupt();
		} catch (InterruptedException e) {
			System.out.println("main catch");
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
