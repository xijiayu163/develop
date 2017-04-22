package com.yu.chapter7.supplement.Les1.state;

public class Les1_new_runnable_terminated {

	// NEW,
	// RUNNABLE,
	// TERMINATED,

	// BLOCKED,
	// WAITING,
	// TIMED_WAITING,

	public static class MyThread extends Thread {

		public MyThread() {
			System.out.println("���췽���е�״̬��" + Thread.currentThread().getState());
		}

		@Override
		public void run() {
			System.out.println("run�����е�״̬��" + Thread.currentThread().getState());
		}

	}
	
	/**
	 * NEW:���߳�ʵ�����󻹴�δִ��start()����ʱ��״̬
	 * RUNNABLE:���߳̽������е�״̬
	 * TERMINATED:���̱߳�����ʱ��״̬
	 * 
	 * ���췽���е�״̬��RUNNABLE  ��ʵ��main���̵߳�״̬
main�����е�״̬1��NEW
run�����е�״̬��RUNNABLE
main�����е�״̬2��TERMINATED

	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread t = new MyThread();
			System.out.println("main�����е�״̬1��" + t.getState());
			Thread.sleep(1000);
			t.start();
			Thread.sleep(1000);
			System.out.println("main�����е�״̬2��" + t.getState());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
