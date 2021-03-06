package com.yu.chapter3.les1.thread.communication;

import java.util.ArrayList;
import java.util.List;

public class LesE7_multi_product_one_consumer_list {
	
	public static class MyStack {
		volatile private List list = new ArrayList();

		synchronized public void push() {
			try {
				while (list.size() == 1) {
					this.wait();
				}
				list.add("anyString=" + Math.random());
				this.notifyAll();
				System.out.println("push=" + list.size());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		synchronized public String pop() {
			String returnValue = "";
			try {
				while (list.size() == 0) {
					System.out.println("pop操作中的："
							+ Thread.currentThread().getName() + " 线程呈wait状态");
					this.wait();
				}
				returnValue = "" + list.get(0);
				list.remove(0);
				this.notifyAll();
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
	 * 多个生产者一个消费者,size没有超过1
	 * 
	 */
	public static void main(String[] args) {
		MyStack myStack = new MyStack();

		P p1 = new P(myStack);
		P p2 = new P(myStack);
		P p3 = new P(myStack);
		P p4 = new P(myStack);
		P p5 = new P(myStack);
		P p6 = new P(myStack);

		P_Thread pThread1 = new P_Thread(p1);
		P_Thread pThread2 = new P_Thread(p2);
		P_Thread pThread3 = new P_Thread(p3);
		P_Thread pThread4 = new P_Thread(p4);
		P_Thread pThread5 = new P_Thread(p5);
		P_Thread pThread6 = new P_Thread(p6);
		pThread1.start();
		pThread2.start();
		pThread3.start();
		pThread4.start();
		pThread5.start();
		pThread6.start();
		
		C c1 = new C(myStack);
		C_Thread cThread = new C_Thread(c1);
		cThread.start();
	}
}
