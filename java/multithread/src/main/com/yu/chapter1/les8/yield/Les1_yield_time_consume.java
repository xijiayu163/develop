package com.yu.chapter1.les8.yield;

public class Les1_yield_time_consume extends Thread {

	/**
	 * yield�����Ƿ�����ǰ��CPU��Դ�������ø�����������ȥռ��CPUִ��ʱ�䣬��������ʱ�䲻ȷ��
	 * �п��ܸոշ����������ֻ��CPUʱ��Ƭ
	 * ʹ��yield��ʱ��3911���룡
	 * ע�͵�yield,��ʱ��23���룡
	 * 
	 */
	@Override
	public void run() {
		long beginTime = System.currentTimeMillis();
		int count = 0;
		for (int i = 0; i < 50000000; i++) {
			//Thread.yield();
			count = count + (i + 1);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("��ʱ��" + (endTime - beginTime) + "���룡");
	}

	public static void main(String[] args) {
		Les1_yield_time_consume thread = new Les1_yield_time_consume();
		thread.start();
	}
	
}
