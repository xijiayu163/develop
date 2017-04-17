package com.yu.chapter3.les2.thread.communication;

public class Les2_execute_after_threadfinish_join {

	public static class MyThread extends Thread {

		@Override
		public void run() {
			try {
				int secondValue = (int) (Math.random() * 10000);
				System.out.println(secondValue);
				Thread.sleep(secondValue);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * ʹ��join��threadTestֹͣ����ִ��
	 * ����join��������ʹ�������̶߳���x����ִ��run()�����е����񣬶�ʹ��ǰ�߳�z����������������
	 * �ȴ��߳�x���ٺ��ټ���ִ���߳�z����Ĵ���
	 * ����join����ʹ�߳��Ŷӵ����ã���Щ����ͬ��������Ч����join��synchronized��������:join��
	 * �ڲ�ʹ��wait()�������еȴ�����synchronized�ؼ���ʹ�õ���"���������"ԭ����Ϊͬ��
	 * ��join�����У������ǰ�̶߳����жϣ���ǰ�̳߳����쳣
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread threadTest = new MyThread();
			threadTest.start();
			threadTest.join();

			System.out.println("���뵱threadTest����ִ����Ϻ�����ִ�У���������");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
