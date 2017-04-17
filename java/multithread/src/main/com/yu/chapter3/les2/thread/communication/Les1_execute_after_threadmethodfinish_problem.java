package com.yu.chapter3.les2.thread.communication;

public class Les1_execute_after_threadmethodfinish_problem {

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
	//��Ϊ��һ�����ֵ������ȷ��ʲôʱ���ͣ��
	public static void main(String[] args) {
		MyThread threadTest = new MyThread();
		threadTest.start();

		// Thread.sleep(?)
		System.out.println("���뵱threadTest����ִ����Ϻ�����ִ��");
		System.out.println("����������е�sleep()�е�ֵӦ��д�����أ�");
		System.out.println("���ǣ����ݲ���ȷ��:)");
	}
}
