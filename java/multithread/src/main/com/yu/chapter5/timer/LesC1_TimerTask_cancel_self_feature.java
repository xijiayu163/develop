package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesC1_TimerTask_cancel_self_feature {
	private static Timer timer = new Timer();

	static public class MyTaskA extends TimerTask {
		@Override
		public void run() {
			System.out.println("A�����ˣ�ʱ��Ϊ��" + new Date());
			this.cancel();
		}
	}

	static public class MyTaskB extends TimerTask {
		@Override
		public void run() {
			System.out.println("B�����ˣ�ʱ��Ϊ��" + new Date());
		}
	}

	/**
	 * Aֻ������һ�Σ���Ϊ��A���к�͵���timerTask.cancelȡ����
	 * timer.cancel�ǽ������������������,����������Ӱ�� 
	 * 
	 * �ַ���ʱ�䣺2014-10-12 9:12:00 ��ǰʱ�䣺2017-4-22 18:13:14
A�����ˣ�ʱ��Ϊ��Sat Apr 22 18:13:14 CST 2017
B�����ˣ�ʱ��Ϊ��Sat Apr 22 18:13:14 CST 2017
B�����ˣ�ʱ��Ϊ��Sat Apr 22 18:13:18 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTaskA taskA = new MyTaskA();
			MyTaskB taskB = new MyTaskB();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = "2014-10-12 09:12:00";
			Date dateRef = sdf.parse(dateString);
			System.out.println("�ַ���ʱ�䣺" + dateRef.toLocaleString() + " ��ǰʱ�䣺"
					+ new Date().toLocaleString());
			timer.schedule(taskA, dateRef, 4000);
			timer.schedule(taskB, dateRef, 4000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
