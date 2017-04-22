package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les7_getsubGroup_recursive_OrNo {

	/**
	 * 递归与非递归取得子组
	 * @param args
	 */
	public static void main(String[] args) {

		ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
		ThreadGroup groupA = new ThreadGroup(mainGroup, "A");
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					System.out.println("runMethod!");
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		ThreadGroup groupB = new ThreadGroup(groupA, "B");

		// 分配空间，但不一定全部用完
		ThreadGroup[] listGroup1 = new ThreadGroup[Thread.currentThread()
				.getThreadGroup().activeGroupCount()];
		// false非递归取得子对象，也就是不取得B线程 ，打印 A
		// true，递归取得子组及子孙组,取和B线程 ，打印 A B
		Thread.currentThread().getThreadGroup().enumerate(listGroup1, false);
		for (int i = 0; i < listGroup1.length; i++) {
			if (listGroup1[i] != null) {
				System.out.println(listGroup1[i].getName());
			}
		}
	}

}
