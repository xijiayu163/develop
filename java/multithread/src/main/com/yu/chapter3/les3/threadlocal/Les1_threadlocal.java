package com.yu.chapter3.les3.threadlocal;

public class Les1_threadlocal {
	public static ThreadLocal tl = new ThreadLocal();

	public static void main(String[] args) {
		if (tl.get() == null) {
			System.out.println("��δ�Ź�ֵ");
			tl.set("�ҵ�ֵ");
		}
		System.out.println(tl.get());
		System.out.println(tl.get());
	}

}
