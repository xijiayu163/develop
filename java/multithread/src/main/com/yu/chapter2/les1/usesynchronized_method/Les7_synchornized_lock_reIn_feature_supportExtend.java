package com.yu.chapter2.les1.usesynchronized_method;


public class Les7_synchornized_lock_reIn_feature_supportExtend extends Thread {
	@Override
	public void run() {
		Sub sub = new Sub();
		sub.operateISubMethod();
	}
	
	public static class Main {

		public int i = 10;

		synchronized public void operateIMainMethod() {
			try {
				i--;
				System.out.println("main print i=" + i);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public static class Sub extends Main {

		synchronized public void operateISubMethod() {
			try {
				while (i > 0) {
					i--;
					System.out.println("sub print i=" + i);
					Thread.sleep(100);
					this.operateIMainMethod();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args) {
		Les7_synchornized_lock_reIn_feature_supportExtend t = new Les7_synchornized_lock_reIn_feature_supportExtend();
		t.start();
	}
	
}
