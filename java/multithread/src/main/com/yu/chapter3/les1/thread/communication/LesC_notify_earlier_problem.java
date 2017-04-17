package com.yu.chapter3.les1.thread.communication;

public class LesC_notify_earlier_problem {

	private String lock = new String("");

	private Runnable runnableA = new Runnable() {
		public void run() {
			try {
				synchronized (lock) {
					System.out.println("begin wait");
					lock.wait();
					System.out.println("end wait");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	private Runnable runnableB = new Runnable() {
		public void run() {
			synchronized (lock) {
				System.out.println("begin notify");
				lock.notify();
				System.out.println("end notify");
			}
		}
	};

	/**
	 * 通知过早造成 wait后不会有机会唤醒了
	 * begin notify
end notify
begin wait
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		LesC_notify_earlier_problem run = new LesC_notify_earlier_problem();

//		Thread a = new Thread(run.runnableA);
//		a.start();
//
//		Thread.sleep(100);
//
//		Thread b = new Thread(run.runnableB);
//		b.start();

		Thread b = new Thread(run.runnableB);
		b.start();
		
		Thread a = new Thread(run.runnableA);
		a.start();


	}

}
