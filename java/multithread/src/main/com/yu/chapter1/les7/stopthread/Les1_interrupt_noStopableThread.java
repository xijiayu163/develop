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
	 * interrupt������Ϊ��ֹ�̣߳�ʹ��Ч��������for+break����������ֹͣѭ�����������ڵ�ǰ
	 * �߳��д���һ��ֹͣ��ǣ����������ֹͣ�߳�
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
