package com.yu.chapter6.singleton;

public class Les2_3_lazy_not_recommend {

	public static class MyObject {

		private static MyObject myObject;

		private MyObject() {
		}

		// ����ͬ������Ч��̫����
		// ��������������
		synchronized public static MyObject getInstance() {
			try {
				if (myObject != null) {
				} else {
					// ģ���ڴ�������֮ǰ��һЩ׼���ԵĹ���
					Thread.sleep(3000);
					myObject = new MyObject();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
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
	 * ��ȷ�ĵ���ģʽ������Ч���е�ͣ���Ϊ����������������
	 * 672430613
672430613
672430613
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
