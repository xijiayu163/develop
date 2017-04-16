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
			//��ͣ�ڼ䣬getI()��ֵû��
			System.out.println("A= " + System.currentTimeMillis() + " i="
					+ thread.getI());
			Thread.sleep(5000);
			System.out.println("A= " + System.currentTimeMillis() + " i="
					+ thread.getI());
			// B
			thread.resume();
			//�ָ���i��ֵ�����ۼ�
			Thread.sleep(5000);

			// C ��ͣ�ڼ䣬getI()��ֵû��
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
