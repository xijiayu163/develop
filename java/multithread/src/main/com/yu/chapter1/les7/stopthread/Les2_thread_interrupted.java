package com.yu.chapter1.les7.stopthread;

public class Les2_thread_interrupted extends Thread {
	@Override
	public void run() {
		super.run();
		for (int i = 0; i < 500000; i++) {
			System.out.println("i=" + (i + 1));
		}

	}
	
//	/**
//	 * 判断线程是否停止,打印结果
//	 * 	run
//		是否停止1？=false
//		是否停止2？=false
//		end!
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			Les1_thread_interrupted thread = new Les1_thread_interrupted();
//			thread.start();
//			Thread.sleep(1000);
//			System.out.println("是否停止1？="+thread.interrupted());
//			System.out.println("是否停止2？="+thread.interrupted());
//		} catch (InterruptedException e) {
//			System.out.println("main catch");
//			e.printStackTrace();
//		}
//		System.out.println("end!");
//	}
	
//	/**
//	 * 
//	 * 判断主线程是否停止,打印结果,interrupted指的是当前调用该代码的线程的中断状态
//	 * 	run
//		是否停止1？=true
//		是否停止2？=false
//		end!
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		try {
//			Les1_thread_interrupted thread = new Les1_thread_interrupted();
//			thread.start();
//			Thread.sleep(1000);
//			Thread.currentThread().interrupt();
//			//thread.interrupted()静态方法，指的是当前调用该代码的线程，这里即主线程
//			System.out.println("是否停止1？="+thread.interrupted());
//			//interrupted()还具有清除状态的作用，所以第二次调用返回值为false.
//			System.out.println("是否停止2？="+thread.interrupted());
//		} catch (InterruptedException e) {
//			System.out.println("main catch");
//			e.printStackTrace();
//		}
//		System.out.println("end!");
//	}
	
	/**
	 * 
	 * 判断主线程是否停止,打印结果,isInterrupted指的是线程的中断状态,调用后不会清除状态
	 * 	run
		是否停止1？=true
		是否停止2？=true
		end!
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Les2_thread_interrupted thread = new Les2_thread_interrupted();
			thread.start();
			Thread.sleep(1000);
//			Thread.currentThread().interrupt();
			thread.interrupt();
			//thread.interrupted()静态方法，指的是当前调用该代码的线程，这里即主线程
			System.out.println("是否停止1？="+thread.isInterrupted());
			//interrupted()还具有清除状态的作用，所以第二次调用返回值为false.
			System.out.println("是否停止2？="+thread.isInterrupted());
		} catch (InterruptedException e) {
			System.out.println("main catch");
			e.printStackTrace();
		}
		System.out.println("end!");
	}
}
