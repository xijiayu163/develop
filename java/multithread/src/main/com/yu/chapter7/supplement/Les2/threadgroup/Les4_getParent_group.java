package com.yu.chapter7.supplement.Les2.threadgroup;

public class Les4_getParent_group {

	
	/**
	 * JVM�ĸ��߳������system,��ȡ�丸�߳���������쳣
	 * 
	 * 
	 * �̣߳�main ���ڵ��߳�����Ϊ��main
main�߳����ڵ��߳���ĸ��߳���������ǣ�system
Exception in thread "main" java.lang.NullPointerException
	at com.yu.chapter7.supplement.Les2.threadgroup.Run.main(Run.java:24)
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("�̣߳�" + Thread.currentThread().getName()
				+ " ���ڵ��߳�����Ϊ��"
				+ Thread.currentThread().getThreadGroup().getName());
		System.out
				.println("main�߳����ڵ��߳���ĸ��߳���������ǣ�"
						+ Thread.currentThread().getThreadGroup().getParent()
								.getName());
		System.out.println("main�߳����ڵ��߳���ĸ��߳���ĸ��߳���������ǣ�"
				+ Thread.currentThread().getThreadGroup().getParent()
						.getParent().getName());
	}

}
