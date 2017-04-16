package com.yu.chapter1.les7.stopthread;

public class Les9_thread_stop_threaddeath_exception extends Thread {
	@Override
	public void run() {
		try {
			this.stop();
		} catch (ThreadDeath e) {
			System.out.println("进入了catch()方法！");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Les9_thread_stop_threaddeath_exception thread = new Les9_thread_stop_threaddeath_exception();
		thread.start();
	}
}
