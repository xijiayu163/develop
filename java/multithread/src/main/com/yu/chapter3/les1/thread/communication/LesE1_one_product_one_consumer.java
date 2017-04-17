package com.yu.chapter3.les1.thread.communication;

public class LesE1_one_product_one_consumer {

	public static class ValueObject {
		volatile public static String value = "";
	}
	
	//������
	public static class P {
		private String lock;
		public P(String lock) {
			super();
			this.lock = lock;
		}

		public void setValue() {
			try {
				synchronized (lock) {
					if (!ValueObject.value.equals("")) {
						lock.wait();
					}
					String value = System.currentTimeMillis() + "_"
							+ System.nanoTime();
					System.out.println("set��ֵ��" + value);
					ValueObject.value = value;
					lock.notify();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//������
	public static class C {
		private String lock;
		public C(String lock) {
			super();
			this.lock = lock;
		}

		public void getValue() {
			try {
				synchronized (lock) {
					if (ValueObject.value.equals("")) {
						lock.wait();
					}
					System.out.println("get��ֵ��" + ValueObject.value);
					ValueObject.value = "";
					lock.notify();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
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
	
	/**
	 * һ������һ������
	 * set��get��������
	 * 
	 * set��ֵ��1492430521252_823013430668949
get��ֵ��1492430521252_823013430668949
set��ֵ��1492430521252_823013430690759
get��ֵ��1492430521252_823013430690759
set��ֵ��1492430521252_823013430712997
get��ֵ��1492430521252_823013430712997
	 * @param args
	 */
	public static void main(String[] args) {

		String lock = new String("");
		P p = new P(lock);
		C r = new C(lock);

		ThreadP pThread = new ThreadP(p);
		ThreadC rThread = new ThreadC(r);

		pThread.start();
		rThread.start();
	}
}
