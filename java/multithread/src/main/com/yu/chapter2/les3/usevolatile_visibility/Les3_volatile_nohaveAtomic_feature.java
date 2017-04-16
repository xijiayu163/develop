package com.yu.chapter2.les3.usevolatile_visibility;

public class Les3_volatile_nohaveAtomic_feature {
	
	public static class MyThread extends Thread {
//		volatile public static int count;

//		/**
//		 * count��ֵ����100�ۼӵģ��ɼ�volatileû�б�֤ԭ����
//		 * count=2046
//	count=1546
//	count=1546
//	count=1366
//	count=1266
//		 * @param args
//		 */
//		private static void addCount() {
//			for (int i = 0; i < 100; i++) {
//				count++;
//			}
//			System.out.println("count=" + count);
//		}
		
		
	   public static int count;
		
		/**
		 * ��synchronized�����ۼӣ���֤��ԭ����,ʹ����synchronized,count����Ҳ��û�б�Ҫ��volatile
		 * ����֤�ɼ����ˣ���Ϊsynchronizedͬʱ��֤��ԭ���Ժͼ�ӱ�֤�˿ɼ��ԣ���Ϊ���Ὣ˽���ڴ�͹����ڴ��е�
		 * ������ͬ����
		 * count=9700
count=9800
count=9900
count=10000
		 */
		synchronized private static void addCount() {
			for (int i = 0; i < 100; i++) {
				count++;
			}
			System.out.println("count=" + count);
		}

		@Override
		public void run() {
			addCount();
		}
	}
	

	public static void main(String[] args) {
		MyThread[] mythreadArray = new MyThread[100];
		for (int i = 0; i < 100; i++) {
			mythreadArray[i] = new MyThread();
		}

		for (int i = 0; i < 100; i++) {
			mythreadArray[i].start();
		}

	}

}
