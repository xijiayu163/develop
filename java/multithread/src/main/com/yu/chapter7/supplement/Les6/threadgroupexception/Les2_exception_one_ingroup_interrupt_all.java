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
				System.out.println("��ѭ���У�" + Thread.currentThread().getName());
			}
		}

	}
	
	/**
	 * �������к�����һ���̳߳����쳣�������߳�ȫ��ֹͣ��
	 * ʹ���Զ���ThreadGoup�߳��飬������дuncauoghtException�������������߳��ж���Ϊʱ��ÿ��
	 * �̶߳����е�run�����ڲ���Ҫ���쳣catch��䣬�����catch��䣬��public void uncauoghtException
	 * ������ִ��
	 * @param args
	 */
	public static void main(String[] args) {
		MyThreadGroup group = new MyThreadGroup("�ҵ��߳���");
		MyThread[] myThread = new MyThread[10];
		for (int i = 0; i < myThread.length; i++) {
			myThread[i] = new MyThread(group, "�߳�" + (i + 1), "1");
			myThread[i].start();
		}
		MyThread newT = new MyThread(group, "�����߳�", "a");
		newT.start();
	}

}
