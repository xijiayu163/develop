package com.yu.chapter2.les3.usevolatile_visibility;

public class Les3_volatile_nohaveAtomic_feature {
	
	public static class MyThread extends Thread {
//		volatile public static int count;

//		/**
//		 * count的值不是100累加的，可见volatile没有保证原子性
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
		 * 加synchronized后变成累加，保证了原子性,使用了synchronized,count变量也就没有必要用volatile
		 * 来保证可见性了，因为synchronized同时保证了原子性和间接保证了可见性，因为它会将私有内存和公共内存中的
		 * 数据做同步。
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
