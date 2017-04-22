package com.yu.chapter5.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesD2_schedule_task_delay_and_perodic {
	static public class MyTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("�����ˣ�ʱ��Ϊ��" + new Date());
		}
	}

	/**
	 *
	 * schedule(TimerTask task,long delay,long period) �Ե�ʱ��ʱ��Ϊ�ο�ʱ�䣬
	 * �ڴ�ʱ��������ӳ�ָ���ĺ�����������ĳһ���ʱ�����޴�����ִ��ĳһ����
	 * 
	 * ��ǰʱ�䣺2017-4-22 18:26:02
�����ˣ�ʱ��Ϊ��Sat Apr 22 18:26:05 CST 2017
�����ˣ�ʱ��Ϊ��Sat Apr 22 18:26:10 CST 2017

	 * @param args
	 */
	public static void main(String[] args) {
			MyTask task = new MyTask();
			Timer timer = new Timer();
			System.out.println("��ǰʱ�䣺"+new Date().toLocaleString());
			timer.schedule(task, 3000,5000);
	}
}
