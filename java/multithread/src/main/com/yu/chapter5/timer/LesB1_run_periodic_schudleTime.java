package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesB1_run_periodic_schudleTime {
	static public class MyTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("运行了！时间为：" + new Date());
		}
	}

	/**
	 * timer.schedule(task, dateRef, 4000); 在指定的日期之后，按指定的时间间隔周期性地无限循环地执行某一任务
	 * 
	 * 字符串时间：2014-10-12 9:12:00 当前时间：2017-4-22 18:01:51
运行了！时间为：Sat Apr 22 18:01:51 CST 2017
运行了！时间为：Sat Apr 22 18:01:55 CST 2017
运行了！时间为：Sat Apr 22 18:01:59 CST 2017
运行了！时间为：Sat Apr 22 18:02:03 CST 2017
...
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask task = new MyTask();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = "2014-10-12 09:12:00";
			Timer timer = new Timer();
			Date dateRef = sdf.parse(dateString);
			System.out.println("字符串时间：" + dateRef.toLocaleString() + " 当前时间："
					+ new Date().toLocaleString());
			timer.schedule(task, dateRef, 4000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
