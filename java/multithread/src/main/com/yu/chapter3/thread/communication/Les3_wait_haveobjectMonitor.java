package com.yu.chapter3.thread.communication;

public class Les3_wait_haveobjectMonitor {

	/**
	 * wait使程序
	 * 运行结果:
	 * syn上面
		syn第一行
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String lock = new String();
			System.out.println("syn上面");
			synchronized (lock) {
				System.out.println("syn第一行");
				lock.wait();
				System.out.println("wait下的代码！");
			}
			System.out.println("syn下面的代码");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
