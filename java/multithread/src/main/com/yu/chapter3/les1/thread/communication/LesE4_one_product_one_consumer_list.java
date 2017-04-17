package com.yu.chapter3.les1.thread.communication;

import java.util.ArrayList;
import java.util.List;

public class LesE4_one_product_one_consumer_list {
	
	public static class MyStack {
		private List list = new ArrayList();

		synchronized public void push() {
			try {
				if (list.size() == 1) {
					this.wait();
				}
				list.add("anyString=" + Math.random());
				this.notify();
				System.out.println("push=" + list.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public String pop() {
			String returnValue = "";
			try {
				if (list.size() == 0) {
					System.out.println("pop操作中的："
							+ Thread.currentThread().getName() + " 线程呈wait状态");
					this.wait();
				}
				returnValue = "" + list.get(0);
				list.remove(0);
				this.notify();
				System.out.println("pop=" + list.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return returnValue;
		}
	}
	
	public static class P {

		private MyStack myStack;

		public P(MyStack myStack) {
			super();
			this.myStack = myStack;
		}

		public void pushService() {
			myStack.push();
		}
	}
	
	public static class C {

		private MyStack myStack;

		public C(MyStack myStack) {
			super();
			this.myStack = myStack;
		}

		public void popService() {
			System.out.println("pop=" + myStack.pop());
		}
	}
	
	public static class P_Thread extends Thread {

		private P p;

		public P_Thread(P p) {
			super();
			this.p = p;
		}

		@Override
		public void run() {
			while (true) {
				p.pushService();
			}
		}

	}
	
	public static class C_Thread extends Thread {

		private C r;

		public C_Thread(C r) {
			super();
			this.r = r;
		}

		@Override
		public void run() {
			while (true) {
				r.popService();
			}
		}

	}
	
	/**
	 * list最大容量是1，只有一个生产者与一个消费者
	 * 程序运行的结果是size()不会大于1，值在0和1之间交替，也就是生产和消费这两个过程在交替执行
	 * @param args
	 */
	public static void main(String[] args) {
		MyStack myStack = new MyStack();

		P p = new P(myStack);
		C r = new C(myStack);

		P_Thread pThread = new P_Thread(p);
		C_Thread rThread = new C_Thread(r);
		pThread.start();
		rThread.start();
	}

}
