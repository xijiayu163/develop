package com.yu.chapter1.les7.stopthread;

public class Les8_thread_stop_force_notrecommend extends Thread {
	private int i = 0;

	@Override
	public void run() {
		try {
			while (true) {
				i++;
				System.out.println("i=" + i);
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 暴力停止，调用stop,不会进入异常,执行后图标成灰色
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Les8_thread_stop_force_notrecommend thread = new Les8_thread_stop_force_notrecommend();
			thread.start();
			Thread.sleep(8000);
			//不推荐使用
			thread.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
