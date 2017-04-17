package com.yu.chapter3.les1.thread.communication;

public class LesE2_multi_product_multi_consumer_notify_feigndead {

	public static class ValueObject {
		public static String value = "";
	}
	
	//生产�?
	public static class P {
		private String lock;
		public P(String lock) {
			super();
			this.lock = lock;
		}

		public void setValue() {
			try {
				synchronized (lock) {
					while (!ValueObject.value.equals("")) {
						System.out.println("生产�? "
								+ Thread.currentThread().getName() + " WAITING了★");
						lock.wait();
					}
					System.out.println("生产�? " + Thread.currentThread().getName()
							+ " RUNNABLE�?");
					String value = System.currentTimeMillis() + "_"
							+ System.nanoTime();
					ValueObject.value = value;
					lock.notify();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	//消费�?
	public static class C {
		private String lock;
		public C(String lock) {
			super();
			this.lock = lock;
		}

		public void getValue() {
			try {
				synchronized (lock) {
					while (ValueObject.value.equals("")) {
						System.out.println("消费�? "
								+ Thread.currentThread().getName() + " WAITING了☆");
						lock.wait();
					}
					System.out.println("消费�? " + Thread.currentThread().getName()
							+ " RUNNABLE�?");
					ValueObject.value = "";
					lock.notify();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class ThreadP extends Thread {
		private P p;
		public ThreadP(P p) {
			super();
			this.p = p;
		}

		@Override
		public void run() {
			while (true) {
				p.setValue();
			}
		}
	}
	
	public static class ThreadC extends Thread {
		private C r;
		public ThreadC(C r) {
			super();
			this.r = r;
		}

		@Override
		public void run() {
			while (true) {
				r.getValue();
			}
		}

	}
	
	/**
	 * �?有的线程呈假死状�?
	 * 在代码中确实已经通过wait/notify进行通信了，但不保证notify唤醒的是异类，也放是同类�?
	 * 比如“生产�?��?�唤醒生产�?�，�?"消费�?"唤可不是"消费�?"这样的情况�?�如果按这样情况运行的比率积�?
	 * 成多，就会导致所有的线程都不能运行下去，大家都在等待，都呈waiting状�?�，程序�?后也就呈假死状�?��??
	 * 
	 * 打印结果: 生产者notify生产者，消费者notify消费者，导致�?有的都进入waiting状�??
	 * 消费�? 消费�?2 WAITING了☆
生产�? 生产�?1 RUNNABLE�?
生产�? 生产�?1 WAITING了★
生产�? 生产�?2 WAITING了★
消费�? 消费�?1 RUNNABLE�?
消费�? 消费�?1 WAITING了☆
消费�? 消费�?2 WAITING了☆

	解决方案：不光唤醒同类，将异类也�?同唤醒就好了
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		String lock = new String("");
		P p = new P(lock);
		C r = new C(lock);

		ThreadP[] pThread = new ThreadP[2];
		ThreadC[] rThread = new ThreadC[2];

		for (int i = 0; i < 2; i++) {
			pThread[i] = new ThreadP(p);
			pThread[i].setName("生产�?" + (i + 1));

			rThread[i] = new ThreadC(r);
			rThread[i].setName("消费�?" + (i + 1));

			pThread[i].start();
			rThread[i].start();
		}

		Thread.sleep(5000);
		Thread[] threadArray = new Thread[Thread.currentThread()
				.getThreadGroup().activeCount()];
		Thread.currentThread().getThreadGroup().enumerate(threadArray);

		for (int i = 0; i < threadArray.length; i++) {
			System.out.println(threadArray[i].getName() + " "
					+ threadArray[i].getState());
		}
	}

}
