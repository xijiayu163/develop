package com.yu.chapter3.les2.thread.communication;

public class Les3_join_interrupt_exception {

	public static class ThreadA extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				String newString = new String();
				Math.random();
			}
		}
	}

	public static class ThreadB extends Thread {

		@Override
		public void run() {
			try {
				ThreadA a = new ThreadA();
				a.start();
				a.join();

				System.out.println("线程B在run end处打印了");
			} catch (InterruptedException e) {
				System.out.println("线程B在catch处打印了");
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadC extends Thread {

		private ThreadB threadB;

		public ThreadC(ThreadB threadB) {
			super();
			this.threadB = threadB;
		}

		@Override
		public void run() {
			threadB.interrupt();
		}

	}
	
	/**
	 * 运行结果:
	 * 线程B在catch处打印了
	 * java.lang.InterruptedException
	 * 
	 * 在join过程中，如果当前线程对象被中断，则当前线程出现异常
	 * 方法join与interrupt彼此遇到，则会出现异常。但进程按钮还呈"红色"，原因是线程ThreadA
	 * 还在继续运行，线程ThreadA并未出现异常，是正常执行的状态
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			ThreadB b = new ThreadB();
			b.start();

			Thread.sleep(500);

			ThreadC c = new ThreadC(b);
			c.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
