package com.yu.chapter6.singleton;

public class Les2_1_lazy_error1 {

	public static class MyObject {

		private static MyObject myObject;

		private MyObject() {
		}

		public static MyObject getInstance() {
			// �ӳټ���
			if (myObject != null) {
			} else {
				myObject = new MyObject();
			}
			return myObject;
		}

	}

	public static class MyThread extends Thread {
		@Override
		public void run() {
			System.out.println(MyObject.getInstance().hashCode());
		}

	}
	
	/**
	 * ��������ģʽ
	 * ��ʵ����Ȼȡ��һ�������ʵ������������ڶ��̵߳Ļ����У��ͻ����ȡ�����ʵ�����������
	 * ����ģʽ�ĳ������౳���
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		t1.start();
	}

}
