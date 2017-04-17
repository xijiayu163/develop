package com.yu.chapter3.les1.thread.communication;

import java.util.ArrayList;
import java.util.List;

public class LesD_wait_condition_change_problem {

	public static class ValueObject {

		volatile public static List list = new ArrayList();

	}

	public static class Add {

		private String lock;

		public Add(String lock) {
			super();
			this.lock = lock;
		}

		public void add() {
			synchronized (lock) {
				ValueObject.list.add("anyString");
				lock.notifyAll();
			}
		}

	}
	
	public static class Subtract {

		private String lock;

		public Subtract(String lock) {
			super();
			this.lock = lock;
		}

		public void subtract() {
			try {
				synchronized (lock) {
//					while (ValueObject.list.size() == 0) {
					if(ValueObject.list.size() == 0) {
						System.out.println("wait begin ThreadName="
								+ Thread.currentThread().getName());
						lock.wait();
						System.out.println("wait   end ThreadName="
								+ Thread.currentThread().getName());
					}
					ValueObject.list.remove(0);
					System.out.println("list size=" + ValueObject.list.size());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class ThreadAdd extends Thread {

		private Add p;

		public ThreadAdd(Add p) {
			super();
			this.p = p;
		}

		@Override
		public void run() {
			p.add();
		}

	}
	
	public static class ThreadSubtract extends Thread {

		private Subtract r;

		public ThreadSubtract(Subtract r) {
			super();
			this.r = r;
		}

		@Override
		public void run() {
			r.subtract();
		}

	}
	
	
	/**
	 * 尽管substract在同步方法内,有两个实现了删除remove()的线程，它们在Thread.sleep(1000)
	 * 之前都执行了Wait()方法，呈等待状态，当加操作的线程在1S后被运行时，通知了所有呈wait等待状态的减
	 * 操作的线程，那么第一个实现减操作的线程能正确删除list中索引为0的数据，但第二个实现减数据的线程则出
	 * 现索引溢出的异常。因为list中仅仅添加了一个数据，也只能删除一个数据，所以没有第二个数据可删。
	 * 
	 * 
	 * wait begin ThreadName=subtract1Thread
wait begin ThreadName=subtract2Thread
wait   end ThreadName=subtract2Thread
Exception in thread "subtract1Thread" java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
list size=0
wait   end ThreadName=subtract1Thread
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		String lock = new String("");

		Add add = new Add(lock);
		Subtract subtract = new Subtract(lock);

		ThreadSubtract subtract1Thread = new ThreadSubtract(subtract);
		subtract1Thread.setName("subtract1Thread");
		subtract1Thread.start();

		ThreadSubtract subtract2Thread = new ThreadSubtract(subtract);
		subtract2Thread.setName("subtract2Thread");
		subtract2Thread.start();

		Thread.sleep(1000);

		ThreadAdd addThread = new ThreadAdd(add);
		addThread.setName("addThread");
		addThread.start();

	}

}
