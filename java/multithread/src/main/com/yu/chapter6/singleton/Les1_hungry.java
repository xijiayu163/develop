package com.yu.chapter6.singleton;

public class Les1_hungry {
	
	public static class MyObject {

		// �������ط�ʽ==����ģʽ
		private static MyObject myObject = new MyObject();

		private MyObject() {
		}

		public static MyObject getInstance() {
			// �˴���汾Ϊ��������
			// �˰汾�����ȱ���ǲ���������ʵ������
			// ��ΪgetInstance()����û��ͬ��
			// �����п��ܳ��ַ��̰߳�ȫ����
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
	 * ����̨��ӡ��hashcodeΪͬһ��ֵ��˵��������ͬһ����Ҳ��ʵ�������������͵������ģʽ
	 * 
	 * 672430613
672430613
672430613
	 * 
	 * 
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
