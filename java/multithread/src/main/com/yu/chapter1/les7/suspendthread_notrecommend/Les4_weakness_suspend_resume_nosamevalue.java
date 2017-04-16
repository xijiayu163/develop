package com.yu.chapter1.les7.suspendthread_notrecommend;

public class Les4_weakness_suspend_resume_nosamevalue {

	private String username = "1";
	private String password = "11";

	public void setValue(String u, String p) {
		this.username = u;
		if (Thread.currentThread().getName().equals("a")) {
			System.out.println("ֹͣa�̣߳�");
			Thread.currentThread().suspend();
		}
		this.password = p;
	}

	public void printUsernamePassword() {
		System.out.println(username + " " + password);
	}
	
	public static void main(String[] args) throws InterruptedException {

		final Les4_weakness_suspend_resume_nosamevalue myobject = new Les4_weakness_suspend_resume_nosamevalue();

		Thread thread1 = new Thread() {
			public void run() {
				myobject.setValue("a", "aa");
			};
		};
		thread1.setName("a");
		thread1.start();

		Thread.sleep(500);

		Thread thread2 = new Thread() {
			public void run() {
				myobject.printUsernamePassword();
			};
		};
		thread2.start();

	}
}
