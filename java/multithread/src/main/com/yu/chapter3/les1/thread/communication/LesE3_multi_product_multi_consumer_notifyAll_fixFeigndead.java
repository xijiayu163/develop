package com.yu.chapter3.les1.thread.communication;

public class LesE3_multi_product_multi_consumer_notifyAll_fixFeigndead {
	public static class ValueObject {
		public static String value = "";
	}
	
	//生产者
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
						System.out.println("生产者 "
								+ Thread.currentThread().getName() + " WAITING了★");
						lock.wait();
					}
					System.out.println("生产者 " + Thread.currentThread().getName()
							+ " RUNNABLE了");
					String value = System.currentTimeMillis() + "_"
							+ System.nanoTime();
					ValueObject.value = value;
					lock.notifyAll();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	//消费者
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
						System.out.println("消费者 "
								+ Thread.currentThread().getName() + " WAITING了☆");
						lock.wait();
					}
					System.out.println("消费者 " + Thread.currentThread().getName()
							+ " RUNNABLE了");
					ValueObject.value = "";
					lock.notifyAll();
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

	public static class ThreadR extends Thread {

		private C r;

		public ThreadR(C r) {
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
	 * 接E2 将notify改成notifyAll即可避免假死情况
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		String lock = new String("");
		P p = new P(lock);
		C r = new C(lock);

		ThreadP[] pThread = new ThreadP[2];
		ThreadR[] rThread = new ThreadR[2];

		for (int i = 0; i < 2; i++) {
			pThread[i] = new ThreadP(p);
			pThread[i].setName("生产者" + (i + 1));

			rThread[i] = new ThreadR(r);
			rThread[i].setName("消费者" + (i + 1));

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
