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
				System.out.println("A运行了！时间为：" + new Date());
				Thread.sleep(5000);
				System.out.println("A结束了！时间为：" + new Date());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 本来期望是每4秒执行，由于执行一次任务花了5秒，所以延时了1秒
	 * 任务被延时，但还是一个一个执行
	 * 
	 * 字符串时间：2015-3-19 14:14:00 当前时间：2017-4-22 18:05:59
A运行了！时间为：Sat Apr 22 18:05:59 CST 2017
A结束了！时间为：Sat Apr 22 18:06:04 CST 2017
A运行了！时间为：Sat Apr 22 18:06:04 CST 2017
A结束了！时间为：Sat Apr 22 18:06:09 CST 2017
A运行了！时间为：Sat Apr 22 18:06:09 CST 2017
...
	 * 
	 * 任务执行时间被延时
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTaskA taskA = new MyTaskA();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = "2015-3-19 14:14:00";
			Timer timer = new Timer();
			Date dateRef = sdf.parse(dateString);
			System.out.println("字符串时间：" + dateRef.toLocaleString() + " 当前时间："
					+ new Date().toLocaleString());
			timer.schedule(taskA, dateRef, 4000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
