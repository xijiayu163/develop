package com.yu.chapter2.les2.usesynchronized_codeblock;


public class LesA1_static_method_classlock {

	public static class Service {

		synchronized public static void printA() {
			try {
				System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName()
						+ "��" + System.currentTimeMillis() + "����printA");
				Thread.sleep(3000);
				System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName()
						+ "��" + System.currentTimeMillis() + "�뿪printA");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public static void printB() {
			System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName() + "��"
					+ System.currentTimeMillis() + "����printB");
			System.out.println("�߳�����Ϊ��" + Thread.currentThread().getName() + "��"
					+ System.currentTimeMillis() + "�뿪printB");
		}

	}

	public static class ThreadA extends Thread {
		@Override
		public void run() {
			Service.printA();
		}

	}
	
	public static class ThreadB extends Thread {
		@Override
		public void run() {
			Service.printB();
		}
	}
	
	/**
	 * �Ծ�̬������synchronized,��ס����class�࣬������󣬶�������ʵ�����
	 * �߳�����Ϊ��A��1492316674613����printA
�߳�����Ϊ��A��1492316677614�뿪printA
�߳�����Ϊ��B��1492316677614����printB
�߳�����Ϊ��B��1492316677614�뿪printB
	 * @param args
	 */
	public static void main(String[] args) {

		ThreadA a = new ThreadA();
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB();
		b.setName("B");
		b.start();

	}

}
