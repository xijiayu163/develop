package com.yu.chapter2.les1.usesynchronized_method;

public class Les6_dirty_read {

	public String username = "A";
	public String password = "AA";

	synchronized public void setValue(String username, String password) {
		try {
			this.username = username;
			Thread.sleep(5000);
			this.password = password;

			System.out.println("setValue method thread name="
					+ Thread.currentThread().getName() + " username="
					+ username + " password=" + password);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * 脏读
//	 * getValue method thread name=main username=B password=AA
//setValue method thread name=Thread-0 username=B password=BB
//	 */
//	public void getValue() {
//		System.out.println("getValue method thread name="
//				+ Thread.currentThread().getName() + " username=" + username
//				+ " password=" + password);
//	}
	
	/**
	 * 同步读写
	 * setValue method thread name=Thread-0 username=B password=BB
getValue method thread name=main username=B password=BB
	 */
	synchronized public void getValue() {
		System.out.println("getValue method thread name="
				+ Thread.currentThread().getName() + " username=" + username
				+ " password=" + password);
	}
	
	public static class ThreadA extends Thread {

		private Les6_dirty_read publicVar;

		public ThreadA(Les6_dirty_read publicVar) {
			super();
			this.publicVar = publicVar;
		}

		@Override
		public void run() {
			super.run();
			publicVar.setValue("B", "BB");
		}
	}
	
	public static void main(String[] args) {
		try {
			Les6_dirty_read publicVarRef = new Les6_dirty_read();
			ThreadA thread = new ThreadA(publicVarRef);
			thread.start();

			Thread.sleep(200);// 打印结果受此值大小影响

			publicVarRef.getValue();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
