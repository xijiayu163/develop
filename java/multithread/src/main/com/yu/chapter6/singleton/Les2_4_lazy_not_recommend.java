package com.yu.chapter6.singleton;

public class Les2_4_lazy_not_recommend {

	public static class MyObject {

		private static MyObject myObject;

		private MyObject() {
		}

		public static MyObject getInstance() {
			try {
				// ����д����ͬ�ڣ�
				// synchronized public static MyObject getInstance()
				// ��д����Ч��һ���ܵͣ�ȫ�����뱻����
				synchronized (MyObject.class) {
					if (myObject != null) {
					} else {
						// ģ���ڴ�������֮ǰ��һЩ׼���ԵĹ���
						Thread.sleep(3000);

						myObject = new MyObject();
					}
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
	 * 		// �˰汾������Ȼ����ȷ��
		// ��public static MyObject getInstance()����
		// �е�ȫ�����붼��ͬ�����ˣ�����������Ч��
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
