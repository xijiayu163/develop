package com.yu.chapter2.les3.usevolatile_visibility;

import java.util.concurrent.atomic.AtomicLong;

public class Les5_Atomicinteger_Nosafe {
	
	public static class MyService {

		public static AtomicLong aiRef = new AtomicLong();

	    public void addNum() {
			System.out.println(Thread.currentThread().getName() + "����100֮���ֵ��:"
					+ aiRef.addAndGet(100));
			aiRef.addAndGet(1);
		}

	}
	
	public static class MyThread extends Thread {
		private MyService mySerivce;

		public MyThread(MyService mySerivce) {
			super();
			this.mySerivce = mySerivce;
		}

		@Override
		public void run() {
			mySerivce.addNum();
		}

	}
	
	/**
	 * ��ӡ˳����ˣ�Ӧ��ÿ��1��100�ټ�1��1�������������������ΪaddAndGet()������ԭ�ӵģ�������
	 * �ͷ���֮��ĵ���ȴ����ԭ�ӵġ�����������������Ҫ��ͬ��
	 * 
	 * Thread-1����100֮���ֵ��:100
Thread-0����100֮���ֵ��:200
Thread-2����100֮���ֵ��:300
Thread-3����100֮���ֵ��:403
Thread-4����100֮���ֵ��:504
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyService service = new MyService();

			MyThread[] array = new MyThread[5];
			for (int i = 0; i < array.length; i++) {
				array[i] = new MyThread(service);
			}
			for (int i = 0; i < array.length; i++) {
				array[i].start();
			}
			Thread.sleep(1000);
			System.out.println(service.aiRef.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
