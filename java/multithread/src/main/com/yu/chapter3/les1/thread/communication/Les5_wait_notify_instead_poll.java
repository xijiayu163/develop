package com.yu.chapter3.les1.thread.communication;

import java.util.ArrayList;
import java.util.List;

public class Les5_wait_notify_instead_poll {

	public static class MyList {

		private static List list = new ArrayList();

		public static void add() {
			list.add("anyString");
		}

		public static int size() {
			return list.size();
		}

	}
	
	public static class ThreadA extends Thread {

		private Object lock;

		public ThreadA(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			try {
				synchronized (lock) {
					if (MyList.size() != 5) {
						System.out.println("wait begin "
								+ System.currentTimeMillis());
						lock.wait();
						System.out.println("wait end  "
								+ System.currentTimeMillis());
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 
	 * �۲����н��:ThreadA����֪ͨ�󲢲������ͷ�����ֱ��notify���ڵĴ����ִ����ϲ��ͷ����Ž���ThreadB��wait��Ĵ���
	 * 
	 * wait begin 1492354517527
�����1��Ԫ��!
�����2��Ԫ��!
�����3��Ԫ��!
�����4��Ԫ��!
�ѷ���֪ͨ��
�����5��Ԫ��!
�����6��Ԫ��!
�����7��Ԫ��!
�����8��Ԫ��!
�����9��Ԫ��!
�����10��Ԫ��!
wait end  1492354527583
	 * @author xijia
	 *
	 */
	public static class ThreadB extends Thread {
		private Object lock;

		public ThreadB(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			try {
				synchronized (lock) {
					for (int i = 0; i < 10; i++) {
						MyList.add();
						if (MyList.size() == 5) {
							lock.notify();
							System.out.println("�ѷ���֪ͨ��");
						}
						System.out.println("�����" + (i + 1) + "��Ԫ��!");
						Thread.sleep(1000);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args) {

		try {
			Object lock = new Object();

			ThreadA a = new ThreadA(lock);
			a.start();

			Thread.sleep(50);

			ThreadB b = new ThreadB(lock);
			b.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
