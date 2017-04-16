package com.yu.chapter3.thread.communication;

import java.util.ArrayList;
import java.util.List;

public class Les1_poll_commnication {
	
	public static class MyList {

		volatile private List list = new ArrayList();

		public void add() {
			list.add("�ߺ���");
		}

		public int size() {
			return list.size();
		}

	}
	
	public static class ThreadA extends Thread {

		private MyList list;

		public ThreadA(MyList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					list.add();
					System.out.println("�����" + (i + 1) + "��Ԫ��");
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static class ThreadB extends Thread {

		private MyList list;

		public ThreadB(MyList list) {
			super();
			this.list = list;
		}

		@Override
		public void run() {
			try {
				while (true) {
					if (list.size() == 5) {
						System.out.println("==5�ˣ��߳�bҪ�˳��ˣ�");
						throw new InterruptedException();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * ��Ȼ�����̼߳�ʵ����ͨ�ţ�����һ���׶˾��ǣ��߳�ThreadB.java��ͣ��ͨ��while�����ѯ����
	 * �����ĳһ���������������˷�CPU��Դ
	 * �����ѯ��ʱ������С�����˷�CPU��Դ�������ѯ��ʱ�����ܴ��п��ܻ�ȡ������Ҫ�����ݡ�
	 * ������Ҫ��һ�ֻ�����ʵ�ּ���CPU����Դ�˷ѣ����һ�����ʵ���ڶ���̼߳�ͨ�ţ�������"wait/notify"
	 * ����
	 * @param args
	 */
	public static void main(String[] args) {
		MyList service = new MyList();

		ThreadA a = new ThreadA(service);
		a.setName("A");
		a.start();

		ThreadB b = new ThreadB(service);
		b.setName("B");
		b.start();

	}

}
