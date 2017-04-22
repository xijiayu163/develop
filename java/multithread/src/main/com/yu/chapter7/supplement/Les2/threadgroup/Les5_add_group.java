package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les5_add_group {

	/**
	 * 用显式的方式在一个线程组中添加了一个子线程组
	 * 
	 * 线程组名称：main
线程组中活动的线程数量：1
线程组中线程组的数量-加之前：0
线程组中线程组的数量-加之之后：1
父线程组名称：system
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("线程组名称："
				+ Thread.currentThread().getThreadGroup().getName());
		System.out.println("线程组中活动的线程数量："
				+ Thread.currentThread().getThreadGroup().activeCount());
		System.out.println("线程组中线程组的数量-加之前："
				+ Thread.currentThread().getThreadGroup().activeGroupCount());
		ThreadGroup newGroup = new ThreadGroup(Thread.currentThread()
				.getThreadGroup(), "newGroup");
		System.out.println("线程组中线程组的数量-加之之后："
				+ Thread.currentThread().getThreadGroup().activeGroupCount());
		System.out
				.println("父线程组名称："
						+ Thread.currentThread().getThreadGroup().getParent()
								.getName());
	}

}
