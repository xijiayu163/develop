package com.yu.chapter1.les7.stopthread;

public class Les2_thread_interrupted extends Thread {
	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 500000; i++) {
			System.out.println("i=" + (i + 1));
		}

	}
	
//	/**
//	 * �ж��߳��Ƿ�ֹͣ,��ӡ���
//	 * 	run
//		�Ƿ�ֹͣ1��=false
//		�Ƿ�ֹͣ2��=false
//		end!
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			Les1_thread_interrupted thread = new Les1_thread_interrupted();
//			thread.start();
//			Thread.sleep(1000);
//			System.out.println("�Ƿ�ֹͣ1��="+thread.interrupted());
//			System.out.println("�Ƿ�ֹͣ2��="+thread.interrupted());
//		} catch (InterruptedException e) {
//			System.out.println("main catch");
//			e.printStackTrace();
//		}
//		System.out.println("end!");
//	}
	
//	/**
//	 * 
//	 * �ж����߳��Ƿ�ֹͣ,��ӡ���,interruptedָ���ǵ�ǰ���øô�����̵߳��ж�״̬
//	 * 	run
//		�Ƿ�ֹͣ1��=true
//		�Ƿ�ֹͣ2��=false
//		end!
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			Les1_thread_interrupted thread = new Les1_thread_interrupted();
//			thread.start();
//			Thread.sleep(1000);
//			Thread.currentThread().interrupt();
//			//thread.interrupted()��̬������ָ���ǵ�ǰ���øô�����̣߳����Ｔ���߳�
//			System.out.println("�Ƿ�ֹͣ1��="+thread.interrupted());
//			//interrupted()���������״̬�����ã����Եڶ��ε��÷���ֵΪfalse.
//			System.out.println("�Ƿ�ֹͣ2��="+thread.interrupted());
//		} catch (InterruptedException e) {
//			System.out.println("main catch");
//			e.printStackTrace();
//		}
//		System.out.println("end!");
//	}
	
	/**
	 * 
	 * �ж����߳��Ƿ�ֹͣ,��ӡ���,isInterruptedָ�����̵߳��ж�״̬,���ú󲻻����״̬
	 * 	run
		�Ƿ�ֹͣ1��=true
		�Ƿ�ֹͣ2��=true
		end!
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Les2_thread_interrupted thread = new Les2_thread_interrupted();
			thread.start();
			Thread.sleep(1000);
//			Thread.currentThread().interrupt();
			thread.interrupt();
			//thread.interrupted()��̬������ָ���ǵ�ǰ���øô�����̣߳����Ｔ���߳�
			System.out.println("�Ƿ�ֹͣ1��="+thread.isInterrupted());
			//interrupted()���������״̬�����ã����Եڶ��ε��÷���ֵΪfalse.
			System.out.println("�Ƿ�ֹͣ2��="+thread.isInterrupted());
		} catch (InterruptedException e) {
			System.out.println("main catch");
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
