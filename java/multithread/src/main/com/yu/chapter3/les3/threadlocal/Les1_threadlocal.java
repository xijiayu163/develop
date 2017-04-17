package com.yu.chapter3.les3.threadlocal;

public class Les1_threadlocal {
	public static ThreadLocal tl = new ThreadLocal();

	public static void main(String[] args) {
		if (tl.get() == null) {
			System.out.println("从未放过值");
			tl.set("我的值");
		}
		System.out.println(tl.get());
		System.out.println(tl.get());
	}

}
