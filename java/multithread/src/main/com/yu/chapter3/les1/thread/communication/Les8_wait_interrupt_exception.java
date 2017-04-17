package com.yu.chapter3.les1.thread.communication;

public class Les8_wait_interrupt_exception {

	public static class Service {

		public void testMethod(Object lock) {
			try {
				synchronized (lock) {
					System.out.println("begin wait()");
					lock.wait();
					System.out.println("  end wait()");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("�����쳣�ˣ���Ϊ��wait״̬���̱߳�interrupt�ˣ�");
			}
		}

	}
	
	public static class ThreadA extends Thread {

		private Object lock;

		public ThreadA(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			Service service = new Service();
			service.testMethod(lock);
		}

	}
	
	/**
	 * ���̳߳�wait()״̬ʱ�������̶߳����interrupt()���������InterruptException
	 * begin wait()
java.lang.InterruptedException
�����쳣�ˣ���Ϊ��wait״̬���̱߳�interrupt�ˣ�
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			Object lock = new Object();

			ThreadA a = new ThreadA(lock);
			a.start();

			Thread.sleep(5000);

			a.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
