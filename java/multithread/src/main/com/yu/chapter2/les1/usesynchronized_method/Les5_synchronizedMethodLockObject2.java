package com.yu.chapter2.les1.usesynchronized_method;

/**
 * A�߳��ȳ���object�����Lock����B�߳̿������첽�ķ�ʽ����object����ķ�synchronized���͵ķ���
 * A�߳��ȳ���object�����Lock����B�߳��������ʱ����object�����е�synchronized���͵ķ�������ȴ���Ҳ����ͬ��
 * 
 * @author xijia
 *
 */
public class Les5_synchronizedMethodLockObject2 {
	
	synchronized public void methodA() {
		try {
			System.out.println("begin methodA threadName="
					+ Thread.currentThread().getName());
			Thread.sleep(5000);
			System.out.println("end endTime=" + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
//		
//	 /**
//	  * �Ŷ�ִ����methodA��methodB
//	  * main���н��	
//	  * begin methodA threadName=A
//end endTime=1492283809632
//begin methodB threadName=B begin time=1492283809632
//end
//
//	 * 
//	 * @param args
//	 */
//	 synchronized public void methodB() {
//		try {
//			System.out.println("begin methodB threadName="
//					+ Thread.currentThread().getName() + " begin time="
//					+ System.currentTimeMillis());
//			Thread.sleep(5000);
//			System.out.println("end");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	 /**
	  * methodA������Ӱ��methodB�Ĳ���ִ��
	  * begin methodA threadName=A
begin methodB threadName=B begin time=1492283844136
end
end endTime=1492283849136
	  */
	 public void methodB() {
			try {
				System.out.println("begin methodB threadName="
						+ Thread.currentThread().getName() + " begin time="
						+ System.currentTimeMillis());
				Thread.sleep(5000);
				System.out.println("end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	public static class ThreadA extends Thread {

		private Les5_synchronizedMethodLockObject2 object;

		public ThreadA(Les5_synchronizedMethodLockObject2 object) {
			super();
			this.object = object;
		}

		@Override
		public void run() {
			super.run();
			object.methodA();
		}

	}
	
	public static class ThreadB extends Thread {

		private Les5_synchronizedMethodLockObject2 object;

		public ThreadB(Les5_synchronizedMethodLockObject2 object) {
			super();
			this.object = object;
		}

		@Override
		public void run() {
			super.run();
			object.methodB();
		}
	}
	

	public static void main(String[] args) {
		Les5_synchronizedMethodLockObject2 object = new Les5_synchronizedMethodLockObject2();
		ThreadA a = new ThreadA(object);
		a.setName("A");
		ThreadB b = new ThreadB(object);
		b.setName("B");

		a.start();
		b.start();
	}


}
