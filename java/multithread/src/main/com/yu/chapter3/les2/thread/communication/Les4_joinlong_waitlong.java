package com.yu.chapter3.les2.thread.communication;

public class Les4_joinlong_waitlong {

	public static class MyThread extends Thread {

		@Override
		public void run() {
			try {
				System.out.println("begin Timer=" + System.currentTimeMillis());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * ����join(long)�еĲ������趨�ȴ���ʱ��
	 * ���н��:
	 * begin Timer=1492465426956
	 * end timer=1492465428956
	 * 
	 * ��main�е�join(2000)�ĳ�sleep(2000),���е�Ч�����ǵȴ�2S
	 * join(long)��sleep(long)������������2��������ͬ���Ĵ����ϡ�
	 * join(long)�Ĺ������ڲ���ʹ��wait(long)������ʵ�ֵģ�����join(long)���������ͷ������ص�
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread threadTest = new MyThread();
			threadTest.start();

			 threadTest.join(2000);// ֻ��2��
//			Thread.sleep(2000);

			System.out.println("  end timer=" + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
