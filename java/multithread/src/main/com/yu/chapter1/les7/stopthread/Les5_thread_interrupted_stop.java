package com.yu.chapter1.les7.stopthread;

public class Les5_thread_interrupted_stop extends Thread {
	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 500000; i++) {
			//判断中断状态，同时清除中断标记
			if (Thread.interrupted()) {
				System.out.println("已经是停止状态了!我要退出了!");
				break;
			}
			System.out.println("i=" + (i + 1));
		}
		if (!Thread.interrupted()) {
			System.out.println("interrupted,我被输出，如果此代码是for又继续运行，线程并未停止！");
		}
		if (!this.isInterrupted()) {
			System.out.println("isInterrupted,我被输出，如果此代码是for又继续运行，线程并未停止！");
		}
	}
	
	/**
	 * 	end!
已经是停止状态了!我要退出了!
interrupted,我被输出，如果此代码是for又继续运行，线程并未停止！
isInterrupted,我被输出，如果此代码是for又继续运行，线程并未停止！
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Les5_thread_interrupted_stop thread = new Les5_thread_interrupted_stop();
			thread.start();
			Thread.sleep(2000);
			thread.interrupt();
		} catch (InterruptedException e) {
			System.out.println("main catch");
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
