package com.yu.chapter2.les2.usesynchronized_codeblock;

public class Les4_synchornized_codeblock_same_monitorobject {

	public static class ObjectService {

		public void serviceMethodA() {
			try {
				synchronized (this) {
					System.out.println("A begin time=" + System.currentTimeMillis());
					Thread.sleep(2000);
					System.out.println("A end    end=" + System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void serviceMethodB() {
			synchronized (this) {
				try {
					System.out.println("B begin time=" + System.currentTimeMillis());
					Thread.sleep(3000);
					System.out.println("B end    end=" + System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		synchronized public void serviceMethodC() {
			try {
				System.out.println("C begin time=" + System.currentTimeMillis());
				Thread.sleep(2000);
				System.out.println("C end    end=" + System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadA extends Thread {

		private ObjectService service;

		public ThreadA(ObjectService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			super.run();
			service.serviceMethodA();
		}

	}

	public static class ThreadB extends Thread {
		private ObjectService service;

		public ThreadB(ObjectService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			super.run();
			service.serviceMethodB();
		}
	}
	
	public static class ThreadC extends Thread {
		private ObjectService service;

		public ThreadC(ObjectService service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			super.run();
			service.serviceMethodC();
		}
	}
	
	/**
	 * ��һ���̷߳���object��һ��synchronized(this)ͬ�������ʱ�������̶߳�ͬһ��object
	 * ������synchronized(this)ͬ�������ķ��ʽ�����������˵��synchronizedʹ�õġ������������
	 * ��ͬһ��
	 * A begin time=1492312776114
A end    end=1492312778115
C begin time=1492312778115
C end    end=1492312780115
B begin time=1492312780115
B end    end=1492312783115
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectService service = new ObjectService();

		ThreadA a = new ThreadA(service);
		a.setName("a");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("b");
		b.start();
		
		ThreadC c = new ThreadC(service);
		c.setName("c");
		c.start();
	}

	
	
}
