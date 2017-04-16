package com.yu.chapter1.les7.suspendthread_notrecommend;

public class Les1_suspend_resume_notrecommend extends Thread {

	private long i = 0;

	public long getI() {
		return i;
	}

	public void setI(long i) {
		this.i = i;
	}

	@Override
	public void run() {
		while (true) {
			i++;
		}
	}
	
	public static void main(String[] args) {

		try {
			Les1_suspend_resume_notrecommend thread = new Les1_suspend_resume_notrecommend();
			thread.start();
			Thread.sleep(5000);
			// A
			thread.suspend();
			//暂停期间，getI()的值没变
			System.out.println("A= " + System.currentTimeMillis() + " i="
					+ thread.getI());
			Thread.sleep(5000);
			System.out.println("A= " + System.currentTimeMillis() + " i="
					+ thread.getI());
			// B
			thread.resume();
			//恢复后，i的值继续累加
			Thread.sleep(5000);

			// C 暂停期间，getI()的值没变
			thread.suspend();
			System.out.println("B= " + System.currentTimeMillis() + " i="
					+ thread.getI());
			Thread.sleep(5000);
			System.out.println("B= " + System.currentTimeMillis() + " i="
					+ thread.getI());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
