package com.yu.chapter2.les2.usesynchronized_codeblock;

import java.util.ArrayList;
import java.util.List;

public class Les8_method_call_randommize {

	public static class MyList {

		private List list = new ArrayList();

		synchronized public void add(String username) {
			System.out.println("ThreadName=" + Thread.currentThread().getName()
					+ "执行了add方法！");
			list.add(username);
			System.out.println("ThreadName=" + Thread.currentThread().getName()
					+ "退出了add方法！");
		}

		synchronized public int getSize() {
			System.out.println("ThreadName=" + Thread.currentThread().getName()
					+ "执行了getSize方法！");
			int sizeValue = list.size();
			System.out.println("ThreadName=" + Thread.currentThread().getName()
					+ "退出了getSize方法！");
			return sizeValue;
		}

	}
	
	public static class MyThreadA extends Thread {

		private MyList list;

		public MyThreadA(MyList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100000; i++) {
				list.add("threadA" + (i + 1));
			}
		}

	}
	
	public static class MyThreadB extends Thread {

		private MyList list;

		public MyThreadB(MyList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			for (int i = 0; i < 100000; i++) {
				list.add("threadB" + (i + 1));
			}
		}
	}
	
	public static void main(String[] args) {
		MyList mylist = new MyList();

		MyThreadA a = new MyThreadA(mylist);
		a.setName("A");
		a.start();

		MyThreadB b = new MyThreadB(mylist);
		b.setName("B");
		b.start();
	}

}
