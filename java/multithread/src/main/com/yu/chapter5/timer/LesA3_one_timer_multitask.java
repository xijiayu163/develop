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
			System.out.println("运行了！时间为：" + new Date());
		}
	}

	static public class MyTask2 extends TimerTask {
		@Override
		public void run() {
			System.out.println("运行了！时间为：" + new Date());
		}
	}

	/**
	 * 一个timer中可以运行多个TimerTask任务
	 * TimerTask是以队列的方式一个一个被顺序执行，所以执行的时间有可能和预期的时间不一致，
	 * 因为前面的任务有可能消耗的时间较长，则后面的任务运行的时间也会被延迟。
	 * 
	 * 字符串1时间：2014-10-12 10:39:00 当前时间：2017-4-22 17:51:44
字符串2时间：2014-10-12 10:40:00 当前时间：2017-4-22 17:51:44
运行了！时间为：Sat Apr 22 17:51:44 CST 2017
运行了！时间为：Sat Apr 22 17:51:44 CST 2017
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

			System.out.println("字符串1时间：" + dateRef1.toLocaleString() + " 当前时间："
					+ new Date().toLocaleString());
			System.out.println("字符串2时间：" + dateRef2.toLocaleString() + " 当前时间："
					+ new Date().toLocaleString());

			timer.schedule(task1, dateRef1);
			timer.schedule(task2, dateRef2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
