package com.yu.chapter3.les4.inheritablethreadlocal;

import java.util.Date;

public class Les2_inheritableThreadLocal_value_extend_andChange {

	public static class InheritableThreadLocalExt extends InheritableThreadLocal {
		@Override
		protected Object initialValue() {
			return new Date().getTime();
		}
		
		@Override
		protected Object childValue(Object parentValue){
			return parentValue+" �������߳��мӵ�";
		}
	}

	public static class Tools {
		public static InheritableThreadLocalExt tl = new InheritableThreadLocalExt();
	}

	public static class ThreadA extends Thread {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					System.out.println("��ThreadA�߳���ȡֵ=" + Tools.tl.get());
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *  ���߳�ȡ��ֵ���޸�
	 * 
	 *  ��Main�߳���ȡֵ=1492469823816
		��ThreadA�߳���ȡֵ=1492469823816 �������߳��мӵ�
		
		ע��:������߳���ȡ��ֵ��ͬʱ�����߳̽�InheritableThreadLocal�е�ֵ���и��ģ�
		��ô���߳�ȡ����ֵ���Ǿ�ֵ
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println("       ��Main�߳���ȡֵ=" + Tools.tl.get());
				Thread.sleep(100);
			}
			Thread.sleep(5000);
			ThreadA a = new ThreadA();
			a.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
