package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les3_auto_belong_group {
	
	/**
	 * 在实例化一个ThreadGroup x时如果不显示指定所属的线程组，则x线程组自动归到当前
	 * 线程对象所属的线程组中，也就是隐式地在一个线程组中添加了一个子线程组，所以在控制台中
	 * 打印的线程组数量值由0变成了1
	 * 
	 * A处线程：main 中有线程组数量：0
A处线程：main 中有线程组数量：1
第一个线程组名称为：新的组
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("A处线程："+Thread.currentThread().getName()+" 中有线程组数量："+Thread.currentThread().getThreadGroup().activeGroupCount());
		ThreadGroup group=new ThreadGroup("新的组");
		System.out.println("A处线程："+Thread.currentThread().getName()+" 中有线程组数量："+Thread.currentThread().getThreadGroup().activeGroupCount());
		ThreadGroup[] threadGroup=new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
		Thread.currentThread().getThreadGroup().enumerate(threadGroup);
		for (int i = 0; i < threadGroup.length; i++) {
			System.out.println("第一个线程组名称为："+threadGroup[i].getName());
		}
	}
}
