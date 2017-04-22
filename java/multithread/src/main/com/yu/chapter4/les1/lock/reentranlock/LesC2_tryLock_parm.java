package com.yu.chapter4.les1.lock.reentranlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LesC2_tryLock_parm {

	public static class MyService {

		public ReentrantLock lock = new ReentrantLock();

		public void waitMethod() {
			try {
				if (lock.tryLock(3, TimeUnit.SECONDS)) {
					System.out.println("      " + Thread.currentThread().getName()
							+ "�������ʱ�䣺" + System.currentTimeMillis());
					Thread.sleep(10000);
				} else {
					System.out.println("      " + Thread.currentThread().getName()
							+ "û�л����");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (lock.isHeldByCurrentThread()) {
					lock.unlock();
				}
			}
		}
	}
	
	
	/**
	 * tryLock(long timeout,TimeUnit unit)�������ǣ���������ڸ���ʱ����û��
	 * ����һ���̱߳��֣� �ҵ�ǰ�߳�δ���жϣ����ȡ������
	 * 
	 * �߳�A3����ȡ�����߳�B��ʱδ�����
	 * 
	 * A����waitMethodʱ�䣺1492849203670
B����waitMethodʱ�䣺1492849203670
      A�������ʱ�䣺1492849203723
      Bû�л����
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		final MyService service = new MyService();

		Runnable runnableRef = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()
						+ "����waitMethodʱ�䣺" + System.currentTimeMillis());
				service.waitMethod();
			}
		};

		Thread threadA = new Thread(runnableRef);
		threadA.setName("A");
		threadA.start();
		Thread threadB = new Thread(runnableRef);
		threadB.setName("B");
		threadB.start();
	}
}
