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
			return parentValue+" 我在子线程中加的";
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
	 *  子线程取得值并修改
	 * 
	 *  在Main线程中取值=1492469823816
		在ThreadA线程中取值=1492469823816 我在子线程中加的
		
		注意:如果子线程在取得值的同时，主线程将InheritableThreadLocal中的值进行更改，
		那么子线程取到的值还是旧值
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
