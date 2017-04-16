package com.yu.chapter2.les2.usesynchronized_codeblock;


public class LesA2_static_nostatic_method_synchronize {

	public static class Service {

		synchronized public static void printA() {
			try {
				System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName()
						+ "��" + System.currentTimeMillis() + "����printA");
				Thread.sleep(3000);
				System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName()
						+ "��" + System.currentTimeMillis() + "�뿪printA");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public static void printB() {
			System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName() + "��"
					+ System.currentTimeMillis() + "����printB");
			System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName() + "��"
					+ System.currentTimeMillis() + "�뿪printB");
		}

		synchronized public void printC() {
			System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName() + "��"
					+ System.currentTimeMillis() + "����printC");
			System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName() + "��"
					+ System.currentTimeMillis() + "�뿪printC");
		}

	}

	public static class ThreadA extends Thread {
		private Service service;

		public ThreadA(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.printA();
		}

	}
	
	public static class ThreadB extends Thread {
		private Service service;

		public ThreadB(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.printB();
		}
	}
	
	public static class ThreadC extends Thread {

		private Service service;

		public ThreadC(Service service) {
			super();
			this.service = service;
		}

		@Override
		public void run() {
			service.printC();
		}
	}
	
	/**
	 * AB��˳��ִ�У�C��AB��Ϊ˳��ִ�У���Ϊ���Ķ���һ��
	 * �߳�����Ϊ��A��1492316881634����printA
�߳�����Ϊ��C��1492316881636����printC
�߳�����Ϊ��C��1492316881636�뿪printC
�߳�����Ϊ��A��1492316884635�뿪printA
�߳�����Ϊ��B��1492316884635����printB
�߳�����Ϊ��B��1492316884635�뿪printB
	 * @param args
	 */
	public static void main(String[] args) {

		Service service = new Service();

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();

		ThreadC c = new ThreadC(service);
		c.setName("C");
		c.start();

	}

}
