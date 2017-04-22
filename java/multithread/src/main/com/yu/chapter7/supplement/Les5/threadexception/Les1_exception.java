package com.yu.chapter7.supplement.Les5.threadexception;

public class Les1_exception {

	public static class MyThread extends Thread {
		@Override
		public void run() {
			String username = null;
			System.out.println(username.hashCode());
		}

	}
	
	/**
	 * Òì³£Å×³ö
	 * @param args
	 */
	public static void main(String[] args) {
		MyThread t = new MyThread();
		t.start();
	}

}
