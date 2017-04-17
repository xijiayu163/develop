package com.yu.chapter4.lock.les1.reentranlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Les5_MustUseMoreCondition_Error {

	public static class MyService {

		private Lock lock = new ReentrantLock();
		public Condition condition = lock.newCondition();

		public void awaitA() {
			try {
				lock.lock();
				System.out.println("begin awaitAʱ��Ϊ" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
				condition.await();
				System.out.println("  end awaitAʱ��Ϊ" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void awaitB() {
			try {
				lock.lock();
				System.out.println("begin awaitBʱ��Ϊ" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
				condition.await();
				System.out.println("  end awaitBʱ��Ϊ" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}

		public void signalAll() {
			try {
				lock.lock();
				System.out.println("  signalAllʱ��Ϊ" + System.currentTimeMillis()
						+ " ThreadName=" + Thread.currentThread().getName());
				condition.signalAll();
			} finally {
				lock.unlock();
			}
		}
	}
	
	public static class ThreadA extends Thread {

		private MyService service;

		public ThreadA(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.awaitA();
		}
	}
	
	public static class ThreadB extends Thread {

		private MyService service;

		public ThreadB(MyService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.awaitB();
		}
	}
	
	/**
	 * �������к��߳�A��B����������
	 * ����뵥�����Ѳ����̣߳�����ʹ�ö��condition����Ҳ����condition������Ի��Ѳ���
	 * ָ���̣߳������������������е�Ч�������Զ��߳̽��з��飬Ȼ���ٻ���ָ�����е��߳�
	 * 
	 * begin awaitAʱ��Ϊ1492471719424 ThreadName=A
begin awaitBʱ��Ϊ1492471719425 ThreadName=B
  signalAllʱ��Ϊ1492471722424 ThreadName=main
  end awaitAʱ��Ϊ1492471722424 ThreadName=A
  end awaitBʱ��Ϊ1492471722424 ThreadName=B
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		MyService service = new MyService();

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();

		Thread.sleep(3000);

		service.signalAll();

	}

}
