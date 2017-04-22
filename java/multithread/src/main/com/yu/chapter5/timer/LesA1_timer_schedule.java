package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesA1_timer_schedule {

	private static Timer timer = new Timer();

	static public class MyTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("运行了！时间为：" + new Date());
		}
	}

	/**
	 * 任务虽然执行了，但程序还没有结束:因为创建一个timer,就是启动一个新的线程，这个新启动的线程
	 * 不是守护线程，它一直在运行
	 * 如果执行任务的时间早于当前时间，则立即执行
	 * 
	 * 字符串时间：2014-10-12 11:55:00 当前时间：2017-4-22 17:46:18
运行了！时间为：Sat Apr 22 17:46:18 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask task = new MyTask();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = "2014-10-12 11:55:00";

			Date dateRef = sdf.parse(dateString);
			System.out.println("字符串时间：" + dateRef.toLocaleString() + " 当前时间："
					+ new Date().toLocaleString());
			timer.schedule(task, dateRef);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
