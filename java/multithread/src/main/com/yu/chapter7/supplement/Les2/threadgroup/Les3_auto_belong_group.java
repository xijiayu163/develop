package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les3_auto_belong_group {
	
	/**
	 * ��ʵ����һ��ThreadGroup xʱ�������ʾָ���������߳��飬��x�߳����Զ��鵽��ǰ
	 * �̶߳����������߳����У�Ҳ������ʽ����һ���߳����������һ�����߳��飬�����ڿ���̨��
	 * ��ӡ���߳�������ֵ��0�����1
	 * 
	 * A���̣߳�main �����߳���������0
A���̣߳�main �����߳���������1
��һ���߳�������Ϊ���µ���
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("A���̣߳�"+Thread.currentThread().getName()+" �����߳���������"+Thread.currentThread().getThreadGroup().activeGroupCount());
		ThreadGroup group=new ThreadGroup("�µ���");
		System.out.println("A���̣߳�"+Thread.currentThread().getName()+" �����߳���������"+Thread.currentThread().getThreadGroup().activeGroupCount());
		ThreadGroup[] threadGroup=new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];
		Thread.currentThread().getThreadGroup().enumerate(threadGroup);
		for (int i = 0; i < threadGroup.length; i++) {
			System.out.println("��һ���߳�������Ϊ��"+threadGroup[i].getName());
		}
	}
}
