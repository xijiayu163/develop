package com.yu.chapter3.les1.thread.communication;

public class LesB_wait_long_autoawake {
	static private Object lock = new Object();
	static private Runnable runnable1 = new Runnable() {
		public void run() {
			try {
				synchronized (lock) {
					System.out.println("wait begin timer="
							+ System.currentTimeMillis());
					lock.wait(5000);
					System.out.println("wait   end timer="
							+ System.currentTimeMillis());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	static private Runnable runnable2 = new Runnable() {
		public void run() {
			synchronized (lock) {
				System.out.println("notify begin timer="
						+ System.currentTimeMillis());
				lock.notify();
				System.out.println("notify   end timer="
						+ System.currentTimeMillis());
			}
		}
	};

	/**
	 * wait(5000) �ȴ�5������û�л��ѣ����Զ�����
	 * wait begin timer=1492384547348
wait   end timer=1492384552350

		�ָ�ע�ʹ��벢���� 3��󱻻���
		
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(runnable1);
		t1.start();
		Thread.sleep(3000);
		Thread t2 = new Thread(runnable2);
		t2.start();
	}

}
