package com.yu.chapter5.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesD2_schedule_task_delay_and_perodic {
	static public class MyTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("运行了！时间为：" + new Date());
		}
	}

	/**
	 *
	 * schedule(TimerTask task,long delay,long period) 以当时的时间为参考时间，
	 * 在此时间基础上延迟指定的毫秒数，再以某一间隔时间无限次数地执行某一任务
	 * 
	 * 当前时间：2017-4-22 18:26:02
运行了！时间为：Sat Apr 22 18:26:05 CST 2017
运行了！时间为：Sat Apr 22 18:26:10 CST 2017

	 * @param args
	 */
	public static void main(String[] args) {
			MyTask task = new MyTask();
			Timer timer = new Timer();
			System.out.println("当前时间："+new Date().toLocaleString());
			timer.schedule(task, 3000,5000);
	}
}
