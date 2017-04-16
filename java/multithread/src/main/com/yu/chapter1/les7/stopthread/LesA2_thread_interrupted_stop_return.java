package com.yu.chapter1.les7.stopthread;

public class LesA2_thread_interrupted_stop_return extends Thread {

	@Override
	public void run() {
			while (true) {
				if (this.isInterrupted()) {
					System.out.println("Õ£÷π¡À!");
					return;
				}
				System.out.println("timer=" + System.currentTimeMillis());
			}
	}
	
	public static void main(String[] args) throws InterruptedException {
		LesA2_thread_interrupted_stop_return t=new LesA2_thread_interrupted_stop_return();
		t.start();
		Thread.sleep(2000);
		t.interrupt();
	}

}
