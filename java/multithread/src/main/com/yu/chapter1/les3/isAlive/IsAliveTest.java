package com.yu.chapter1.les3.isAlive;

public class IsAliveTest extends Thread {
	@Override
	public void run() {
		System.out.println("run=" + this.isAlive());
	}
	
	/**
	 * isAlive�жϵ�ǰ���߳��Ƿ��ڻ״̬
	 * �״ָ̬:�߳��Ѿ�������δ��ֹ,��ӡ���
	 * begin ==false
		middle ==true
		run=true
		end ==false
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		IsAliveTest mythread = new IsAliveTest();
		System.out.println("begin ==" + mythread.isAlive());
		mythread.start();
		System.out.println("middle ==" + mythread.isAlive());
		Thread.sleep(1000);
		System.out.println("end ==" + mythread.isAlive());
	}
}
