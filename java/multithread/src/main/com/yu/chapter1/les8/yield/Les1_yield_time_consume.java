package com.yu.chapter1.les8.yield;

public class Les1_yield_time_consume extends Thread {

	/**
	 * yield方法是放弃当前的CPU资源，将它让给其他的任务去占用CPU执行时间，但放弃的时间不确定
	 * 有可能刚刚放弃，马上又获得CPU时间片
	 * 使用yield用时：3911毫秒！
	 * 注释掉yield,用时：23毫秒！
	 * 
	 */
	@Override
	public void run() {
		long beginTime = System.currentTimeMillis();
		int count = 0;
		for (int i = 0; i < 50000000; i++) {
			//Thread.yield();
			count = count + (i + 1);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("用时：" + (endTime - beginTime) + "毫秒！");
	}

	public static void main(String[] args) {
		Les1_yield_time_consume thread = new Les1_yield_time_consume();
		thread.start();
	}
	
}
