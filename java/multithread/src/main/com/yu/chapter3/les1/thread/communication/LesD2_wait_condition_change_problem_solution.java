package com.yu.chapter3.les1.thread.communication;

import java.util.ArrayList;
import java.util.List;

public class LesD2_wait_condition_change_problem_solution {

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
					while (ValueObject.list.size() == 0) {
						if (ValueObject.list.size() == 0) {
							System.out.println("wait begin ThreadName=" + Thread.currentThread().getName());
							lock.wait();
							System.out.println("wait   end ThreadName=" + Thread.currentThread().getName());
						}
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
	 * 
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
