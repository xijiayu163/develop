package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LesF_multi_execute_in_order {
	
	volatile private static int nextPrintWho = 1;
	private static ReentrantLock lock = new ReentrantLock();
	final private static Condition conditionA = lock.newCondition();
	final private static Condition conditionB = lock.newCondition();
	final private static Condition conditionC = lock.newCondition();

	/**
	 * 
	 * 使用condition对象可以对线程执行的业务进行排序规则
	 * 
	 * ThreadA 1
ThreadA 2
ThreadA 3
ThreadB 1
ThreadB 2
ThreadB 3
	 * @param args
	 */
	public static void main(String[] args) {

		Thread threadA = new Thread() {
			public void run() {
				try {
					lock.lock();
					while (nextPrintWho != 1) {
						conditionA.await();
					}
					for (int i = 0; i < 3; i++) {
						System.out.println("ThreadA " + (i + 1));
					}
					nextPrintWho = 2;
					conditionB.signalAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		};

		Thread threadB = new Thread() {
			public void run() {
				try {
					lock.lock();
					while (nextPrintWho != 2) {
						conditionB.await();
					}
					for (int i = 0; i < 3; i++) {
						System.out.println("ThreadB " + (i + 1));
					}
					nextPrintWho = 3;
					conditionC.signalAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		};

		Thread threadC = new Thread() {
			public void run() {
				try {
					lock.lock();
					while (nextPrintWho != 3) {
						conditionC.await();
					}
					for (int i = 0; i < 3; i++) {
						System.out.println("ThreadC " + (i + 1));
					}
					nextPrintWho = 1;
					conditionA.signalAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		};
		Thread[] aArray = new Thread[5];
		Thread[] bArray = new Thread[5];
		Thread[] cArray = new Thread[5];

		for (int i = 0; i < 5; i++) {
			aArray[i] = new Thread(threadA);
			bArray[i] = new Thread(threadB);
			cArray[i] = new Thread(threadC);

			aArray[i].start();
			bArray[i].start();
			cArray[i].start();
		}

	}
}
