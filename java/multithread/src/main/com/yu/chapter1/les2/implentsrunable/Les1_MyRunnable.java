package com.yu.chapter1.les2.implentsrunable;

public class Les1_MyRunnable implements Runnable {
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("运行中");
	}
	
	public static void main(String[] args) {
		Runnable runnable=new Les1_MyRunnable();
		Thread thread=new Thread(runnable);
		thread.start();
		System.out.println("运行结束!");
	}
}
