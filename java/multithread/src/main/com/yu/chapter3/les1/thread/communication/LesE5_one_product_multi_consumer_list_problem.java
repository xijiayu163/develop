package com.yu.chapter3.les1.thread.communication;

import java.util.ArrayList;
import java.util.List;

public class LesE5_one_product_multi_consumer_list_problem {
	
	public static class MyStack {
		volatile private List list = new ArrayList();

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
	 * 有几率出现异常,因为MyStack使用了if语句作为条件判断
	 * 
	 * pop=anyString=0.029562336825062818
pop操作中的：Thread-1 线程呈wait状态Exception in thread "Thread-2" 
pop操作中的：Thread-4 线程呈wait状态
pop操作中的：Thread-5 线程呈wait状态
java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
	at java.util.ArrayList.rangeCheck(ArrayList.java:653)
	 */
	public static void main(String[] args) {
		MyStack myStack = new MyStack();

		P p = new P(myStack);

		C r1 = new C(myStack);
		C r2 = new C(myStack);
		C r3 = new C(myStack);
		C r4 = new C(myStack);
		C r5 = new C(myStack);

		P_Thread pThread = new P_Thread(p);
		pThread.start();

		C_Thread cThread1 = new C_Thread(r1);
		C_Thread cThread2 = new C_Thread(r2);
		C_Thread cThread3 = new C_Thread(r3);
		C_Thread cThread4 = new C_Thread(r4);
		C_Thread cThread5 = new C_Thread(r5);
		cThread1.start();
		cThread2.start();
		cThread3.start();
		cThread4.start();
		cThread5.start();
	}

}
