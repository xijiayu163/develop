package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les5_add_group {

	/**
	 * ����ʽ�ķ�ʽ��һ���߳����������һ�����߳���
	 * 
	 * �߳������ƣ�main
�߳����л���߳�������1
�߳������߳��������-��֮ǰ��0
�߳������߳��������-��֮֮��1
���߳������ƣ�system
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("�߳������ƣ�"
				+ Thread.currentThread().getThreadGroup().getName());
		System.out.println("�߳����л���߳�������"
				+ Thread.currentThread().getThreadGroup().activeCount());
		System.out.println("�߳������߳��������-��֮ǰ��"
				+ Thread.currentThread().getThreadGroup().activeGroupCount());
		ThreadGroup newGroup = new ThreadGroup(Thread.currentThread()
				.getThreadGroup(), "newGroup");
		System.out.println("�߳������߳��������-��֮֮��"
				+ Thread.currentThread().getThreadGroup().activeGroupCount());
		System.out
				.println("���߳������ƣ�"
						+ Thread.currentThread().getThreadGroup().getParent()
								.getName());
	}

}
