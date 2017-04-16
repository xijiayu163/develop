package com.yu.chapter1.les7.suspendthread_notrecommend;

public class Les2_weakness_suspend_resume_dead_lock {

	synchronized public void printString() {
		System.out.println("begin");
		if (Thread.currentThread().getName().equals("a")) {
			System.out.println("a�߳���Զ suspend�ˣ�");
			Thread.currentThread().suspend();
		}
		System.out.println("end");
	}

	/**
	 * begin
a�߳���Զ suspend�ˣ�
thread2�����ˣ������벻��printString()������ֻ��ӡ1��begin
��ΪprintString()������a�߳�����������Զ��suspend��ͣ�ˣ�
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final Les2_weakness_suspend_resume_dead_lock object = new Les2_weakness_suspend_resume_dead_lock();

			Thread thread1 = new Thread() {
				@Override
				public void run() {
					object.printString();
				}
			};

			thread1.setName("a");
			thread1.start();

			Thread.sleep(1000);

			Thread thread2 = new Thread() {
				@Override
				public void run() {
					System.out
							.println("thread2�����ˣ������벻��printString()������ֻ��ӡ1��begin");
					System.out
							.println("��ΪprintString()������a�߳�����������Զ��suspend��ͣ�ˣ�");
					object.printString();
				}
			};
			thread2.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
