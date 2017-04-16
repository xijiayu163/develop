package com.yu.chapter1.les9.threadpriority;

import java.util.Random;

public class Les3_priority_random_feature{
	public static class MyThread1 extends Thread {
		@Override
		public void run() {
			long beginTime = System.currentTimeMillis();
			long addResult = 0;
			for (int j = 0; j < 10; j++) {
				for (int i = 0; i < 50000; i++) {
					Random random = new Random();
					random.nextInt();
					addResult = addResult + i;
				}
			}
			long endTime = System.currentTimeMillis();
			System.out.println("������thread 1 use time=" + (endTime - beginTime));
		}
	}
	
	public static class MyThread2 extends Thread {
		@Override
		public void run() {
			long beginTime = System.currentTimeMillis();
			long addResult = 0;
			for (int j = 0; j < 10; j++) {
				for (int i = 0; i < 50000; i++) {
					Random random = new Random();
					random.nextInt();
					addResult = addResult + i;
				}
			}
			long endTime = System.currentTimeMillis();
			System.out.println("������thread 2 use time=" + (endTime - beginTime));
		}
	}
	
	/**
	 * Ϊ������Ч����һ������Ϊ5һ������Ϊ6
	 * ���Կ����������Ƚ�����
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			MyThread1 thread1 = new MyThread1();
			thread1.setPriority(5);
			thread1.start();

			MyThread2 thread2 = new MyThread2();
			thread2.setPriority(6);
			thread2.start();
		}
	}
}


