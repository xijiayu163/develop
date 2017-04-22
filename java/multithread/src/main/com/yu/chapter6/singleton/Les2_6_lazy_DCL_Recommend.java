package com.yu.chapter6.singleton;

public class Les2_6_lazy_DCL_Recommend {

	public static class MyObject {

		private volatile static MyObject myObject;

		private MyObject() {
		}

		// ʹ��˫���������������
		// ����֤�˲���Ҫͬ��������첽
		// �ֱ�֤�˵�����Ч��
		public static MyObject getInstance() {
			try {
				if (myObject != null) {
				} else {
					// ģ���ڴ�������֮ǰ��һЩ׼���ԵĹ���
					Thread.sleep(3000);
					synchronized (MyObject.class) {
						if (myObject == null) {
							myObject = new MyObject();
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return myObject;
		}
		// �˰汾�Ĵ����Ϊ��
		// ˫�ؼ��Double-Check Locking

	}
	
	public static class MyThread extends Thread {

		@Override
		public void run() {
			System.out.println(MyObject.getInstance().hashCode());
		}

	}
	
	/**
	 * ˫��������,����֤�˲���Ҫͬ��������첽,�ֱ�֤�˵�����Ч��
	 * 
	 * 1980094282
1980094282
1980094282
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
