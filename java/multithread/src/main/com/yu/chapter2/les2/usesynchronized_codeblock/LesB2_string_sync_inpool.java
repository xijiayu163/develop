package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesB2_string_sync_inpool {

	public static class Service {
		public static void print(String stringParam) {
			try {
				synchronized (stringParam) {
					while (true) {
						System.out.println(Thread.currentThread().getName());
						Thread.sleep(1000);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
			service.print("AA");
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
			service.print("AA");
		}
	}
	
	/**
	 * string������ֵ����AA�������̳߳�����ͬ��������������߳�B����ִ�С�
	 * �����String�����������������⡣����ڴ����������£�ͬ��synchronized�����
	 * ����ʹ��String��Ϊ�����󣬶���������
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

	}

}
