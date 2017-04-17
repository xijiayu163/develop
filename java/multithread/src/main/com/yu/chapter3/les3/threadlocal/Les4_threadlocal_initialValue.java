package com.yu.chapter3.les3.threadlocal;

public class Les4_threadlocal_initialValue {
	
	public static class ThreadLocalExt extends ThreadLocal {
		@Override
		protected Object initialValue() {
			return "我是默认值 第一次get不再为null";
		}
	}
	
	public static ThreadLocalExt tl = new ThreadLocalExt();

	/**
	 * 县城局部变量初始化initialValue
	 * 
	 * 我是默认值 第一次get不再为null
		我是默认值 第一次get不再为null
	 * @param args
	 */
	public static void main(String[] args) {
		if (tl.get() == null) {
			System.out.println("从未放过值");
			tl.set("我的值");
		}
		System.out.println(tl.get());
		System.out.println(tl.get());
	}

}
