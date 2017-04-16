package com.yu.chapter2.les2.usesynchronized_codeblock;

public class Les6_synchornized_codeblock_usetherobject_local_variable {

	public static class Service {

		private String usernameParam;
		private String passwordParam;

		public void setUsernamePassword(String username, String password) {
			try {
				String anyString = new String();
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
	 * 线程名称为：A在1492313434449进入同步块
线程名称为：B在1492313434451进入同步块
线程名称为：A在1492313437450离开同步块
线程名称为：B在1492313437451离开同步块

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
