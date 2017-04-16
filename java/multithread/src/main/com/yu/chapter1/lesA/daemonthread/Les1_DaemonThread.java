package com.yu.chapter1.lesA.daemonthread;

public class Les1_DaemonThread extends Thread {
	private int i = 0;

	@Override
	public void run() {
		try {
			while (true) {
				i++;
				System.out.println("i=" + (i));
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Les1_DaemonThread thread = new Les1_DaemonThread();
			thread.setDaemon(true);
			thread.start();
			Thread.sleep(5000);
			System.out.println("���뿪thread����Ҳ���ٴ�ӡ�ˣ�Ҳ����ֹͣ�ˣ�");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
