package com.yu.chapter3.les1.thread.communication;

public class Les2_wait_noobjectMonitor_exception {
	
	/**
	 * 运行出现 java.lang.IllegalMonitorStateException异常
	 * 原因是没有“对象监视器”，也就是没有同步加锁
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String newString = new String("");
			newString.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}