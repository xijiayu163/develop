package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesC2_Timer_cancel_all_feature {
	private static Timer timer = new Timer();

	static public class MyTaskA extends TimerTask {
		@Override
		public void run() {
			System.out.println("A运行了！时间为：" + new Date());
			timer.cancel();
		}
	}

	static public class MyTaskB extends TimerTask {
		@Override
		public void run() {
			System.out.println("B运行了！时间为：" + new Date());
		}
	}

	/**
	 * 只有A运行了一次后程序就结束，因为在A运行后就调用timer.cancel取消了所有的任务
	 * timer.cancel是将自身从任务队列中清除,其它任务不受影响 
	 * 
	 * 字符串时间：2014-10-12 9:12:00 当前时间：2017-4-22 18:14:28
A运行了！时间为：Sat Apr 22 18:14:28 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTaskA taskA = new MyTaskA();
			MyTaskB taskB = new MyTaskB();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = "2014-10-12 09:12:00";
			Date dateRef = sdf.parse(dateString);
			System.out.println("字符串时间：" + dateRef.toLocaleString() + " 当前时间："
					+ new Date().toLocaleString());
			timer.schedule(taskA, dateRef, 4000);
			timer.schedule(taskB, dateRef, 4000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
