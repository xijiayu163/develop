package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesB2_run_periodic_schudleTime_delay {
	static public class MyTaskA extends TimerTask {
		@Override
		public void run() {
			try {
				System.out.println("A�����ˣ�ʱ��Ϊ��" + new Date());
				Thread.sleep(5000);
				System.out.println("A�����ˣ�ʱ��Ϊ��" + new Date());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����������ÿ4��ִ�У�����ִ��һ��������5�룬������ʱ��1��
	 * ������ʱ��������һ��һ��ִ��
	 * 
	 * �ַ���ʱ�䣺2015-3-19 14:14:00 ��ǰʱ�䣺2017-4-22 18:05:59
A�����ˣ�ʱ��Ϊ��Sat Apr 22 18:05:59 CST 2017
A�����ˣ�ʱ��Ϊ��Sat Apr 22 18:06:04 CST 2017
A�����ˣ�ʱ��Ϊ��Sat Apr 22 18:06:04 CST 2017
A�����ˣ�ʱ��Ϊ��Sat Apr 22 18:06:09 CST 2017
A�����ˣ�ʱ��Ϊ��Sat Apr 22 18:06:09 CST 2017
...
	 * 
	 * ����ִ��ʱ�䱻��ʱ
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTaskA taskA = new MyTaskA();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = "2015-3-19 14:14:00";
			Timer timer = new Timer();
			Date dateRef = sdf.parse(dateString);
			System.out.println("�ַ���ʱ�䣺" + dateRef.toLocaleString() + " ��ǰʱ�䣺"
					+ new Date().toLocaleString());
			timer.schedule(taskA, dateRef, 4000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
