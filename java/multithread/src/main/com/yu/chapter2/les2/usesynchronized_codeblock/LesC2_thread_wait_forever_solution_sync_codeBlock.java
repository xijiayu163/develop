package com.yu.chapter2.les2.usesynchronized_codeblock;

public class LesC2_thread_wait_forever_solution_sync_codeBlock {

	public static class Service {
		Object object1 = new Object();

		public void methodA() {
			synchronized (object1) {
				System.out.println("methodA begin");
				boolean isContinueRun = true;
				while (isContinueRun) {
				}
				System.out.println("methodA end");
			}
		}

		Object object2 = new Object();

		public void methodB() {
			synchronized (object2) {
				System.out.println("methodB begin");
				System.out.println("methodB end");
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
	 * 通过同步代码块锁不同的对象，解决无限等待的问题
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
