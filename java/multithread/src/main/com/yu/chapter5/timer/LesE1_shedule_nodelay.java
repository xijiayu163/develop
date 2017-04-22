package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesE1_shedule_nodelay {

	private static Timer timer = new Timer();
	private static int runCount = 0;

	static public class MyTask1 extends TimerTask {
		@Override
		public void run() {
			try {
				System.out.println("1 begin 运行了！时间为：" + new Date());
				Thread.sleep(1000);
				System.out.println("1   end 运行了！时间为：" + new Date());
				runCount++;
				if (runCount == 5) {
					timer.cancel();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * schedule,在不延时的情况下，如果执行任务的时间没有被延时，则下一次执行任务的时间是上一次任务的开始时间
	 * 加上delay时间
	 * 
	 * 
	 * 字符串1时间：2014-10-12 15:11:00 当前时间：2017-4-22 18:38:19
1 begin 运行了！时间为：Sat Apr 22 18:38:19 CST 2017
1   end 运行了！时间为：Sat Apr 22 18:38:20 CST 2017
1 begin 运行了！时间为：Sat Apr 22 18:38:22 CST 2017
1   end 运行了！时间为：Sat Apr 22 18:38:23 CST 2017
...
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask1 task1 = new MyTask1();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString1 = "2014-10-12 15:11:00";
			Date dateRef1 = sdf1.parse(dateString1);
			System.out.println("字符串1时间：" + dateRef1.toLocaleString() + " 当前时间："
					+ new Date().toLocaleString());
			timer.schedule(task1, dateRef1, 3000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
