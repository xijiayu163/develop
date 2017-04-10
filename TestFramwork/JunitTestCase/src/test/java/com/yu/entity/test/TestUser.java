package com.yu.entity.test;

import org.junit.Test;

import junit.framework.TestCase;

public class TestUser extends TestCase{
	
	@Override
	protected void setUp(){
		System.out.println("setup");
	}
	
	@Test
	public void testSetUser(){
		System.out.println("xxxxxxxx");
	}
	
	@Override
	protected void tearDown(){
		System.out.println("tear down");
	}
}
