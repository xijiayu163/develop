package com.yu.chapter2.les1.usesynchronized_method;

public class Les3_TowObjectsTowLock_Synchornized_ObjectLock {
	public static class HasSelfPrivateNum {

		private int num = 0;

		synchronized public void addI(String username) {
			try {
				if (username.equals("a")) {
					num = 100;
					System.out.println("a set over!");
					Thread.sleep(2000);
				} else {
					num = 200;
					System.out.println("b set over!");
				}
				System.out.println(username + " num=" + num);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static class ThreadA extends Thread {

		private HasSelfPrivateNum numRef;

		public ThreadA(HasSelfPrivateNum numRef) {
			super();
			this.numRef = numRef;
		}

		@Override
		public void run() {
			super.run();
			numRef.addI("a");
		}

	}
	
	public static class ThreadB extends Thread {

		private HasSelfPrivateNum numRef;

		public ThreadB(HasSelfPrivateNum numRef) {
			super();
			this.numRef = numRef;
		}

		@Override
		public void run() {
			super.run();
			numRef.addI("b");
		}

	}
	
	public static void main(String[] args) {

		HasSelfPrivateNum numRef1 = new HasSelfPrivateNum();
		HasSelfPrivateNum numRef2 = new HasSelfPrivateNum();

		ThreadA athread = new ThreadA(numRef1);
		athread.start();

		ThreadB bthread = new ThreadB(numRef2);
		bthread.start();

	}

}
