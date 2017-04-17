package com.yu.chapter3.les4.inheritablethreadlocal;

import java.util.Date;

public class Les1_inheritableThreadLocal_value_extend {

	public static class InheritableThreadLocalExt extends InheritableThreadLocal {
		@Override
		protected Object initialValue() {
			return new Date().getTime();
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
					System.out.println("在ThreadA线程中取值=" + Tools.tl.get());
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * InheritableThreadLocal 可以让子线程从父线程中取得值
	 * 
	 *  在Main线程中取值=1492469584942
		在ThreadA线程中取值=1492469584942
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (int i = 0; i < 10; i++) {
				System.out.println("       在Main线程中取值=" + Tools.tl.get());
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
