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
					System.out.println("pop�����еģ�"
							+ Thread.currentThread().getName() + " �̳߳�wait״̬");
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
	 * list���������1��ֻ��һ����������һ��������
	 * �������еĽ����size()�������1��ֵ��0��1֮�佻�棬Ҳ�������������������������ڽ���ִ��
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
