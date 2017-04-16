package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesC1_thread_wait_forever {

	public static class Service {
		synchronized public void methodA() {
			System.out.println("methodA begin");
			boolean isContinueRun = true;
			while (isContinueRun) {
			}
			System.out.println("methodA end");
		}

		synchronized public void methodB() {
			System.out.println("methodB begin");
			System.out.println("methodB end");
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
			service.methodA();
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
			service.methodB();
		}

	}
	
	/**
	 * 线程B没有机会去执行了,无限等待
	 * @param args
	 */
	public static void main(String[] args) {
		Service service = new Service();

		ThreadA athread = new ThreadA(service);
		athread.start();

		ThreadB bthread = new ThreadB(service);
		bthread.start();
	}

}
