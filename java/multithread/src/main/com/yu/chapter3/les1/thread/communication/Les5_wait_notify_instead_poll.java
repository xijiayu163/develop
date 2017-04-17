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
	 * 观察运行结果:ThreadA发出通知后并不立即释放锁，直到notify所在的代码块执行完毕并释放锁才进入ThreadB的wait后的代码
	 * 
	 * wait begin 1492354517527
添加了1个元素!
添加了2个元素!
添加了3个元素!
添加了4个元素!
已发出通知！
添加了5个元素!
添加了6个元素!
添加了7个元素!
添加了8个元素!
添加了9个元素!
添加了10个元素!
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
							System.out.println("已发出通知！");
						}
						System.out.println("添加了" + (i + 1) + "个元素!");
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
