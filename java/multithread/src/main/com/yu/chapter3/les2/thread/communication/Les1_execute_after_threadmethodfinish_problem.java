package com.yu.chapter3.les2.thread.communication;

public class Les1_execute_after_threadmethodfinish_problem {

	public static class MyThread extends Thread {

		@Override
		public void run() {
			try {
				int secondValue = (int) (Math.random() * 10000);
				System.out.println(secondValue);
				Thread.sleep(secondValue);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	//因为是一个随机值，不能确定什么时候该停下
	public static void main(String[] args) {
		MyThread threadTest = new MyThread();
		threadTest.start();

		// Thread.sleep(?)
		System.out.println("我想当threadTest对象执行完毕后我再执行");
		System.out.println("但上面代码中的sleep()中的值应该写多少呢？");
		System.out.println("答案是：根据不能确定:)");
	}
}
