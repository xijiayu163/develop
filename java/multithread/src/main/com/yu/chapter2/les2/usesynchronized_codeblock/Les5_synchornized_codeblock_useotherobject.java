package com.yu.chapter2.les2.usesynchronized_codeblock;

public class Les5_synchornized_codeblock_useotherobject {

	public static class Service {

		private String usernameParam;
		private String passwordParam;
		private String anyString = new String();

		public void setUsernamePassword(String username, String password) {
			try {
				synchronized (anyString) {
					System.out.println(
							"线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "进入同步块");
					usernameParam = username;
					Thread.sleep(3000);
					passwordParam = password;
					System.out.println(
							"线程名称为：" + Thread.currentThread().getName() + "在" + System.currentTimeMillis() + "离开同步块");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
			service.setUsernamePassword("a", "aa");

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
			service.setUsernamePassword("b", "bb");

		}

	}

	/**
	 * 线程名称为：A在1492313278119进入同步块
线程名称为：A在1492313281120离开同步块
线程名称为：B在1492313281120进入同步块
线程名称为：B在1492313284121离开同步块

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
