package com.yino.test;

import com.yino.drudgery.entity.Job;
import com.yino.drudgery.factory.JobFactory;

public class Apptest {

	Job job = JobFactory.createJob(null);

	public static void main(String[] args) {
		Apptest test = new Apptest();
		Thread t1 = new Thread(test.new run1(test));
		Thread t2 = new Thread(test.new run2(test));
		t1.start();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
	}

	public class run1 implements Runnable {
		private Apptest apptest;

		public run1(Apptest apptest) {
			this.apptest = apptest;
		}

		@Override
		public void run() {
			synchronized (apptest) {
				try {
					apptest.notify();
					apptest.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public class run2 implements Runnable {
		private Apptest apptest;

		public run2(Apptest apptest) {
			this.apptest = apptest;
		}

		@Override
		public void run() {
			synchronized (apptest) {
				apptest.notify();
			}
		}

	}
}
