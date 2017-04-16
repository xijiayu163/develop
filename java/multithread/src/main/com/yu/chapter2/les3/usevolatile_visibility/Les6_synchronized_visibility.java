package com.yu.chapter2.les3.usevolatile_visibility;

public class Les6_synchronized_visibility {

	public static class Service {

		private boolean isContinueRun = true;

		public void runMethod() {
			String anyString = new String();
			while (isContinueRun == true) {
//				synchronized (anyString) {
//				}
			}
			System.out.println("停下来了！");
		}

		public void stopMethod() {
			isContinueRun = false;
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
			service.runMethod();
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
			service.stopMethod();
		}

	}
	
	/**
	 * 打印出 已经发起停止的命令了！  但是程序却永远也停不下来.
	 * 恢复12行的代码，程序可以停止。
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Service service = new Service();

			ThreadA a = new ThreadA(service);
			a.start();

			Thread.sleep(1000);

			ThreadB b = new ThreadB(service);
			b.start();

			System.out.println("已经发起停止的命令了！");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
