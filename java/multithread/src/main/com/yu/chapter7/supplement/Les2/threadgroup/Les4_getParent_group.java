package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les4_getParent_group {

	
	/**
	 * JVM的根线程组就是system,再取其父线程组则出现异常
	 * 
	 * 
	 * 线程：main 所在的线程组名为：main
main线程所在的线程组的父线程组的名称是：system
Exception in thread "main" java.lang.NullPointerException
	at com.yu.chapter7.supplement.Les2.threadgroup.Run.main(Run.java:24)
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("线程：" + Thread.currentThread().getName()
				+ " 所在的线程组名为："
				+ Thread.currentThread().getThreadGroup().getName());
		System.out
				.println("main线程所在的线程组的父线程组的名称是："
						+ Thread.currentThread().getThreadGroup().getParent()
								.getName());
		System.out.println("main线程所在的线程组的父线程组的父线程组的名称是："
				+ Thread.currentThread().getThreadGroup().getParent()
						.getParent().getName());
	}

}
