package com.yu.chapter3.thread.communication;

public class Les3_wait_haveobjectMonitor {

	/**
	 * waitʹ����
	 * ���н��:
	 * syn����
		syn��һ��
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String lock = new String();
			System.out.println("syn����");
			synchronized (lock) {
				System.out.println("syn��һ��");
				lock.wait();
				System.out.println("wait�µĴ��룡");
			}
			System.out.println("syn����Ĵ���");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
