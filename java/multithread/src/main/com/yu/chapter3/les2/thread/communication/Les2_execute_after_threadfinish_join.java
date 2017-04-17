package com.yu.chapter3.les2.thread.communication;

public class Les2_execute_after_threadfinish_join {

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
	
	/**
	 * 使用join在threadTest停止后再执行
	 * 方法join的作用是使所属的线程对象x正常执行run()方法中的任务，而使当前线程z进行无限期阻塞，
	 * 等待线程x销毁后再继续执行线程z后面的代码
	 * 方法join具有使线程排队的作用，有些类似同步的运行效果。join与synchronized的区别是:join在
	 * 内部使用wait()方法进行等待，而synchronized关键字使用的是"对象监视器"原理做为同步
	 * 在join过程中，如果当前线程对象被中断，则当前线程出现异常
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyThread threadTest = new MyThread();
			threadTest.start();
			threadTest.join();

			System.out.println("我想当threadTest对象执行完毕后我再执行，我做到了");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
