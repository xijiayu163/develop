package com.yu.chapter5.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesD1_schedule_task_delay_once {
	static public class MyTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("�����ˣ�ʱ��Ϊ��" + new Date());
		}
	}
	
	/**
	 * timer.schedule(task, delay);�ڵ�ǰʱ��Ļ������ӳ�ָ����7��ִ��һ������
	 * ���߳�û�н���
	 * 
	 * ��ǰʱ�䣺2017-4-22 18:23:01
�����ˣ�ʱ��Ϊ��Sat Apr 22 18:23:08 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Timer timer = new Timer();
		System.out.println("��ǰʱ�䣺" + new Date().toLocaleString());
		timer.schedule(task, 7000);
	}
}
