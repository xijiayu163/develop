package com.yu.test;

public class CatchRunException {
	
	public static void main(String [] args){
		try
		{
			Test.getValue(false);
		}catch(Exception ex){
			System.out.println("ok111");
		}
		
		System.out.println("ok");
	}
	
	public static class Test{
		public static void getValue(boolean ok){
			if(ok){
				System.out.println("ok");
			}else{
				throw new RuntimeException();
			}
			
		}
	}
}



