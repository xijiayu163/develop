package com.yu.chapter1.les7.suspendthread_notrecommend;

public class Les3_weakness_suspend_resume_println_LockStop extends Thread {
	private long i = 0;

//	/**
//	 * ����main���ӡmain end!
//	 */
//	@Override
//	public void run() {
//		while (true) {
//			i++;
//			//System.out.println(i);
//		}
//	}
	
	/**
	 * println�ڲ�����ͬ��
	 * ����main�����ӡmain end!
	 */
	@Override
	public void run() {
		while (true) {
			i++;
			System.out.println(i);
		}
	}
	
	public static void main(String[] args) {

		try {
			Les3_weakness_suspend_resume_println_LockStop thread = new Les3_weakness_suspend_resume_println_LockStop();
			thread.start();
			Thread.sleep(1000);
			thread.suspend();
			System.out.println("main end!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
