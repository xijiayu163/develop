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
			System.out.println("A运行了！时间为：" + new Date());
			this.cancel();
		}
	}

	static public class MyTaskB extends TimerTask {
		@Override
		public void run() {
			System.out.println("B运行了！时间为：" + new Date());
		}
	}

	/**
	 * A只运行了一次，因为在A运行后就调用timerTask.cancel取消了
	 * timer.cancel是将自身从任务队列中清除,其它任务不受影响 
	 * 
	 * 字符串时间：2014-10-12 9:12:00 当前时间：2017-4-22 18:13:14
A运行了！时间为：Sat Apr 22 18:13:14 CST 2017
B运行了！时间为：Sat Apr 22 18:13:14 CST 2017
B运行了！时间为：Sat Apr 22 18:13:18 CST 2017
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
