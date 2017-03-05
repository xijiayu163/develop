package com.yuxijia.controller;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
	
	/*
	 * 将HttpServletRequest保存在线程局比变量，针对每个请求都是一个独立的线程.可以在其它地方需要的时候拿出来使用
	 */
	private static final ThreadLocal<HttpServletRequest> REQUEST_THREAD_LOCAL = new ThreadLocal<HttpServletRequest>();
	
	/**
	 * 测试使用,在拦截器里设置线程局部变量,设置 方式为BaseController.setMyValue(10);在其它地方通过getMyValue()获取，不会与其他请求设置的myValue混淆
	 */
	private static final ThreadLocal<Integer> MYVALUE_THREAD_LOCAL = new ThreadLocal<Integer>();
	
	public static void setRequest(HttpServletRequest request) {
		REQUEST_THREAD_LOCAL.remove();
		REQUEST_THREAD_LOCAL.set(request);
	}
	
	public static HttpServletRequest getRequest() {
		return REQUEST_THREAD_LOCAL.get();
	}
	
	public static void setMyValue(Integer myValue) {
		MYVALUE_THREAD_LOCAL.remove();
		MYVALUE_THREAD_LOCAL.set(myValue);
	}
	
	public static Integer getMyValue() {
		return MYVALUE_THREAD_LOCAL.get();
	}
}
