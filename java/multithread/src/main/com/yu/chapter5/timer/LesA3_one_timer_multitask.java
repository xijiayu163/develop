package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesA3_one_timer_multitask {

	private static Timer timer = new Timer();

	static public class MyTask1 extends TimerTask {
		@Override
		public void run() {
			System.out.println("�����ˣ�ʱ��Ϊ��" + new Date());
		}
	}

	static public class MyTask2 extends TimerTask {
		@Override
		public void run() {
			System.out.println("�����ˣ�ʱ��Ϊ��" + new Date());
		}
	}

	/**
	 * һ��timer�п������ж��TimerTask����
	 * TimerTask���Զ��еķ�ʽһ��һ����˳��ִ�У�����ִ�е�ʱ���п��ܺ�Ԥ�ڵ�ʱ�䲻һ�£�
	 * ��Ϊǰ��������п������ĵ�ʱ��ϳ����������������е�ʱ��Ҳ�ᱻ�ӳ١�
	 * 
	 * �ַ���1ʱ�䣺2014-10-12 10:39:00 ��ǰʱ�䣺2017-4-22 17:51:44
�ַ���2ʱ�䣺2014-10-12 10:40:00 ��ǰʱ�䣺2017-4-22 17:51:44
�����ˣ�ʱ��Ϊ��Sat Apr 22 17:51:44 CST 2017
�����ˣ�ʱ��Ϊ��Sat Apr 22 17:51:44 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask1 task1 = new MyTask1();
			MyTask2 task2 = new MyTask2();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString1 = "2014-10-12 10:39:00";
			String dateString2 = "2014-10-12 10:40:00";

			Date dateRef1 = sdf1.parse(dateString1);
			Date dateRef2 = sdf2.parse(dateString2);

			System.out.println("�ַ���1ʱ�䣺" + dateRef1.toLocaleString() + " ��ǰʱ�䣺"
					+ new Date().toLocaleString());
			System.out.println("�ַ���2ʱ�䣺" + dateRef2.toLocaleString() + " ��ǰʱ�䣺"
					+ new Date().toLocaleString());

			timer.schedule(task1, dateRef1);
			timer.schedule(task2, dateRef2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
