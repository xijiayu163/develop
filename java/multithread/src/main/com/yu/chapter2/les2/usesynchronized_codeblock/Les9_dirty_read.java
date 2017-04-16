package com.yu.chapter2.les2.usesynchronized_codeblock;

import java.util.ArrayList;
import java.util.List;

public class Les9_dirty_read {

	public static class MyOneList {

		private List list = new ArrayList();

		synchronized public void add(String data) {
			list.add(data);
		};

		synchronized public int getSize() {
			return list.size();
		};

	}
	
	public static class MyService {

		/**
		 * 运行main,listSize=2,从远程取了两次
		 * 加上synchronized (list),保证只取一次
		 * @param list
		 * @param data
		 * @return
		 */
		public MyOneList addServiceMethod(MyOneList list, String data) {
			try {
//				synchronized (list) {
					if (list.getSize() < 1) {
						Thread.sleep(2000);  //模拟如果没有数据，就从远程读取
						list.add(data);
					}
//				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return list;
		}

	}
	
	public static class MyThread1 extends Thread {

		private MyOneList list;

		public MyThread1(MyOneList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			MyService msRef = new MyService();
			msRef.addServiceMethod(list, "A");
		}

	}
	
	public static class MyThread2 extends Thread {

		private MyOneList list;

		public MyThread2(MyOneList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			MyService msRef = new MyService();
			msRef.addServiceMethod(list, "B");
		}

	}
	
	public static void main(String[] args) throws InterruptedException {
		MyOneList list = new MyOneList();

		MyThread1 thread1 = new MyThread1(list);
		thread1.setName("A");
		thread1.start();

		MyThread2 thread2 = new MyThread2(list);
		thread2.setName("B");
		thread2.start();
		
		Thread.sleep(6000);
		
		System.out.println("listSize=" + list.getSize());

	}

}
