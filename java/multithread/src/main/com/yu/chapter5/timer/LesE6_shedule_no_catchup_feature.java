package com.yu.chapter5.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesE6_shedule_no_catchup_feature {

	private static Timer timer = new Timer();

	static public class MyTask1 extends TimerTask {
		@Override
		public void run() {
				System.out.println("1 begin 运行了！时间为：" + new Date());
				System.out.println("1   end 运行了！时间为：" + new Date());
		}
	}

	/**
	 * schedule 不具有追赶执行性
	 * 
	 * 字符串1时间：2014-10-12 15:37:00 当前时间：2017-4-22 21:22:20
1 begin 运行了！时间为：Sat Apr 22 21:22:20 CST 2017
1   end 运行了！时间为：Sat Apr 22 21:22:20 CST 2017
1 begin 运行了！时间为：Sat Apr 22 21:22:25 CST 2017
1   end 运行了！时间为：Sat Apr 22 21:22:25 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MyTask1 task1 = new MyTask1();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString1 = "2014-10-12 15:37:00";
			Date dateRef1 = sdf1.parse(dateString1);
			System.out.println("字符串1时间：" + dateRef1.toLocaleString() + " 当前时间："
					+ new Date().toLocaleString());
			timer.schedule(task1, dateRef1, 5000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
