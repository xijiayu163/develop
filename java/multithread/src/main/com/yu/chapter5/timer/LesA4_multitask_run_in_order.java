package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesA4_multitask_run_in_order {

	private static Timer timer = new Timer();

	static public class MyTask1 extends TimerTask {
		@Override
		public void run() {
			try {
				System.out.println("1 begin 运行了！时间为：" + new Date());
				Thread.sleep(20000);
				System.out.println("1   end 运行了！时间为：" + new Date());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static public class MyTask2 extends TimerTask {
		@Override
		public void run() {
			System.out.println("2 begin 运行了！时间为：" + new Date());
			System.out.println("运行了！时间为：" + new Date());
			System.out.println("2   end 运行了！时间为：" + new Date());
		}
	}

	/**
	 * TimerTask是以队列的方式一个一个被顺序执行，所以执行的时间有可能和预期的时间不一致，
	 * 因为前面的任务有可能消耗的时间较长，则后面的任务运行的时间也会被延迟。
	 * 
	 * 多个task按顺序执行
	 * 由于task1需要用时20秒执行完任务，task1开始的时间是17:55:12,那么将要影响task2的计划
	 * 执行的时间，task2以此时间为基准，向后延迟20秒，task2在17:55:32执行任务 。
	 * 
	 * 字符串1时间：2014-10-12 11:33:00 当前时间：2017-4-22 17:55:12
字符串2时间：2014-10-12 11:33:10 当前时间：2017-4-22 17:55:12
1 begin 运行了！时间为：Sat Apr 22 17:55:12 CST 2017
1   end 运行了！时间为：Sat Apr 22 17:55:32 CST 2017
2 begin 运行了！时间为：Sat Apr 22 17:55:32 CST 2017
运行了！时间为：Sat Apr 22 17:55:32 CST 2017
2   end 运行了！时间为：Sat Apr 22 17:55:32 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask1 task1 = new MyTask1();
			MyTask2 task2 = new MyTask2();

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString1 = "2014-10-12 11:33:00";
			String dateString2 = "2014-10-12 11:33:10";

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
