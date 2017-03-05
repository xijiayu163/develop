package com.yu.test;

public class MyTest {
	
	static Integer a;
	public static void main(String[] args){
//		int a;
//		Integer b;
		
		System.out.println(a);
		
//		if(a==b){
//			System.out.println("相等");
//		}else{
//			System.out.println("不相等");
//		}
		testNullPointerException();
	}
	
	public static void testNullPointerException(){
		throw new NullPointerException("xxx");
	}
}
