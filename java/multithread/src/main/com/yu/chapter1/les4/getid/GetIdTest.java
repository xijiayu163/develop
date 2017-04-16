package com.yu.chapter1.les4.getid;

public class GetIdTest {
	/**
	 * 获取线程唯一Id
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName()+Thread.currentThread().getId());
	}
}
