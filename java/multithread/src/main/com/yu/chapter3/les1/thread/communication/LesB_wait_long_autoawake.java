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
	 * wait(5000) 等待5秒后，如果没有唤醒，则自动唤醒
	 * wait begin timer=1492384547348
wait   end timer=1492384552350

		恢复注释代码并运行 3秒后被唤醒
		
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
