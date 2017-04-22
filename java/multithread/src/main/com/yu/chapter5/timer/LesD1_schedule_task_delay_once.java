package com.yu.chapter5.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LesD1_schedule_task_delay_once {
	static public class MyTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("运行了！时间为：" + new Date());
		}
	}
	
	/**
	 * timer.schedule(task, delay);在当前时间的基础上延迟指定的7秒执行一次任务
	 * 但线程没有结束
	 * 
	 * 当前时间：2017-4-22 18:23:01
运行了！时间为：Sat Apr 22 18:23:08 CST 2017
	 * @param args
	 */
	public static void main(String[] args) {
		MyTask task = new MyTask();
		Timer timer = new Timer();
		System.out.println("当前时间：" + new Date().toLocaleString());
		timer.schedule(task, 7000);
	}
}
