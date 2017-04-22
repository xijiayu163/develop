package com.yu.chapter7.supplement.Les6.threadgroupexception;

public class Les2_exception_one_ingroup_interrupt_all {

	public static class MyThreadGroup extends ThreadGroup {

		public MyThreadGroup(String name) {
			super(name);
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			super.uncaughtException(t, e);
			this.interrupt();
		}

	}
	
	/**
	 * 
	 * @author xijia
	 *
	 */
	public static class MyThread extends Thread {

		private String num;

		public MyThread(ThreadGroup group, String name, String num) {
			super(group, name);
			this.num = num;
		}

		@Override
		public void run() {
			int numInt = Integer.parseInt(num);
			while (this.isInterrupted() == false) {
				System.out.println("死循环中：" + Thread.currentThread().getName());
			}
		}

	}
	
	/**
	 * 程序运行后，其中一个线程出现异常，其他线程全部停止了
	 * 使用自定义ThreadGoup线程组，并且重写uncauoghtException方法处理组内线程中断行为时，每个
	 * 线程对象中的run方法内部不要有异常catch语句，如果有catch语句，则public void uncauoghtException
	 * 方法不执行
	 * @param args
	 */
	public static void main(String[] args) {
		MyThreadGroup group = new MyThreadGroup("我的线程组");
		MyThread[] myThread = new MyThread[10];
		for (int i = 0; i < myThread.length; i++) {
			myThread[i] = new MyThread(group, "线程" + (i + 1), "1");
			myThread[i].start();
		}
		MyThread newT = new MyThread(group, "报错线程", "a");
		newT.start();
	}

}
