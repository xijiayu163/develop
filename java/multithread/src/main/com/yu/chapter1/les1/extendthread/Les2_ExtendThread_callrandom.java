package com.yu.chapter1.les1.extendthread;

public class Les2_ExtendThread_callrandom extends Thread {
	@Override
	public void run() {
		super.run();
		System.out.println("MyThread Name:"+Thread.currentThread().getName());
	}
	
	/* ���õ������
	 * cpu�Բ�ȷ���ķ�ʽ������˵�������ʱ���������߳��е�run����
	 * ���н���
	 * MyThread Name:Thread-0
	 */
	public static void main(String[] args) {
		Les2_ExtendThread_callrandom eThread = new Les2_ExtendThread_callrandom();
		eThread.start();
		System.out.println("���н���");
	}
}
