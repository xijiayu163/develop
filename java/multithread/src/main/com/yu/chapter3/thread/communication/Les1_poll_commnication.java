package com.yu.chapter3.thread.communication;

import java.util.ArrayList;
import java.util.List;

public class Les1_poll_commnication {
	
	public static class MyList {

		volatile private List list = new ArrayList();

		public void add() {
			list.add("高洪岩");
		}

		public int size() {
			return list.size();
		}

	}
	
	public static class ThreadA extends Thread {

		private MyList list;

		public ThreadA(MyList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					list.add();
					System.out.println("添加了" + (i + 1) + "个元素");
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class ThreadB extends Thread {

		private MyList list;

		public ThreadB(MyList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			try {
				while (true) {
					if (list.size() == 5) {
						System.out.println("==5了，线程b要退出了！");
						throw new InterruptedException();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 虽然两个线程间实现了通信，但有一个弊端就是，线程ThreadB.java不停地通过while语句轮询机制
	 * 来检测某一个条件，这样会浪费CPU资源
	 * 如果轮询的时间间隔很小，更浪费CPU资源；如果轮询的时间间隔很大，有可能会取不到想要的数据。
	 * 所以需要有一种机制来实现减少CPU的资源浪费，而且还可以实现在多个线程间通信，它就是"wait/notify"
	 * 机制
	 * @param args
	 */
	public static void main(String[] args) {
		MyList service = new MyList();

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();

	}

}
