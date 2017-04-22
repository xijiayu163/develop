package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les7_getsubGroup_recursive_OrNo {

	/**
	 * �ݹ���ǵݹ�ȡ������
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

		// ����ռ䣬����һ��ȫ������
		ThreadGroup[] listGroup1 = new ThreadGroup[Thread.currentThread()
				.getThreadGroup().activeGroupCount()];
		// false�ǵݹ�ȡ���Ӷ���Ҳ���ǲ�ȡ��B�߳� ����ӡ A
		// true���ݹ�ȡ�����鼰������,ȡ��B�߳� ����ӡ A B
		Thread.currentThread().getThreadGroup().enumerate(listGroup1, false);
		for (int i = 0; i < listGroup1.length; i++) {
			if (listGroup1[i] != null) {
				System.out.println(listGroup1[i].getName());
			}
		}
	}

}
