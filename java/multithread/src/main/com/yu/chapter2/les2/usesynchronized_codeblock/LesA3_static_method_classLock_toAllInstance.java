package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesA3_static_method_classLock_toAllInstance {

	public static class Service {

		synchronized public static void printA() {
			try {
				System.out.println(
						"�߳�����Ϊ��" + Thread.currentThread().getName() + "��" + System.currentTimeMillis() + "����printA");
				Thread.sleep(3000);
				System.out.println(
						"�߳�����Ϊ��" + Thread.currentThread().getName() + "��" + System.currentTimeMillis() + "�뿪printA");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public static void printB() {
			System.out.println(
					"�߳�����Ϊ��" + Thread.currentThread().getName() + "��" + System.currentTimeMillis() + "����printB");
			System.out.println(
					"�߳�����Ϊ��" + Thread.currentThread().getName() + "��" + System.currentTimeMillis() + "�뿪printB");
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
	
	/**
	 * class�����Զ��������ʵ������������
	 * �߳�����Ϊ��A��1492317185758����printA
�߳�����Ϊ��A��1492317188760�뿪printA
�߳�����Ϊ��B��1492317188760����printB
�߳�����Ϊ��B��1492317188760�뿪printB

	 * @param args
	 */
	public static void main(String[] args) {

		Service service1 = new Service();
		Service service2 = new Service();

		ThreadA a = new ThreadA(service1);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service2);
		b.setName("B");
		b.start();

	}

}
