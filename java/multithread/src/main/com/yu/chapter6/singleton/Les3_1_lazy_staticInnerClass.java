package com.yu.chapter6.singleton;

public class Les3_1_lazy_staticInnerClass {

	public static class MyObject {

		// �ڲ��෽ʽ
		private static class MyObjectHandler {
			private static MyObject myObject = new MyObject();
		}

		private MyObject() {
		}

		public static MyObject getInstance() {
			return MyObjectHandler.myObject;
		}

	}
	
	public static class MyThread extends Thread {

		@Override
		public void run() {
			System.out.println(MyObject.getInstance().hashCode());
		}

	}
	
	/**
	 * ��̬�ڲ��෽ʽʵ�ֶ��̵߳���ģʽ
	 * ȱ��:����������л�����ʱ��ʹ��Ĭ�ϵķ�ʽ���еõ��Ľ�����Ƕ�����
	 * 
	 * 856722497
856722497
856722497
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		MyThread t3 = new MyThread();

		t1.start();
		t2.start();
		t3.start();

	}

}
