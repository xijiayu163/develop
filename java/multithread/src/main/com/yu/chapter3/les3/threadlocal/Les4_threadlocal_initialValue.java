package com.yu.chapter3.les3.threadlocal;

public class Les4_threadlocal_initialValue {
	
	public static class ThreadLocalExt extends ThreadLocal {
		@Override
		protected Object initialValue() {
			return "����Ĭ��ֵ ��һ��get����Ϊnull";
		}
	}
	
	public static ThreadLocalExt tl = new ThreadLocalExt();

	/**
	 * �سǾֲ�������ʼ��initialValue
	 * 
	 * ����Ĭ��ֵ ��һ��get����Ϊnull
		����Ĭ��ֵ ��һ��get����Ϊnull
	 * @param args
	 */
	public static void main(String[] args) {
		if (tl.get() == null) {
			System.out.println("��δ�Ź�ֵ");
			tl.set("�ҵ�ֵ");
		}
		System.out.println(tl.get());
		System.out.println(tl.get());
	}

}
