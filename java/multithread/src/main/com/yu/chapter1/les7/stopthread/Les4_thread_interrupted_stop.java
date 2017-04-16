package com.yu.chapter1.les7.stopthread;

public class Les4_thread_interrupted_stop extends Thread {
	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 500000; i++) {
			//�ж��ж�״̬��ͬʱ����жϱ��
			if (Thread.interrupted()) {
				System.out.println("�Ѿ���ֹͣ״̬��!��Ҫ�˳���!");
				break;
			}
			System.out.println("i=" + (i + 1));
		}
		System.out.println("�ұ����������˴�����for�ּ������У��̲߳�δֹͣ��");
	}
	
	/**
	 * 	end!
		�Ѿ���ֹͣ״̬��!��Ҫ�˳���!
		�ұ����������˴�����for�ּ������У��̲߳�δֹͣ��
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Les4_thread_interrupted_stop thread = new Les4_thread_interrupted_stop();
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
