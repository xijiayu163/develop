package com.yu.chapter3.les1.thread.communication;

public class LesC2_notify_earlier_problem {

	private String lock = new String("");
	volatile private boolean isFirstRunB = false;

	private Runnable runnableA = new Runnable() {
		public void run() {
			try {
				synchronized (lock) {
					while (isFirstRunB == false) {
						System.out.println("begin wait");
						lock.wait();
						System.out.println("end wait");
					}
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
				isFirstRunB = true;

			}
		}
	};

	/**
	 * 如果先通知胃，wait也就没有必要执行了
	 * 调换顺序后，程序恢复正确顺序
	 * begin notify
end notify
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		LesC2_notify_earlier_problem run = new LesC2_notify_earlier_problem();

		Thread b = new Thread(run.runnableB);
		b.start();


		Thread.sleep(100);

		Thread a = new Thread(run.runnableA);
		a.start();

		
//		Thread a = new Thread(run.runnableA);
//		a.start();
//		Thread b = new Thread(run.runnableB);
//		b.start();




	}

}
