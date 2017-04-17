package com.yu.chapter4.lock.les1.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Les4_UseConditionWait_ok {

	public static class MyService {
		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public void waitMethod() {
			try {
				lock.lock();
				System.out.println("A");
				condition.await();
				System.out.println("B");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
				System.out.println("���ͷ��ˣ�");
			}
		}
	}
	
	public static class MyThreadA extends Thread {

		private MyService myService;

		public MyThreadA(MyService myService) {
			super();
			this.myService = myService;
		}

		@Override
		public void run() {
			myService.waitMethod();
		}

	}
	
	/**
	 * �ڿ���ֻ̨��ӡ��һ��A��ԭ���ǵ�����Condition�����await()������ʹ��ǰִ�������
	 * �߳̽����˵ȴ�waiting״̬
	 * @param args
	 */
	public static void main(String[] args) {
		MyService myService = new MyService();
		MyThreadA a = new MyThreadA(myService);
		a.start();
	}
}
